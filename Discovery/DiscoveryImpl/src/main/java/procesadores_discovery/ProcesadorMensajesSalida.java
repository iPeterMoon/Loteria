
package procesadores_discovery;

import network.OutgoingMessageDispatcher;

/**
 *
 * @author Alici
 */
public class ProcesadorMensajesSalida extends ProcesadorMensajes{

    /**
     * Constructor, establece la cadena de responsbilidad, por ahora los unicos mensajes que salen son mensajes
     * de desconexión.
     */
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
