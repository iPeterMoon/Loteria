package peer;

import com.google.gson.Gson;

import dtos.peer.PeerInfo;
import eventos.eventos_peers.EventoHeartbeat;
import network.OutgoingMessageDispatcher;

/**
 * Hilo encargado de enviar periodicamente mensajes de heartbeat.
 *
 * @author Jp
 */
public class Heartbeat implements Runnable {

    /**
     * Intervalo de envío en milisegundos.
     */
    private final static long INTERVALO = 500;
    
    /**
     * Evento de heartbeat asociado al peer.
     */
    private final EventoHeartbeat eventoHeartbeat;

    /**
     * Serializador JSON.
     */
    private final Gson gson = new Gson();

    /**
     * Controla el ciclo de ejecución del hilo.
     */
    private volatile boolean isRunning = true;

    /**
     * Crea un nuevo hilo de heartbeat para un peer específico.
     *
     * @param peer Info del peer que enviará el heartbeat.
     */
    public Heartbeat(PeerInfo peer) {
        this.eventoHeartbeat = new EventoHeartbeat(peer, peer.getUser());
    }

    /**
     * Envía periódicamente mensajes de heartbeat a través del despachador de
     * salida.
     */
    @Override
    public void run() {
        while (isRunning) {
            enviarHeartbeat();
        }
        System.out.println("[Heartbeat] Detenido.");
    }

    /**
     * Controla el intervalo entre envíos.
     */
    private void enviarHeartbeat() {
        try {
            Thread.sleep(INTERVALO);
            if (isRunning) {
                enviarMensaje();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Selializa el evento de heartbeat y lo envía a la cola.
     */
    private void enviarMensaje() {
        String mensaje = gson.toJson(eventoHeartbeat);
        OutgoingMessageDispatcher.dispatch(mensaje);
    }

    /**
     * Detiene el envío de heartbeats y finaliza el hilo.
     */
    public void stop() {
        isRunning = false;
    }

}
