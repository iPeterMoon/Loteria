package modelo;

import dtos.JugadorSalaEsperaDTO;
import java.util.List;
import java.util.ArrayList;
import util.Subject;

/**
 *
 * @author norma
 */
public class SalaSubject extends Subject {

    private String nivel;
    private int limiteJugadores;
    private List<JugadorSalaEsperaDTO> jugadores;

    public SalaSubject() {
        this.jugadores = new ArrayList<>();
    }

    public SalaSubject(String nivel, int limiteJugadores, List<JugadorSalaEsperaDTO> jugadores) {
        this.nivel = nivel;
        this.limiteJugadores = limiteJugadores;
        this.jugadores = jugadores;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
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

    public void agregarJugador(JugadorSalaEsperaDTO nuevoJugador) {
        if (this.jugadores == null) {
            this.jugadores = new ArrayList<>();
        }
        this.jugadores.add(nuevoJugador);
        notifyAllObservers();
    }

    public void eliminarJugador(String nickname) {
        if (this.jugadores != null) {
            this.jugadores.removeIf(j -> j.getNickname().equals(nickname));
            notifyAllObservers();
        }
    }

}