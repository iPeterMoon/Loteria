package cliente;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import dtos.Mensaje;

/**
 *
 * @author norma
 */
public class OutgoingMessageDispatcher {

    private static BlockingQueue<Mensaje> outgoingQueue = new LinkedBlockingQueue<>();

    private OutgoingMessageDispatcher() {
    }

    public static void dispatch(Mensaje mensaje) {
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

    public static Mensaje take() throws InterruptedException {
        return outgoingQueue.take();
    }
}