package red;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Clase que almacena el pool de hilos para los componentes de red
 * el pool de hilos lo comparten el componente de envio y el de recepcion
 * @author Peter
 */
public class PoolHilos {

    private final ExecutorService threadPool = Executors.newCachedThreadPool();
    private static PoolHilos instance;
    
    private PoolHilos() {
    }

    public static PoolHilos getInstance() {
        if(instance == null) {
            instance = new PoolHilos();
        }
        return instance;
    }

    public ExecutorService getThreadPool() {
        return threadPool;
    }
    
}
