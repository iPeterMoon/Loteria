package network;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import dtos.PeerInfo;
import factory.RedFactory;
import interfaces.IEnvio;
import java.util.concurrent.ExecutorService;
import procesadorEventos.ManejadorBroadcast;
import procesadorEventos.ProcesadorMensajesSalida;
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
    private final Gson gson = new Gson();

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
        envio.sendEvent(peer.getIp(), peer.getPort(), mensaje);
    }

    public void stop() {
        envio.stop();
        procesador.stop();
    }
}
