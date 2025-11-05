package peer;

import java.net.InetAddress;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import dtos.PeerInfo;
import enums.TipoEvento;
import eventos.Evento;
import eventos.EventoFicha;
import eventos.EventoNuevoPeer;
import factory.RedFactory;
import interfaces.IEnvio;
import interfaces.IObserver;
import interfaces.IPeer;
import interfaces.IRecepcion;
import interfaces.IRedListener;
import util.ConfigLoader;

/**
 *
 * @author Alici
 */
public class Peer implements IPeer, IRedListener {

    private PeerInfo myInfo;
    private final IEnvio envio = RedFactory.crearEnvioHandler();
    private final IRecepcion recepcion = RedFactory.crearRecepcionHandler();
    private final Gson gson;
    private final IObserver observer;
    private static final String DISCOVERY_IP = ConfigLoader.getInstance().getIpServidor();
    private static final int DISCOVERY_PUERTO = ConfigLoader.getInstance().getPuertoDiscovery();
    private final List<PeerInfo> peersPartida = new LinkedList<>();
    private volatile boolean isHost = false;

    public Peer(IObserver observer) {
        gson = new Gson();
        this.observer = null;
    }

    @Override
    public PeerInfo getMyInfo() {
        return this.myInfo;
    }

    @Override
    public void start() {
        try {
            int myPort = recepcion.empezarEscucha();

            String myIp = InetAddress.getLocalHost().getHostAddress();
            this.myInfo = new PeerInfo(null, myIp, myPort);

            System.out.println("Mi info : " + myInfo);
            envio.startClient();
            recepcion.setEventListener(this);

            registrarEnDiscovery(DISCOVERY_IP, DISCOVERY_PUERTO);
        } catch (Exception e) {
            System.err.println("Error al iniciar el peer: " + e.getMessage());
            stop();
        }

    }

    public void registrarEnDiscovery(String ipDiscovery, int portDiscovery) {
        EventoNuevoPeer nuevo = new EventoNuevoPeer(myInfo, myInfo.getUser());
        String json = gson.toJson(nuevo);
        envio.sendEvent(ipDiscovery, portDiscovery, json);
    }

    @Override
    public void stop() {
        if (recepcion != null) {
            recepcion.stop();
        }
        if (envio != null) {
            envio.stop();
        }

        System.out.println("[PeerImpl] Peer detenido.");
    }

    @Override
    public void broadcastEvento(Evento evento) {
        if (peersPartida.isEmpty()) {
            System.err.println("[PeerImpl] No se puede hacer broadcast: lista de peers vacía.");
            return;
        }

        System.out.println("[PeerImpl] Enviando Broadcast: " + evento.getTipoEvento() + " a " + peersPartida.size() + " peers.");

        for (PeerInfo peer : peersPartida) {
            String json = gson.toJson(evento);
            System.out.println(json);
            envio.sendEvent(peer.getIp(), peer.getPort(), json);
        }
    }

    @Override
    public void onMensajeRecibido(String mensaje) {
        procesarMensajeEntrante(mensaje);
    }

    /**
     * Por el momento solamente se estan manejando los eventos de un nuevo peer
     * y el evento de recibir una ficha de otro peer
     *
     * @param mensaje
     */
    private void procesarMensajeEntrante(String mensaje) {
        try {
            JsonObject json = gson.fromJson(mensaje, JsonObject.class);
            String tipo = json.get("tipoEvento").getAsString();

            switch (TipoEvento.valueOf(tipo)) {
                case TipoEvento.NUEVO_PEER ->
                    procesarNuevoPeer(json);
                case TipoEvento.FICHA ->
                    procesarEventoFicha(json);
                case TipoEvento.HEARTBEAT ->
                    responderHeartbeat();
                default ->
                    System.out.println("[Peer] Tipo de evento desconocido: " + tipo);
            }
        } catch (JsonSyntaxException e) {
            System.err.println("[Peer] Error: " + e.getMessage());
        }
    }

    private void procesarNuevoPeer(JsonObject json) {
        EventoNuevoPeer evento = gson.fromJson(json, EventoNuevoPeer.class);
        PeerInfo peer = evento.getPeer();
        this.peersPartida.add(peer);
    }

    private void procesarEventoFicha(JsonObject json) {
        EventoFicha evento = gson.fromJson(json, EventoFicha.class);
        notify(evento);
    }

    private void responderHeartbeat() {
        PeerInfo discoveryInfo = new PeerInfo("discovery", DISCOVERY_IP, DISCOVERY_PUERTO);
        ProcesadorHeartbeat.procesarHeartbeat(discoveryInfo, myInfo);
    }

    private void notify(Evento evento) {
        observer.update(evento);
    }

    @Override
    public void setUser(String user) {
        this.myInfo.setUser(user);
    }
}
