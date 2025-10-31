package red;

import cliente.ClienteRed;
import dtos.Mensaje;
import interfaces.IEnvio;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author Peter
 */
public class EnvioHandler implements IEnvio{
    
    private ClienteRed cliente;
    private final ExecutorService threadPool;
    private final BlockingQueue<Mensaje> outgoingQueue = new LinkedBlockingQueue<>();
    private static EnvioHandler instance;
    
    private EnvioHandler(){
        this.threadPool = PoolHilos.getInstance().getThreadPool();
    }
    
    public static EnvioHandler getInstance(){
        if(instance == null){
            instance = new EnvioHandler();
        }
        return instance;
    }
    
    @Override
    public void startClient() {
        cliente = new ClienteRed(outgoingQueue);
        threadPool.submit(cliente);
    }
    
    @Override
    public void sendEvent(String ip, int puerto, String mensaje) {
        try {
            outgoingQueue.put(new Mensaje(ip, puerto, mensaje));
        } catch (InterruptedException e) {
            System.err.println("[RedImpl] Interrumpido al intentar poner en cola de salida: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void stop() {
        if (cliente != null) cliente.stop();
        threadPool.shutdownNow();
    }

    
}
