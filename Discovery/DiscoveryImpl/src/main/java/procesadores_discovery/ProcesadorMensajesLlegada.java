package procesadores_discovery;

import network.IncomingMessageDispatcher;

/**
 *
 * @author Alici
 */
public class ProcesadorMensajesLlegada extends ProcesadorMensajes {
    
    /**
     * Inicializa la cadena de responsabilidad, primero maneja los heartbeats
     * porque son los que mas llegan, y luego maneja los nuevosPeers.
     */
    public ProcesadorMensajesLlegada() {

        // Inicializar manejadores
        ManejadorMensajesLlegada heartbeat = new ManejadorHeartbeat();
        ManejadorMensajesLlegada nuevoPeer = new ManejadorNuevoPeer();
        
        heartbeat.setNext(nuevoPeer);
        
        this.manejadorPrincipal = heartbeat;
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
