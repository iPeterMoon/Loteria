package network;

import java.util.concurrent.ExecutorService;
import procesadores_discovery.ProcesadorMensajes;
import procesadores_discovery.ProcesadorMensajesLlegada;
import util_discovery.PoolHilos;

/**
 *
 * @author Alicia
 */
public class Recepcion {

    private static Recepcion instance;

    private final ExecutorService threadPool;

    private Recepcion() {
        this.threadPool = PoolHilos.getInstance().getThreadPool();
    }

    public static Recepcion getInstance() {
        if (instance == null) {
            instance = new Recepcion();
        }
        return instance;
    }

    private ProcesadorMensajes procesador;
    private RedListener redListener;

    /**
     * Empieza la recepción de mensajes
     */
    public void empezarEscucha() {
        redListener = new RedListener();
        procesador = new ProcesadorMensajesLlegada();
        redListener.start();
        ejecutarHilos();
    }

    /**
     * Ejecuta los hilos del redListener y del 
     * procesador de mensajes por separado
     */
    private void ejecutarHilos() {
        threadPool.execute(redListener);
        threadPool.execute(procesador);
    }

    /**
     * Para la ejecución de la recepción de mensajes  
     */ 
    public void stop() {
        redListener.stop();
        procesador.stop();
    }

}
