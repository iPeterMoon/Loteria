package discovery;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import dtos.PeerInfo;
import enums.TipoEvento;
import eventos.EventoMatchmakerInfo;
import eventos.EventoNuevoPeer;
import eventos.EventoSolicitarMatchmaker;
import factory.RedFactory;
import interfaces.IEnvio;
import interfaces.IRecepcion;
import interfaces.IRedListener;

/**
 *
 * @author pedro
 */
public class Discovery implements IRedListener {

    private static final int PUERTO = 5000;
    private static final long HEARTBEAT_INTERVALO_MS = 500;
    private static final long HEARTBEAT_TIMEOUT_MS = 3 * HEARTBEAT_INTERVALO_MS; // 3x sin heartbeat → muerto
    private static final int LIMPIEZA_INTERVALO_MS = 500;

    private static final String MATCHMAKER_IP = "automundo.ddns.net";
    private static final int MATCHMAKER_PUERTO = 6000;
    private static final PeerInfo matchmakerPeer = new PeerInfo("matchmaker", MATCHMAKER_IP, MATCHMAKER_PUERTO);

    private final IEnvio envio = RedFactory.crearEnvioHandler();
    private final IRecepcion recepcion = RedFactory.crearRecepcionHandler();
    private final Gson gson = new Gson();

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private final Map<String, PeerInfo> peersVivos = new ConcurrentHashMap<>();
    private final Map<String, Long> ultimaVezVisto = new ConcurrentHashMap<>();

    private volatile boolean running = true;

    public Discovery() {
    }

    public void start() {
        try {
            envio.startClient();
            recepcion.setServerPort(PUERTO);
            recepcion.empezarEscucha();
            recepcion.setEventListener(this);

            scheduler.scheduleAtFixedRate(
                    this::limpiarPeersMuertos,
                    0,
                    LIMPIEZA_INTERVALO_MS,
                    TimeUnit.MILLISECONDS
            );

            System.out.println("[DiscoveryServer] Iniciado en puerto: " + PUERTO);
            System.out.println("[DiscoveryServer] Matchmaker: " + MATCHMAKER_IP + ":" + MATCHMAKER_PUERTO);
            System.out.println("[DiscoveryServer] Heartbeat: " + HEARTBEAT_INTERVALO_MS + " ms");
        } catch (IOException ex) {
            Logger.getLogger(Discovery.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void onMensajeRecibido(String mensaje) {
        procesarMensajeEntrante(mensaje);
    }

    private void procesarMensajeEntrante(String mensaje) {
        try {
            JsonObject json = gson.fromJson(mensaje, JsonObject.class);
            String tipo = json.get("tipoEvento").getAsString();

            switch (TipoEvento.valueOf(tipo)) {
                case TipoEvento.NUEVO_PEER ->
                    procesarRegistro(json);
                case TipoEvento.HEARTBEAT ->
                    procesarHeartbeat(json);
                case TipoEvento.SOLICITAR_MATCHMAKER ->
                    procesarSolicitudMatchmaker(json);
                default ->
                    System.out.println("[DiscoveryServer] Tipo desconocido: " + tipo);
            }
        } catch (JsonSyntaxException e) {
            System.err.println("[DiscoveryServer] Error: " + e.getMessage());
        }
    }

    private void procesarRegistro(JsonObject json) {
        EventoNuevoPeer evento = gson.fromJson(json, EventoNuevoPeer.class);
        PeerInfo peer = evento.getPeer();
        registrarPeer(peer.getUser(), peer);

        EventoMatchmakerInfo respuesta = new EventoMatchmakerInfo(matchmakerPeer, this.getClass().getSimpleName());
        JsonObject respuestaJson = gson.toJsonTree(respuesta).getAsJsonObject();

        enviarRespuesta(peer.getIp(), peer.getPort(), respuestaJson);
    }

    private void procesarHeartbeat(JsonObject json) {
        String user = json.get("user").getAsString();
        if (peersVivos.containsKey(user)) {
            ultimaVezVisto.put(user, System.currentTimeMillis());
        }
    }

    private void procesarSolicitudMatchmaker(JsonObject json) {
        String user = json.get("user").getAsString();
        PeerInfo peer = peersVivos.get(user);
        if (peer != null) {

            EventoSolicitarMatchmaker respuesta = new EventoSolicitarMatchmaker(matchmakerPeer, this.getClass().getSimpleName());
            JsonObject respuestaJson = gson.toJsonTree(respuesta).getAsJsonObject();
            
            enviarRespuesta(peer.getIp(), peer.getPort(), respuestaJson);
        }
    }

    private void enviarRespuesta(String ip, int puerto, JsonObject mensaje) {
        envio.sendEvent(ip, puerto, gson.toJson(mensaje));
    }

    private void registrarPeer(String user, PeerInfo peer) {
        peersVivos.put(user, peer);
        ultimaVezVisto.put(user, System.currentTimeMillis());
        System.out.println("[DiscoveryServer] Peer registrado: " + user + " @ " + peer.getIp() + ":" + peer.getPort());
    }

    private void limpiarPeersMuertos() {
        if (!running) {
            return;
        }

        long ahora = System.currentTimeMillis();
        ultimaVezVisto.entrySet().removeIf(e -> {
            if (ahora - e.getValue() > HEARTBEAT_TIMEOUT_MS) {
                String user = e.getKey();
                PeerInfo muerto = peersVivos.remove(user);
                if (muerto != null) {
                    System.out.println("[DiscoveryServer] Peer eliminado (timeout " + HEARTBEAT_TIMEOUT_MS + " ms): " + user);
                }
                return true;
            }
            return false;
        });
    }

}
