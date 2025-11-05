
package matchmaker;

import java.util.LinkedList;
import java.util.List;

import dtos.PeerInfo;
import excepciones.ExceedLimitException;

public class Sala {
    private final List<PeerInfo> jugadores;
    private final int limiteJugadores;

    public Sala(int limiteJugadores){
        this.jugadores = new LinkedList<>();
        this.limiteJugadores = limiteJugadores;
    }

    public void agregarJugador(PeerInfo jugador) throws ExceedLimitException{
        if(jugadores.size() < limiteJugadores){
            this.jugadores.add(jugador);
        } else {
            throw new ExceedLimitException("La sala esta llena");
        }
    }
}