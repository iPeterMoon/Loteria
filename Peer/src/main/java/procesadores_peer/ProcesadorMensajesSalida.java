package procesadores_peer;

import network.OutgoingMessageDispatcher;

/**
 * Clase encargada de procesar los mensajes salientes en el peer.
 * Se ejecuta en un hilo independiente mientras el componente de envío se
 * encuentra activo.
 *
 * @author Jp
 */
public class ProcesadorMensajesSalida extends ProcesadorMensajes{

    /**
     * Constructor.
     *
     * Inicializa la cadena de responsabilidad para los mensajes de salida,
     * estableciendo el orden de los manejadores.
     */
    public ProcesadorMensajesSalida() {
        // Inicializar manejadores
        ManejadorMensajesSalida envioHeartbeat = new ManejadorEnvioHeartbeat();
        ManejadorMensajesSalida mensajeDirecto = new ManejadorMensajeDirecto();
        ManejadorMensajesSalida broadcast = new ManejadorBroadcast();
        
        envioHeartbeat.setNext(mensajeDirecto);
        mensajeDirecto.setNext(broadcast);

        this.manejadorPrincipal = envioHeartbeat;
    }

    /**
     * Extrae mensajes de la cola de salida y los envía a la cadena de
     * responsabilidad para su procesamiento.
     */
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
