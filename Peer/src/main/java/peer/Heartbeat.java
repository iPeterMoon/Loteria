package peer;

import com.google.gson.Gson;

import dtos.PeerInfo;
import eventos.EventoHeartbeat;
import network.OutgoingMessageDispatcher;

public class Heartbeat implements Runnable {

    private final static long INTERVALO = 500; // Intervalo en milisegundos
    private final EventoHeartbeat eventoHeartbeat;
    private final Gson gson = new Gson();


    public Heartbeat(PeerInfo peer) {
        this.eventoHeartbeat = new EventoHeartbeat(peer, peer.getUser());
    }

    @Override
    public void run() {
        while (true) {
            enviarHeartbeat();
        }
    }
    
    private void enviarHeartbeat() {
        try {
            Thread.sleep(INTERVALO);
            enviarMensaje();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void enviarMensaje() {
        String mensaje = gson.toJson(eventoHeartbeat);
        OutgoingMessageDispatcher.dispatch(mensaje);
    }

}
