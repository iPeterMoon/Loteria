package network;

import factory.RedFactory;
import interfaces.IRecepcion;
import interfaces.IRedListener;

/**
 *
 * @author Jp
 */
public class RedListener implements Runnable, IRedListener {

    private final IRecepcion recepcion;
    private volatile boolean running = false;

    public RedListener() {
        this.recepcion = RedFactory.crearRecepcionHandler();
    }

    public synchronized int start() {
        if (running) {
            return -1;
        }
        recepcion.setEventListener(this);
        try {
            int puerto = recepcion.empezarEscucha();
            running = true;
            new Thread(this, "RecepcionHandler-Thread").start();
            System.out.println("[RecepcionHandler] Escuchando en puerto: " + puerto);
            return puerto;
        } catch (Exception e) {
            System.err.println("[RecepcionHandler] Error al iniciar: " + e.getMessage());
            return -1;
        }
    }

    @Override
    public void onMensajeRecibido(String mensaje) {
        if (mensaje != null && !mensaje.isBlank()) {
            IncomingMessageDispatcher.dispatch(mensaje);
        }
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public synchronized void stop() {
        if (!running) {
            return;
        }
        running = false;
        recepcion.stop();
        System.out.println("[RecepcionHandler] Detenido.");
    }
}
