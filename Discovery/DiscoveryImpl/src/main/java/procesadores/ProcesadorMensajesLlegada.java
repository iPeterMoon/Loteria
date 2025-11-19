package procesadores;

import network.IncomingMessageDispatcher;

/**
 *
 * @author Alici
 */
public class ProcesadorMensajesLlegada extends ProcesadorMensajes {
    
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
