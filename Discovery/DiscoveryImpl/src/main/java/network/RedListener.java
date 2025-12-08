package network;

import factory.RedFactory;
import interfaces.IRecepcion;
import interfaces.IRedListener;
import util.ConfigLoader;

/**
 *
 * @author Alicia
 */
public class RedListener implements Runnable, IRedListener {

    private static final int PUERTO = ConfigLoader.getInstance().getPuertoDiscovery();

    private final IRecepcion recepcion;
    private volatile boolean running = false;

    public RedListener() {
        this.recepcion = RedFactory.crearRecepcionHandler();
    }

    /**
     * Empieza la recepción de mensajes inicializando el componente de red.
     */
    public synchronized void start() {
        if (running) {
            return;
        }
        recepcion.setEventListener(this);
        try {

            recepcion.setServerPort(PUERTO);
            recepcion.empezarEscucha();
            recepcion.setEventListener(this);

            running = true;
            new Thread(this, "RecepcionHandler-Thread").start();
            System.out.println("[RedListener] Escuchando en puerto: " + PUERTO);
        } catch (Exception e) {
            System.err.println("[RedListener] Error al iniciar: " + e.getMessage());
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

    /**
     * Para los hilos del componente de red.
     */
    public synchronized void stop() {
        if (!running) {
            return;
        }
        running = false;
        recepcion.stop();
        System.out.println("[RedListener] Detenido.");
    }
}
