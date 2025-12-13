package red;

import cliente.ClienteRed;
import cliente.OutgoingMessageDispatcher;
import dtos.Mensaje;
import interfaces.IEnvio;
import java.util.concurrent.ExecutorService;

/**
 * Implementación del componente de envío de mensajes de red.
 *
 * @author Peter
 */
public class EnvioHandler implements IEnvio {

    /**
     * Cliente de red encargado de procesar la cola de mensajes de salida.
     */
    private ClienteRed cliente;

    /**
     * Pool de hilos compartido para la ejecución del cliente de red.
     */
    private final ExecutorService threadPool;

    /**
     * Instancia única del EnvioHandler.
     */
    private static EnvioHandler instance;

    /**
     * Constructor privado. Inicializa el pool de hilos utilizado para el envío
     * de mensajes.
     */
    private EnvioHandler() {
        this.threadPool = PoolHilos.getInstance().getThreadPool();
    }

    /**
     * Obtiene la instancia única del EnvioHandler.
     *
     * @return Instancia única del manejador de envío.
     */
    public static EnvioHandler getInstance() {
        if (instance == null) {
            instance = new EnvioHandler();
        }
        return instance;
    }

    /**
     * Inicia el cliente de red encargado de enviar mensajes. El cliente se
     * ejecuta dentro del pool de hilos.
     */
    @Override
    public void startClient() {
        cliente = new ClienteRed();
        threadPool.submit(cliente);
    }

    /**
     * Envía un evento a un destinatario específico.
     *
     * El mensaje se agrega a la cola de salida y será procesado de forma
     * asíncrona por el cliente de red.
     *
     * @param ip IP del destinatario.
     * @param puerto Puerto del destinatario.
     * @param mensaje Contenido del mensaje a enviar.
     */
    @Override
    public void sendEvent(String ip, int puerto, String mensaje) {
        OutgoingMessageDispatcher.dispatch(new Mensaje(ip, puerto, mensaje));
    }

    /**
     * Detiene el cliente de red y finaliza la ejecución de los hilos utilizados
     * para el envío de mensajes.
     */
    @Override
    public void stop() {
        if (cliente != null) {
            cliente.stop();
        }
        threadPool.shutdownNow();
    }

}
