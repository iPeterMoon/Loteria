package network;

import factory.RedFactory;
import interfaces.IRecepcion;
import interfaces.IRedListener;

/**
 * Clase encargada de escuchar los mensajes entrantes provenientes de la red y
 * redirigirlos al sistema interno del peer.
 *
 * @author Jp
 */
public class RedListener implements Runnable, IRedListener {

    /**
     * Handler de recepción de la red.
     */
    private final IRecepcion recepcion;

    /**
     * Indica si el listener se encuentra en ejecución.
     */
    private volatile boolean running = false;

    /**
     * Constructor.
     *
     * Inicializa el handler de recepción de red utilizando la fábrica.
     */
    public RedListener() {
        this.recepcion = RedFactory.crearRecepcionHandler();
    }

    /**
     * Inicia la escucha de mensajes entrantes.
     *
     * Configura este objeto como listener de eventos de red, inicia la escucha
     * en un puerto disponible y ejecuta el listener en un hilo independiente.
     *
     * @return El puerto en el que se está escuchando, o -1 si ocurre un error.
     */
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

    /**
     * Método invocado por el componente de red al detectar la llegada de un
     * nuevo mensaje.
     *
     * El mensaje recibido se valida y se envía al despachador de mensajes
     * entrantes para su posterior procesamiento.
     *
     * @param mensaje Mensaje recibido desde la red.
     */
    @Override
    public void onMensajeRecibido(String mensaje) {
        if (mensaje != null && !mensaje.isBlank()) {
            IncomingMessageDispatcher.dispatch(mensaje);
        }
    }

    /**
     * Mantiene el hilo activo mientras el listener esté en ejecución.
     */
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
     * Detiene la escucha de mensajes y libera los recursos asociados.
     */
    public synchronized void stop() {
        if (!running) {
            return;
        }
        running = false;
        recepcion.stop();
        System.out.println("[RecepcionHandler] Detenido.");
    }
}
