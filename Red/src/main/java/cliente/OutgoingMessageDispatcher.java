package cliente;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import dtos.Mensaje;

/**
 * Clase encargada de gestionar la cola de mensajes de salida.
 *
 * @author norma
 */
public class OutgoingMessageDispatcher {

    /**
     * Utiliza una BlockingQueue para almacenar los mensajes pendientes y
     * bloquear el hilo consumidor hasta que exista un mensaje disponible.
     */
    private static BlockingQueue<Mensaje> outgoingQueue = new LinkedBlockingQueue<>();

    private OutgoingMessageDispatcher() {
    }

    /**
     * Agrega un mensaje a la cola de salida para su posterior envío.
     * 
     * Si el mensaje es válido, se inserta en la cola de forma segura.
     * En caso de error o interrupción del hilo, el mensaje no es procesado.
     * 
     * @param mensaje Mensaje que se desea enviar.
     */
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

    /**
     * Obtiene el siguiente mensaje de la cola de salida.
     * 
     * @return Mensaje a enviar.
     * @throws InterruptedException Si el hilo es interrumpido mientras espera.
     */
    public static Mensaje take() throws InterruptedException {
        return outgoingQueue.take();
    }
}