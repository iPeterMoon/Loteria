package network;

import dtos.peer.PeerInfo;
import factory.RedFactory;
import interfaces.IEnvio;
import java.util.concurrent.ExecutorService;
import procesadores_peer.ProcesadorMensajes;
import procesadores_peer.ProcesadorMensajesSalida;
import utilPeer.PoolHilos;

/**
 * Clase encargada de gestionar el envío de mensajes hacia otros peers de la
 * red.
 *
 * @author Jp
 */
public class EnvioPeer {

    /**
     * Componente de envío de red.
     */
    private final IEnvio envio;
    /**
     * Instancia única del EnvioPeer.
     */
    private static EnvioPeer instance;
    /**
     * Pool de hilos compartido para la ejecución de procesos de red.
     */
    private final ExecutorService threadPool;
    /**
     * Procesador encargado de gestionar los mensajes de salida.
     */
    private final ProcesadorMensajes procesador;

    /**
     * Constructor privado.
     *
     * Inicializa el componente de envío, el procesador de mensajes de salida y
     * el pool de hilos compartido.
     */
    private EnvioPeer() {
        this.envio = RedFactory.crearEnvioHandler();
        this.procesador = new ProcesadorMensajesSalida();
        this.threadPool = PoolHilos.getInstance().getThreadPool();
    }

    /**
     * Obtiene la instancia única del EnvioPeer.
     *
     * @return Instancia única del EnvioPeer.
     */
    public static EnvioPeer getInstance() {
        if (instance == null) {
            instance = new EnvioPeer();
        }
        return instance;
    }

    /**
     * Inicia los procesos necesarios para el envío de mensajes.
     *
     * Arranca el cliente de red y el procesador de mensajes de salida dentro
     * del pool de hilos compartido.
     */
    public void empezarEnvio() {
        envio.startClient();
        threadPool.execute(procesador);
    }

    /**
     * Envía un mensaje directo a un peer específico.
     *
     * @param peer Peer destinatario del mensaje.
     * @param mensaje Contenido del mensaje a enviar.
     */
    public void directMessage(PeerInfo peer, String mensaje) {
        if (peer == null) {
            System.err.println("[EnvioPeer] Intento de enviar mensaje directo a Peer null. Mensaje descartado.");
            return;
        }
        envio.sendEvent(peer.getIp(), peer.getPort(), mensaje);
    }

    /**
     * Detiene el componente de envío del peer y libera los recursos utilizados.
     */
    public void stop() {
        envio.stop();
        procesador.stop();
    }
}
