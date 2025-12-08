package network;

import dtos.PeerInfo;
import factory.RedFactory;
import interfaces.IEnvio;
import java.util.concurrent.ExecutorService;
import procesadores_discovery.ProcesadorMensajesSalida;
import util_discovery.PoolHilos;

/**
 *
 * @author Alici
 */
public class Envio {

    private final IEnvio envio;
    private static Envio instance;
    private final ExecutorService threadPool;
    private final ProcesadorMensajesSalida procesador;

    private Envio() {
        this.envio = RedFactory.crearEnvioHandler();
        this.procesador = new ProcesadorMensajesSalida();
        this.threadPool = PoolHilos.getInstance().getThreadPool();
    }

    /**
     * Metodo getInstance para implementar patron singleton
     * @return Instancia unica de la clase.
     */
    public static Envio getInstance() {
        if (instance == null) {
            instance = new Envio();
        }
        return instance;
    }

    /**
     * Empieza el cliente del componente de red y 
     * el procesador de mensajes de salida
     */
    public void empezarEnvio() {    
        envio.startClient();
        threadPool.execute(procesador);
    }

    /**
     * Metodo para enviar un mensaje directo a un peer
     * @param peer Peer a quien se va a enviar el mensaje
     * @param mensaje Mensaje que se va a enviar
     */
    public void directMessage(PeerInfo peer, String mensaje) {
        envio.sendEvent(peer.getIp(), peer.getPort(), mensaje);
    }

    /**
     * Metodo para parar los hilos del
     * cliente y del procesador.
     */
    public void stop() {
        envio.stop();
        procesador.stop();
    }
}
