
package procesadores;

import network.OutgoingMessageDispatcher;

/**
 *
 * @author Alici
 */
public class ProcesadorMensajesSalida extends ProcesadorMensajes{

    public ProcesadorMensajesSalida() {

        // Inicializar manejadores
        ManejadorMensajesSalida desconexion = new ManejadorDesconexionPeer();

        this.manejadorPrincipal = desconexion;
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                String mensaje = OutgoingMessageDispatcher.take();
                procesar(mensaje);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

}
