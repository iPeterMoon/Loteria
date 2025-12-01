package network;

import java.net.InetAddress;
import java.net.DatagramSocket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import procesadores_peer.ProcesadorMensajes;
import procesadores_peer.ProcesadorMensajesLlegada;
import utilPeer.PoolHilos;

/**
 *
 * @author pedro
 */
public class RecepcionPeer {

    private static RecepcionPeer instance;
    private ProcesadorMensajes procesador;
    private RedListener redListener;
    private final ExecutorService threadPool;

    private RecepcionPeer() {
        this.threadPool = PoolHilos.getInstance().getThreadPool();
    }

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

            // Usamos un DatagramSocket conectado a un IP pública (no se envía tráfico)
            // para conocer la interfaz local que se usaría para salir a Internet.
            String ip;
            try (DatagramSocket ds = new DatagramSocket()) {
                ds.connect(InetAddress.getByName("8.8.8.8"), 53);
                ip = ds.getLocalAddress().getHostAddress();
                if (ip == null || ip.isBlank()) {
                    ip = InetAddress.getLocalHost().getHostAddress();
                }
            } catch (Exception ex) {
                // Fallback al mecanismo tradicional
                ip = InetAddress.getLocalHost().getHostAddress();
            }

            return ip + ":" + port;

        } catch (UnknownHostException e) {
            System.out.println("Problema al obtener la dirección ip");
            return null;
        }
    }

    private void ejecutarHilos() {
        threadPool.execute(redListener);
        threadPool.execute(procesador);
    }

    public void stop() {
        redListener.stop();
        procesador.stop();
    }

}
