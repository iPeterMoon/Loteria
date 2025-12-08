package red;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
    
    /**
     * Detiene el pool de hilos de forma ordenada para un cierre limpio de la aplicación.
     */
    public void shutdown() {
        if (threadPool != null && !threadPool.isShutdown()) {
            System.out.println(">>> [POOL] Iniciando cierre ordenado del Pool de Hilos.");
            
            // Deshabilita la aceptación de nuevas tareas.
            threadPool.shutdown(); 
            
            try {
                //  Espera un tiempo prudente  para que las tareas activas terminen.
                if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                    System.err.println(">>> [POOL] No todas las tareas terminaron. Forzando el cierre...");
                    
                    //  Si el tiempo de espera termina, fuerza la interrupción de las tareas pendientes.
                    threadPool.shutdownNow(); 
                    
                    //  Espera nuevamente un corto periodo para confirmar la terminación forzada.
                    if (!threadPool.awaitTermination(10, TimeUnit.SECONDS)) {
                        System.err.println(">>> [POOL] El pool no se detuvo.");
                    }
                }
            } catch (InterruptedException ie) {
                // (Re-interrupción si el hilo actual es interrumpido)
                threadPool.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }
}
