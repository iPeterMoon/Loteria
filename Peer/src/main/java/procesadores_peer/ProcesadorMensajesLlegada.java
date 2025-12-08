package procesadores_peer;

import network.IncomingMessageDispatcher;


/**
 *
 * @author Jp
 */
public class ProcesadorMensajesLlegada extends ProcesadorMensajes{

    public ProcesadorMensajesLlegada() {
        // Inicializar manejadores
        ManejadorMensajesLlegada nuevoPeer = new ManejadorNuevoPeer();
        ManejadorEventoDesconexion eventoDesconexion = new ManejadorEventoDesconexion();
        ManejadorMensajesLlegada eventoJuego = new ManejadorEventoApplication();

        nuevoPeer.setNext(eventoDesconexion);
        eventoDesconexion.setNext(eventoJuego);

        this.manejadorPrincipal = nuevoPeer;
    }

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
