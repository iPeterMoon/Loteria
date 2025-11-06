package network;

import java.util.concurrent.BlockingQueue;

/**
 *
 * @author pedro
 */
public class MessageDispatcher implements Runnable {

    private final BlockingQueue<String> queue;
    private final String mensaje;

    public MessageDispatcher(String mensaje, BlockingQueue<String> incomingQueue) {
        this.mensaje = mensaje;
        this.queue = incomingQueue;
    }

    @Override
    public void run() {
        try {
            queue.put(mensaje);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

}
