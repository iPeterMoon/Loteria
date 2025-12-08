package peer;

import com.google.gson.Gson;
import dtos.peer.PeerInfo;
import eventos.Evento;
import interfaces.IObserver;
import mensajes.MensajeBroadcast;
import mensajes.MensajeDirecto;

import java.util.concurrent.ExecutorService;
import network.DiscoveryRegistrar;
import network.EnvioPeer;
import network.RecepcionPeer;
import procesadores_peer.ProcesadorMensajesLlegada;
import procesadores_peer.ProcesadorMensajesSalida;
import util.ConfigLoader;
import utilPeer.PoolHilos;
import network.OutgoingMessageDispatcher;

/**
 *
 * @author Alici
 */
public class Peer {

    private static Peer instance;

    private PeerInfo myInfo;
    private EnvioPeer envioHandler;
    private RecepcionPeer recepcionHandler;
    private IObserver observer;
    private Heartbeat heartbeat;
    private ProcesadorMensajesLlegada procesadorLlegada;
    private ProcesadorMensajesSalida procesadorSalida;
    private final Gson gson = new Gson();

    private Peer() {
        this.myInfo = new PeerInfo(null, "", 0);

        this.envioHandler = EnvioPeer.getInstance();
        this.recepcionHandler = RecepcionPeer.getInstance();
    }

    public static Peer getInstance() {
        if (instance == null) {
            instance = new Peer();
        }
        return instance;
    }

    private volatile boolean running = false;

    public Peer(IObserver observer) {
        this.observer = observer;
    }

    public PeerInfo getMyInfo() {
        return myInfo;
    }

    public void start() {
        if (running) {
            return;
        }

        try {

            empezarEscucha();

            envioHandler.empezarEnvio();

            DiscoveryRegistrar.registrar(myInfo); 

            empezarHeartbeat();

            running = true;
            System.out.println("Peer corriendo");
        } catch (Exception e) {
            System.err.println("Error al iniciar peer: " + e.getMessage());
            stop();
        }
    }

    private synchronized void empezarEscucha() throws Exception {
        String key = recepcionHandler.empezarEscucha();
        if (key == null) {
            throw new Exception("Error al empezar la escucha");
        }
        String[] netInfo = key.split(":");
        myInfo.setIp(netInfo[0]);
        myInfo.setPort(Integer.parseInt(netInfo[1]));
    }

    private void empezarHeartbeat() {
        ExecutorService threadPool = PoolHilos.getInstance().getThreadPool();
        this.heartbeat = new Heartbeat(myInfo);
        threadPool.submit(this.heartbeat);
    }

    public void broadcastEvento(Evento evento) {
        MensajeBroadcast mensajeBroadcast = new MensajeBroadcast(evento);
        Gson gson = new Gson();
        String json = gson.toJson(mensajeBroadcast);
        OutgoingMessageDispatcher.dispatch(json);
    }

    /**
     * Crea un MensajeDirecto a partir de un Evento y un usuario (destinatario)
     * y lo envía al despachador de mensajes salientes.
     *
     * @param evento Evento que contiene el mensaje.
     * @param userReceiver Nombre del usuario del Peer destinatario.
     */
    public void directMessage(Evento evento, String userReceiver) {
        MensajeDirecto mensajeDirecto = new MensajeDirecto(evento, userReceiver);

        Gson gson = new Gson();
        String json = gson.toJson(mensajeDirecto);

        OutgoingMessageDispatcher.dispatch(json);
    }

    public void setUser(String user) {
        myInfo.setUser(user);
        PeersConectados.getInstance().setMyInfo(myInfo);
        DiscoveryRegistrar.registrar(myInfo);
    }

    public void notify(Evento evento) {
        observer.update(evento);
    }

    public void stop() {
        running = false;
        envioHandler.stop();
        recepcionHandler.stop();
        System.out.println("[Peer] Detenido.");
        red.PoolHilos.getInstance().shutdown();
    }

    public void setObserver(IObserver observer) {
        this.observer = observer;
    }

    /**
     * Método invocado por la UI para que el Peer abandone la partida.
     */
    public void abandonar() {
        System.out.println(">>> [PEER] Abandonando la partida voluntariamente...");

        // 1. Notificar al Servidor de Descubrimiento (Salida limpia)
        notificarAbandonoAlServidor();

        // 2. Detener todos los servicios de red de forma segura.
        stop();
    }

    /**
     * Crea el JSON del evento de desconexión y lo envía directamente al
     * Servidor Utiliza la infraestructura EnvioPeer, que delega a
     * EnvioPeer.directMessage()
     */
    private void notificarAbandonoAlServidor() {
        try {
            // Crear el DTO que será el cuerpo del mensaje, usando la información local
            MensajeDesconexion eventoDesconexion = new MensajeDesconexion(this.myInfo);

            // Convertir el Evento a JSON
            String jsonDesconexion = gson.toJson(eventoDesconexion);

            // Crear el PeerInfo del Destino 
            PeerInfo serverInfo = new PeerInfo(
                    "discovery",
                    ConfigLoader.getInstance().getIpServidor(),
                    ConfigLoader.getInstance().getPuertoDiscovery()
            );

            // Enviar el mensaje: Se delega a EnvioPeer para resolver y enviar
            EnvioPeer.getInstance().directMessage(serverInfo, jsonDesconexion);

            System.out.println(">>> [PEER] Notificación de abandono enviada al Servidor.");

        } catch (Exception e) {
            System.err.println("[Peer] Error al notificar la desconexión al servidor: " + e.getMessage());
        }
    }

    /**
     * Lógica ejecutada cuando el Peer local es elegido como el nuevo Host tras
     * la desconexión del anterior
     *
     * * @param miInfo La información del Peer local (que ahora es Host)
     */
    public void promoverseAHost(PeerInfo miInfo) {

        System.out.println(">>> [HOST] PROMOCIÓN EXITOSA: El Peer " + miInfo.getUser() + " ha asumido el rol de Host.");

    }
}
