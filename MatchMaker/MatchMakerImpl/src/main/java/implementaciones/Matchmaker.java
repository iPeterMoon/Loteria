package implementaciones;

import eventos.Evento;
import interfaces.IPeer;

/**
 *
 * @author norma
 */
public class Matchmaker {

    private Sala sala;
    private IPeer peer;

    private static Matchmaker instance;

    private Matchmaker() {
    }

    public static Matchmaker getInstance() {
        if (instance == null) {
            instance = new Matchmaker();
        }
        return instance;
    }

    public void setPeer(IPeer peer) {
        this.peer = peer;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public void broadcast(Evento evento) {
        peer.broadcastEvento(evento);
    }
    
    public void directMessage(Evento evento, String user){
        peer.directMessage(evento, user);
    }

    //TODO: Enviar mensaje a jugadores de sala? DENISE
}
