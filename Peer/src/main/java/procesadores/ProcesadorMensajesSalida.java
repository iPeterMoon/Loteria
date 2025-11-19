package procesadores;

import network.OutgoingMessageDispatcher;


/**
 *
 * @author Jp
 */
public class ProcesadorMensajesSalida extends ProcesadorMensajes{

    public ProcesadorMensajesSalida() {
        // Inicializar manejadores
        ManejadorMensajesSalida envioHeartbeat = new ManejadorEnvioHeartbeat();
        ManejadorMensajesSalida mensajeDirecto = new ManejadorMensajeDirecto();
        ManejadorMensajesSalida broadcast = new ManejadorBroadcast();
        
        envioHeartbeat.setNext(mensajeDirecto);
        mensajeDirecto.setNext(broadcast);

        this.manejadorPrincipal = envioHeartbeat;
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
