package network;

import com.google.gson.Gson;
import dtos.PeerInfo;
import eventos.Evento;
import factory.RedFactory;
import interfaces.IEnvio;
import java.util.concurrent.BlockingQueue;
import peer.PeersConectados;

/**
 *
 * @author Jp
 */
public class EnvioPeer implements Runnable {

    private final Gson gson = new Gson();
    private final BlockingQueue<String> outgoingQueue;
    private final IEnvio envio;
    private volatile boolean isRunning = false;

    public EnvioPeer(BlockingQueue<String> queue) {
        this.outgoingQueue = queue;
        this.envio = RedFactory.crearEnvioHandler();
    }

    @Override
    public void run() {
        start();
        while (isRunning || !outgoingQueue.isEmpty()) {
            try {
                String evento = outgoingQueue.take();
                broadcast(evento);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
    
    private void start() {
        if (isRunning) {
            return;
        }
        envio.startClient();
        isRunning = true;
        new Thread(this, "EnvioHandler-Thread").start();
    }

    private void broadcast(String evento) {
        PeersConectados peers = PeersConectados.getInstance();
        peers.ejecutarEnTodos(evento, this::directMessage);
    }
    
    private void directMessage( PeerInfo peer, String mensaje){
        envio.sendEvent(peer.getIp(), peer.getPort(), mensaje);
    }

    public void stop() {
        isRunning = false;
        envio.stop();
    }
}
