package network;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Clase encargada de gestionar la cola de mensajes de salida en el componente
 * de Peer, que serán enviados a otros peers.
 *
 * @author norma
 */
public class OutgoingMessageDispatcher {

    /**
     * Cola bloquean que almacena los mensajes de salida.
     */
    private static BlockingQueue<String> outgoingQueue = new LinkedBlockingQueue<>();

    /**
     * Inserta un mensaje en la cola de salida.
     *
     * @param mensaje Mensaje que será enviado.
     */
    public static void dispatch(String mensaje) {
        if (mensaje == null || outgoingQueue == null) {
            System.err.println("Error: Mensaje o cola de salida nulos en el despachador.");
            return;
        }
        try {
            outgoingQueue.put(mensaje);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Error al despachar mensaje de salida: " + e.getMessage());
        }
    }

    /**
     * Obtiene y elimina el siguiente mensaje disponible en la cola de salida.
     *
     * @return Mensaje a enviar.
     * @throws InterruptedException Si el hilo es interrumpido mientras espera.
     */
    public static String take() throws InterruptedException {
        return outgoingQueue.take();
    }
}
