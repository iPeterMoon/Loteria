package network;

import factory.RedFactory;
import interfaces.IRecepcion;
import interfaces.IRedListener;
import util.ConfigLoader;

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
     * @param puertoFijo Puerto específico a usar, o null para usar el puerto
     * por defecto configurado (puerto_peer).
     * @return El puerto en el que se está escuchando, o -1 si ocurre un error.
     */
    public synchronized int start(Integer puertoFijo) {
        if (running) {
            return -1;
        }
        recepcion.setEventListener(this);
        int puertoDeseado = (puertoFijo != null) ? puertoFijo : ConfigLoader.getInstance().getPuertoPeer();
        recepcion.setServerPort(puertoDeseado);
        try {
            int puertoAsignado = recepcion.empezarEscucha();
            running = true;
            new Thread(this, "RecepcionHandler-Thread").start();
            System.out.println("[RecepcionHandler] Escuchando en puerto: " + puertoAsignado);
            return puertoAsignado;
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
