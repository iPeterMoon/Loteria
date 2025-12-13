package peer;

import com.google.gson.Gson;
import dtos.peer.PeerInfo;
import eventos.Evento;
import util.IObserver;
import mensajes.MensajeBroadcast;
import mensajes.MensajeDirecto;
import java.util.concurrent.ExecutorService;
import network.DiscoveryRegistrar;
import network.EnvioPeer;
import network.RecepcionPeer;
import procesadores_peer.ProcesadorMensajesLlegada;
import procesadores_peer.ProcesadorMensajesSalida;
import utilPeer.PoolHilos;
import network.OutgoingMessageDispatcher;

/**
 * Represemta al Peer principal del sistema. Esta clase actúa como el punto
 * central de coordinación entre los componentes.
 *
 * @author Alici
 */
public class Peer {

    /**
     * Instancia única de la clase.
     */
    private static Peer instance;

    /**
     * Información asociada al peer.
     */
    private PeerInfo myInfo;

    /**
     * Componente encargado del envío de mensajes.
     */
    private EnvioPeer envioHandler;

    /**
     * Componente encargado de la recepción de mensajes.
     */
    private RecepcionPeer recepcionHandler;

    /**
     * Observador que recibe los eventos procesados.
     */
    private IObserver observer;

    /**
     * Hilo encargado de enviar mensajes de heartbeat.
     */
    private Heartbeat heartbeat;

    /**
     * Procesador de mensajes entrantes.
     */
    private ProcesadorMensajesLlegada procesadorLlegada;

    /**
     * Procesador de mensajes salientes.
     */
    private ProcesadorMensajesSalida procesadorSalida;

    /**
     * Serializador JSON.
     */
    private final Gson gson = new Gson();

    /**
     * Indica si el peer se encuentra en ejecución
     */
    private volatile boolean running = false;

    /**
     * Constructor privado.
     *
     * Inicializa los componentes básicos del peer y prepara la estructura para
     * iniciar la comunicación.
     */
    private Peer() {
        this.myInfo = new PeerInfo(null, "", 0);

        this.envioHandler = EnvioPeer.getInstance();
        this.recepcionHandler = RecepcionPeer.getInstance();
    }

    /**
     * Devuelve la instancia única del Peer.
     *
     * @return instancia del Peer.
     */
    public static Peer getInstance() {
        if (instance == null) {
            instance = new Peer();
        }
        return instance;
    }

    /**
     * Constructor que permite registrar un observador.
     *
     * @param observer Observador que será notificado de los eventos.
     */
    public Peer(IObserver observer) {
        this.observer = observer;
    }

    /**
     * Obtiene la información del peer.
     *
     * @return información del peer.
     */
    public PeerInfo getMyInfo() {
        return myInfo;
    }

    /**
     * Inicia al Peer. Comienza la escucha de mensajes entrantes, inicia los
     * componentes de envío, registra el peer en el servidor de discovery e
     * inicia el envío periódico de heartbeat.
     */
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

    /**
     * Inicia el servidor de recepción y actualiza la información de red del
     * peer (IP y puerto).
     */
    private synchronized void empezarEscucha() throws Exception {
        String key = recepcionHandler.empezarEscucha();
        if (key == null) {
            throw new Exception("Error al empezar la escucha");
        }
        String[] netInfo = key.split(":");
        myInfo.setIp(netInfo[0]);
        myInfo.setPort(Integer.parseInt(netInfo[1]));
    }

    /**
     * Inicia el hilo encargado del envío de heartbeats.
     */
    private void empezarHeartbeat() {
        ExecutorService threadPool = PoolHilos.getInstance().getThreadPool();
        this.heartbeat = new Heartbeat(myInfo);
        threadPool.submit(this.heartbeat);
    }

    /**
     * Envía un evento a todos los peers conectados.
     *
     * @param evento Evento a difundir.
     */
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

    /**
     * Establece el nombre de usuario del peer y lo actualiza en el servicio de
     * discovery.
     *
     * @param user Nombre de usuario.
     */
    public void setUser(String user) {
        myInfo.setUser(user);
        PeersConectados.getInstance().setMyInfo(myInfo);
        DiscoveryRegistrar.registrar(myInfo);
    }

    /**
     * Notifica al observador sobre un evento recibido.
     *
     * @param evento Evento procesado.
     */
    public void notify(Evento evento) {
        observer.update(evento);
    }

    /**
     * Detiene todos los servicios asociados al peer y libera los recursos
     * utilizados.
     */
    public void stop() {
        running = false;
        envioHandler.stop();
        recepcionHandler.stop();
        System.out.println("[Peer] Detenido.");
        red.PoolHilos.getInstance().shutdown();
    }

    /**
     * Asigna el observador que recibirá los eventos.
     */
    public void setObserver(IObserver observer) {
        this.observer = observer;
    }

    /**
     * Método invocado por la UI para que el Peer abandone la partida.
     */
    public void abandonar() {
        System.out.println(">>> [PEER] Abandonando la partida voluntariamente...");
        // 2. Detener todos los servicios de red de forma segura.
        stop();
    }

}
