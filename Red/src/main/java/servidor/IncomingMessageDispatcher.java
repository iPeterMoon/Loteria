package servidor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Clase encargada de gestionar la cola de mensajes entrantes.
 *
 * @author norma
 */
public class IncomingMessageDispatcher {

    /**
     * Cola bloqueante que almacena los mensajes entrantes.
     */
    private static BlockingQueue<String> incomingQueue = new LinkedBlockingQueue<>();

    /**
     * Constructor privado.
     */
    private IncomingMessageDispatcher() {
    }

    /**
     * Inserta un mensaje recibido en la cola de entrada.
     *
     * @param mensaje Mensaje recibido desde la red.
     */
    public static void dispatch(String mensaje) {
        if (mensaje == null || incomingQueue == null) {
            System.err.println("Error: Mensaje o cola de entrada nulos en el despachador.");
            return;
        }
        try {
            incomingQueue.put(mensaje);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Error al despachar mensaje de entrada: " + e.getMessage());
        }
    }

    /**
     * Obtiene y elimina el siguiente mensaje disponible en la cola de entrada.
     *
     * @return Mensaje recibido desde la red.
     * @throws InterruptedException Si el hilo es interrumpido mientras espera.
     */
    public static String take() throws InterruptedException {
        return incomingQueue.take();
    }
}
