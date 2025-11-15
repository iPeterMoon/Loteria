package discovery;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import dtos.PeerInfo;
import factory.RedFactory;
import interfaces.IEnvio;
import interfaces.IRecepcion;
import interfaces.IRedListener;
import procesadores.ManejadorHeartbeat;
import procesadores.ManejadorMensajes;
import procesadores.ManejadorNuevoPeer;
import procesadores.PeerCleaner;
import util.ConfigLoader;

/**
 *
 * @author pedro
 */
public class Discovery implements IRedListener {

    private static final int PUERTO = ConfigLoader.getInstance().getPuertoDiscovery();
    private static final long HEARTBEAT_INTERVALO_MS = 500;
    private static final long HEARTBEAT_TIMEOUT_MS = 3 * HEARTBEAT_INTERVALO_MS; // 3x sin heartbeat → muerto
    private static final int LIMPIEZA_INTERVALO_MS = 2000;
    private final IEnvio envio = RedFactory.crearEnvioHandler();
    private final IRecepcion recepcion = RedFactory.crearRecepcionHandler();
    private final Gson gson = new Gson();

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private ManejadorMensajes mainHandler;

    private volatile boolean running = true;

    private static Discovery instance;

    /**
     * Constructor del discovery, inicializa el orden de los manejadores de
     * mensajes.
     */
    private Discovery() {

        ManejadorMensajes heartbeatHandler = new ManejadorHeartbeat();
        ManejadorMensajes newPeerHandler = new ManejadorNuevoPeer();

        heartbeatHandler.setNext(newPeerHandler);

        this.mainHandler = heartbeatHandler;

    }

    /**
     * Obtiene la unica instancia de esta clase
     * 
     * @return Instancia de esta clase
     */
    public static Discovery getInstance() {
        if (instance == null) {
            instance = new Discovery();
        }
        return instance;
    }

    /**
     * Inicializa el servidor de discovery, los componentes de red y también un hilo
     * aparte para eliminar a los peers muertos cada cierto tiempo.
     */
    public void start() {
        try {
            envio.startClient();
            recepcion.setServerPort(PUERTO);
            recepcion.empezarEscucha();
            recepcion.setEventListener(this);

            // Crear la instancia de PeerCleaner
            PeerCleaner tareaLimpieza = new PeerCleaner(
                    this.envio, // Le pasamos el 'envio' que ya tiene Discovery
                    HEARTBEAT_TIMEOUT_MS);

            // Programar la TAREA (el objeto)
            scheduler.scheduleAtFixedRate(
                    tareaLimpieza, // Se pasa el objeto PeerCleaner
                    0,
                    LIMPIEZA_INTERVALO_MS,
                    TimeUnit.MILLISECONDS);

            System.out.println("[DiscoveryServer] Iniciado en puerto: " + PUERTO);
        } catch (IOException ex) {
            Logger.getLogger(Discovery.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void onMensajeRecibido(String mensaje) {
        procesarMensajeEntrante(mensaje);
    }

    /**
     * Procesa un mensaje entrante llamando a los manejadores de mensajes
     * 
     * @param mensaje Mensaje a procesar
     */
    private void procesarMensajeEntrante(String mensaje) {
        try {
            JsonObject json = gson.fromJson(mensaje, JsonObject.class);
            mainHandler.procesar(json);
        } catch (JsonSyntaxException e) {
            System.err.println("[DiscoveryServer] Error: " + e.getMessage());
        }
    }

    /**
     * Envia un mensaje a un peer en especifico
     * 
     * @param ip
     * @param puerto
     * @param mensaje
     */
    public void directMessage(PeerInfo info, String mensaje) {
        envio.sendEvent(info.getIp(), info.getPort(), mensaje);
    }

    /**
     * Limpia los peers muertos llamando a un metodo de la Lista de Peers
     *
     * private void limpiarPeersMuertos() {
     * if (!running) {
     * return;
     * }
     * ListaPeers.limpiarPeersMuertos(HEARTBEAT_TIMEOUT_MS);
     * }
     */

}
