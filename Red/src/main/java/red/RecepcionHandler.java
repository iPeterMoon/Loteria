package red;

import interfaces.IRecepcion;
import interfaces.IRedListener;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import servidor.ServidorRed;

/**
 * 
 * @author Peter
 */
public class RecepcionHandler implements IRecepcion{

    private static RecepcionHandler instance;
    
    //Gestión de hilos
    private final ExecutorService threadPool;
    
    private RecepcionHandler(){
        this.threadPool = PoolHilos.getInstance().getThreadPool();
    }

    public static RecepcionHandler getInstance(){
        if(instance == null){
            instance = new RecepcionHandler();
        }
        return instance;
    }
    
    //Colas
    private final BlockingQueue<String> incomingQueue = new LinkedBlockingQueue<>();
    
    private IRedListener listener;
    
    private volatile boolean isRunning = true;
    
    //Componentes internos
    private ServidorRed servidor;
    private Integer serverPort;
    
    @Override
    public int empezarEscucha() throws IOException {
        //Iniciar servidor
        if(serverPort == null){
            servidor = new ServidorRed(incomingQueue, threadPool);
            this.serverPort = servidor.getPort();
        } else {
            servidor = new ServidorRed(incomingQueue, threadPool, serverPort);
        }

        threadPool.submit(servidor);
        threadPool.submit(this::procesarColaEntrada);
        return this.serverPort;
    }
    
    private void procesarColaEntrada(){
        while(isRunning) {
            try {

                String mensaje = incomingQueue.take();
                if (listener != null){
                    listener.onMensajeRecibido(mensaje);
                }
            } catch(InterruptedException e) {
                if (isRunning) System.err.println("[RedImpl] Hilo de cola de entrada interrumpido.");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
    
    

    @Override
    public void setEventListener(IRedListener listener) {
        this.listener = listener;
    }
    
    @Override
    public void stop() {
        if (!isRunning) return;
        isRunning = false;
        
        // Detener el servidor.
        if (servidor != null) servidor.stop();
        
        // Detener el pool de hilos (interrumpe todos los hilos en espera)
        threadPool.shutdownNow(); 
        
        System.out.println("Componente de Recepción detenido.");
    }

    @Override
    public void setServerPort(int port) {
        this.serverPort = port;
    }
    
}
