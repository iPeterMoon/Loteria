package red;

import interfaces.IRecepcion;
import interfaces.IRedListener;
import java.io.IOException;
import java.util.concurrent.ExecutorService;

import servidor.IncomingMessageDispatcher;
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
    
    private IRedListener listener;
    
    private volatile boolean isRunning = true;
    
    //Componentes internos
    private ServidorRed servidor;
    private Integer serverPort;
    
    @Override
    public int empezarEscucha() throws IOException {
        //Iniciar servidor
        if(serverPort == null){
            servidor = new ServidorRed();
            this.serverPort = servidor.getPort();
        } else {
            servidor = new ServidorRed(serverPort);
        }

        threadPool.submit(servidor);
        threadPool.submit(this::procesarColaEntrada);
        return this.serverPort;
    }
    

    /**
     * Procesa constantemente los mensajes entrantes de la cola de entrada.
     */
    private void procesarColaEntrada(){
        while(isRunning) {
            try {
                String mensaje = IncomingMessageDispatcher.take();
                avisarListener(mensaje);
            } catch(InterruptedException e) {
                if (isRunning) System.err.println("[RedImpl] Hilo de cola de entrada interrumpido.");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    /**
     * Avisa al listener del componente de red que llegó un mensaje
     * @param mensaje
     */
    private void avisarListener(String mensaje){
        if (listener != null){
            listener.onMensajeRecibido(mensaje);
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
