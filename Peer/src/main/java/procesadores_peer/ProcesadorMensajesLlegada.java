package procesadores_peer;

import network.IncomingMessageDispatcher;

/**
 * Clase encargada de procesar los mensajes recibidos desde la red en el peer.
 * Se ejecuta en un hilo independiente mientras el componente de recepción se
 * encuentra activo.
 *
 * @author Jp
 */
public class ProcesadorMensajesLlegada extends ProcesadorMensajes {

    /**
     * Constructor.
     *
     * Inicializa la cadena de responsabilidad para los mensajes de llegada,
     * estableciendo el orden de los manejadores.
     */
    public ProcesadorMensajesLlegada() {
        // Inicializar manejadores
        ManejadorMensajesLlegada nuevoPeer = new ManejadorNuevoPeer();
        ManejadorEventoDesconexion eventoDesconexion = new ManejadorEventoDesconexion();
        ManejadorMensajesLlegada eventoJuego = new ManejadorEventoApplication();

        nuevoPeer.setNext(eventoDesconexion);
        eventoDesconexion.setNext(eventoJuego);

        this.manejadorPrincipal = nuevoPeer;
    }

    /**
     * Extrae mensajes de la cola de entrada y los envía a la cadena de
     * responsabilidad para su procesamiento.
     */
    @Override
    public void run() {
        while (isRunning) {
            try {
                String mensaje = IncomingMessageDispatcher.take();
                procesar(mensaje);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
