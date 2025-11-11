package red;

import cliente.ClienteRed;
import cliente.OutgoingMessageDispatcher;
import dtos.Mensaje;
import interfaces.IEnvio;
import java.util.concurrent.ExecutorService;

/**
 *
 * @author Peter
 */
public class EnvioHandler implements IEnvio{
    
    private ClienteRed cliente;
    private final ExecutorService threadPool;
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
        cliente = new ClienteRed();
        threadPool.submit(cliente);
    }
    
    @Override
    public void sendEvent(String ip, int puerto, String mensaje) {
        OutgoingMessageDispatcher.dispatch(new Mensaje(ip, puerto, mensaje));
    }

    @Override
    public void stop() {
        if (cliente != null) cliente.stop();
        threadPool.shutdownNow();
    }

    
}
