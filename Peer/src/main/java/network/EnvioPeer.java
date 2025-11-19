package network;

import com.google.gson.Gson;
import dtos.PeerInfo;
import factory.RedFactory;
import interfaces.IEnvio;
import java.util.concurrent.ExecutorService;
import procesadores.ProcesadorMensajes;
import procesadores.ProcesadorMensajesSalida;
import utilPeer.PoolHilos;

/**
 *
 * @author Jp
 */
public class EnvioPeer{

    private final IEnvio envio;
    private static EnvioPeer instance;
    private final ExecutorService threadPool;
    private final ProcesadorMensajes procesador;

    private EnvioPeer() {
        this.envio = RedFactory.crearEnvioHandler();
        this.procesador = new ProcesadorMensajesSalida();
        this.threadPool = PoolHilos.getInstance().getThreadPool();
    }

    public static EnvioPeer getInstance() {
        if (instance == null) {
            instance = new EnvioPeer();
        }
        return instance;
    }

    public void empezarEnvio() {    
        envio.startClient();
        threadPool.execute(procesador);
    }

    public void directMessage(PeerInfo peer, String mensaje) {
        if (peer == null) {
            System.err.println("[EnvioPeer] Intento de enviar mensaje directo a Peer null. Mensaje descartado.");
            return;
        }
        envio.sendEvent(peer.getIp(), peer.getPort(), mensaje);
    }

    public void stop() {
        envio.stop();
        procesador.stop();
    }
}
