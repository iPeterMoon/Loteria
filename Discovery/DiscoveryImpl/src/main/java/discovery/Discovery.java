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
import eventos.EventoHeartbeat;
import eventos.EventoMatchmakerInfo;
import eventos.EventoNuevoPeer;
import eventos.EventoSolicitarMatchmaker;
import factory.RedFactory;
import interfaces.IEnvio;
import interfaces.IRecepcion;
import interfaces.IRedListener;
import util.ConfigLoader;

/**
 *
 * @author pedro
 */
public class Discovery implements IRedListener {

    private static final String IP = ConfigLoader.getInstance().getIpServidor(); 
    private static final int PUERTO = ConfigLoader.getInstance().getPuertoDiscovery();
    private static final long HEARTBEAT_INTERVALO_MS = 2000;
    private static final long HEARTBEAT_TIMEOUT_MS = 3 * HEARTBEAT_INTERVALO_MS; // 3x sin heartbeat → muerto
    private static final int LIMPIEZA_INTERVALO_MS = 2000;

    private static final String MATCHMAKER_IP = ConfigLoader.getInstance().getIpServidor();
    private static final int MATCHMAKER_PUERTO = ConfigLoader.getInstance().getPuertoMatchmaker();
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
        registrarPeer(obtenerKey(peer), peer);

        EventoMatchmakerInfo respuesta = new EventoMatchmakerInfo(matchmakerPeer, this.getClass().getSimpleName());
        JsonObject respuestaJson = gson.toJsonTree(respuesta).getAsJsonObject();

        enviarRespuesta(peer.getIp(), peer.getPort(), respuestaJson);
        
        enviarRespuesta(peer.getIp(), peer.getPort(), gson.toJsonTree(evento).getAsJsonObject()); // Para probar eventos NUEVO_PEER
    }
    
    private String obtenerKey(PeerInfo info) {
        return info.getIp() + ":" +  info.getPort();
    }

    private void procesarHeartbeat(JsonObject json) {
        EventoHeartbeat heartbeat = gson.fromJson(json, EventoHeartbeat.class);
        PeerInfo infoPeer = heartbeat.getInfo();
        String key = obtenerKey(infoPeer);
        if (peersVivos.containsKey(key)) {
            ultimaVezVisto.put(key, System.currentTimeMillis());
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

    private void registrarPeer(String key, PeerInfo peer) {
        peersVivos.put(key, peer);
        ultimaVezVisto.put(key, System.currentTimeMillis());
        System.out.println("[DiscoveryServer] Peer registrado: " + key + " @ " + peer.getIp() + ":" + peer.getPort());
    }

    private void limpiarPeersMuertos() {
        if (!running) {
            return;
        }

        long ahora = System.currentTimeMillis();
        ultimaVezVisto.entrySet().removeIf(e -> {
            if (ahora - e.getValue() > HEARTBEAT_TIMEOUT_MS) {
                String peerKey = e.getKey();
                PeerInfo muerto = peersVivos.remove(peerKey);
                if (muerto != null) {
                    System.out.println("[DiscoveryServer] Peer eliminado (timeout " + HEARTBEAT_TIMEOUT_MS + " ms): " + peerKey);
                }
                return true;
            }
            return false;
        });
    }

}
