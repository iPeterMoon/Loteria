package utilPeer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Clase que almacena el pool de hilos para los componentes de red el pool de
 * hilos lo comparten el componente de envio y el de recepcion
 *
 * @author Peter
 */
public class PoolHilos {

    /**
     * Pool de hilos utilizado por los componentes de red.
     */
    private final ExecutorService threadPool = Executors.newCachedThreadPool();

    /**
     * Instancia única de la clase.
     */
    private static PoolHilos instance;

    /**
     * Constructor de privado.
     */
    private PoolHilos() {
    }

    /**
     * Obtiene la instancia única del pool de hilos.
     *
     * @return Instancia de PoolHilos.
     */
    public static PoolHilos getInstance() {
        if (instance == null) {
            instance = new PoolHilos();
        }
        return instance;
    }

    /**
     * Devuelve el ExecutorService compartido.
     *
     * @return Pool de hilos de la aplicación.
     */
    public ExecutorService getThreadPool() {
        return threadPool;
    }

}
