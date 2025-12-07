package modelo;

import dtos.aplicacion.JugadorSalaEsperaDTO;
import dtos.JugadorSalaEsperaDTO;
import enums.TipoNivel;
import java.util.List;
import java.util.ArrayList;
import util.Subject;

/**
 *
 * @author norma
 */
public class SalaSubject extends Subject {

    private TipoNivel nivel;
    private int limiteJugadores;
    private List<JugadorSalaEsperaDTO> jugadores;

    public SalaSubject() {
        this.jugadores = new ArrayList<>();
    }

    public SalaSubject(TipoNivel nivel, int limiteJugadores, List<JugadorSalaEsperaDTO> jugadores) {
        this.nivel = nivel;
        this.limiteJugadores = limiteJugadores;
        this.jugadores = jugadores;
    }

    public TipoNivel getNivel() {
        return nivel;
    }

    public void setNivel(TipoNivel nivel) {
        this.nivel = nivel;
    }

    public int getLimiteJugadores() {
        return limiteJugadores;
    }

    public void setLimiteJugadores(int limiteJugadores) {
        this.limiteJugadores = limiteJugadores;
    }

    public List<JugadorSalaEsperaDTO> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<JugadorSalaEsperaDTO> jugadores) {
        this.jugadores = jugadores;
        notifyAllObservers();
    }
    
    public void actualizarDatosSala(int limiteJugadores, TipoNivel nivel){
        this.limiteJugadores = limiteJugadores;
        this.nivel = nivel;
        notifyAllObservers();
    }

}