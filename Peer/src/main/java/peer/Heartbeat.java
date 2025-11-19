package peer;

import com.google.gson.Gson;

import dtos.PeerInfo;
import eventos.eventos_peers.EventoHeartbeat;
import network.OutgoingMessageDispatcher;

public class Heartbeat implements Runnable {

    private final static long INTERVALO = 500; // Intervalo en milisegundos
    private final EventoHeartbeat eventoHeartbeat;
    private final Gson gson = new Gson();
    private volatile boolean isRunning = true;


    public Heartbeat(PeerInfo peer) {
        this.eventoHeartbeat = new EventoHeartbeat(peer, peer.getUser());
    }

    @Override
    public void run() {
        while (isRunning) {
            enviarHeartbeat();
        }
        System.out.println("[Heartbeat] Detenido.");
    }
    
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

    private void enviarMensaje() {
        String mensaje = gson.toJson(eventoHeartbeat);
        OutgoingMessageDispatcher.dispatch(mensaje);
    }

    public void stop() {
        isRunning = false;
    }

}
