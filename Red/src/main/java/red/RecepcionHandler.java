package red;

import interfaces.IRecepcion;
import interfaces.IRedListener;
import java.io.IOException;
import java.util.concurrent.ExecutorService;

import servidor.IncomingMessageDispatcher;
import servidor.ServidorRed;

/**
 * Implementación del componente de recepción de mensajes desde la red.
 *
 * @author Peter
 */
public class RecepcionHandler implements IRecepcion {

    /**
     * Instancia única del manejador de recepción.
     */
    private static RecepcionHandler instance;

    /**
     * Gestión de hilos.
     */
    private final ExecutorService threadPool;

    /**
     * Listener que serpa notificado cuando se reciba un mensaje desde la red.
     */
    private IRedListener listener;

    /**
     * Indica si el componente de recepción se encuentra en ejecución.
     */
    private volatile boolean isRunning = true;

    /**
     * Componentes internos.
     */
    private ServidorRed servidor;
    private Integer serverPort;

    /**
     * Constructor privado. Obtiene el pool de hilos compartidos para ejecutar
     * los procesos de red.
     */
    private RecepcionHandler() {
        this.threadPool = PoolHilos.getInstance().getThreadPool();
    }

    /**
     * Obtiene la instancia única del RecepcionHandler
     *
     * @return Instancia única del manejador de recepción.
     */
    public static RecepcionHandler getInstance() {
        if (instance == null) {
            instance = new RecepcionHandler();
        }
        return instance;
    }

    /**
     * Inicia el servidor de escucha y el procesamiento de la cola de entrada.
     *
     * @return Puerto en el que el servidor está escuchando.
     * @throws IOException Si ocurre un error al iniciar el servidor.
     */
    @Override
    public int empezarEscucha() throws IOException {
        //Iniciar servidor
        if (serverPort == null) {
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
    private void procesarColaEntrada() {
        while (isRunning) {
            try {
                String mensaje = IncomingMessageDispatcher.take();
                avisarListener(mensaje);
            } catch (InterruptedException e) {
                if (isRunning) {
                    System.err.println("[RedImpl] Hilo de cola de entrada interrumpido.");
                }
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    /**
     * Avisa al listener del componente de red que llegó un mensaje
     *
     * @param mensaje
     */
    private void avisarListener(String mensaje) {
        if (listener != null) {
            listener.onMensajeRecibido(mensaje);
        }
    }

    /**
     * Registra el listener que será notificado.
     *
     * @param listener Componente interesado en procesar los mensajes recibidos.
     */
    @Override
    public void setEventListener(IRedListener listener) {
        this.listener = listener;
    }

    /**
     * Detiene el componente de recepción, cerrando el servidor y deteniendo los
     * hilos asociados.
     */
    @Override
    public void stop() {
        if (!isRunning) {
            return;
        }
        isRunning = false;

        // Detener el servidor.
        if (servidor != null) {
            servidor.stop();
        }

        // Detener el pool de hilos (interrumpe todos los hilos en espera)
        threadPool.shutdownNow();

        System.out.println("Componente de Recepción detenido.");
    }

    /**
     * Configura el puerto en el que el servidor de red escuchará.
     *
     * @param port Puerto de escucha.
     */
    @Override
    public void setServerPort(int port) {
        this.serverPort = port;
    }

}
