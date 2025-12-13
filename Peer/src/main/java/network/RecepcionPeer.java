package network;

import java.util.concurrent.ExecutorService;
import procesadores_peer.ProcesadorMensajes;
import procesadores_peer.ProcesadorMensajesLlegada;
import utilPeer.PoolHilos;

/**
 * Clase responsable de inicializar y coordinar la recepción de mensajes
 * provenientes de la red en el componente de Peer.
 *
 * @author pedro
 */
public class RecepcionPeer {

    /**
     * Instancia única del receptor del peer.
     */
    private static RecepcionPeer instance;
    
    /**
     * Procesador encargado de manejar los mensajes recibidos.
     */
    private ProcesadorMensajes procesador;
    
    /**
     * Listener de red que permanece a la espera de mensajes entrantes.
     */
    private RedListener redListener;
    
    /**
     * Pool de hilos compartido para la ejecución de tareas concurrentes.
     */
    private final ExecutorService threadPool;

    /**
     * Constructor privado.
     */
    private RecepcionPeer() {
        this.threadPool = PoolHilos.getInstance().getThreadPool();
    }

    /**
     * Obtiene la instancia única de RecepcionPeer.
     * @return Instancia única del receptor del peer.
     */
    public static RecepcionPeer getInstance() {
        if (instance == null) {
            instance = new RecepcionPeer();
        }
        return instance;
    }

    /**
     * Empieza la recepción de mensajes
     *
     * @return El key con la ip y puerto del Peer
     */
    public String empezarEscucha() {
        try {
            redListener = new RedListener();
            procesador = new ProcesadorMensajesLlegada();
            int port = redListener.start();

            ejecutarHilos();

            String ip = NetworkHelper.obtenerIpPrioritaria();
            
            System.out.println("[RecepcionPeer] IP Publicada para otros peers: " + ip);
            
            return ip + ":" + port;

        } catch (Exception e) {
            System.out.println("Problema al obtener la dirección ip");
            return null;
        }
    }

    /**
     * Ejecura los hilos asociados al listener y al procesador de mensajes.
     */
    private void ejecutarHilos() {
        threadPool.execute(redListener);
        threadPool.execute(procesador);
    }

    /**
     * Detiene la recepción de mensajes del peer y libera los recursos asociados.
     */
    public void stop() {
        redListener.stop();
        procesador.stop();
    }

}
