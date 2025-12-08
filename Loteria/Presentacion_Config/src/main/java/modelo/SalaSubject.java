package modelo;

import dtos.aplicacion.JugadorSalaEsperaDTO;
import enums.TipoNivel;
import java.util.List;
import java.util.ArrayList;
import util.Subject;

/**
 *
 * @author norma
 */
public class SalaSubject extends Subject {

    private String hostUser;
    private TipoNivel nivel;
    private int limiteJugadores;
    private List<JugadorSalaEsperaDTO> jugadores;
    private String jugadorPrincipalUser;

    private static SalaSubject instance;
    
    private SalaSubject() {
        this.jugadores = new ArrayList<>();
    }
    
    public static SalaSubject getInstance(){
        if(instance == null){
            instance = new SalaSubject();
        }
        return instance;
    }

    public void configurarSala(String hostUser,TipoNivel nivel, int limiteJugadores, List<JugadorSalaEsperaDTO> jugadores) {
        this.hostUser = hostUser;
        this.nivel = nivel;
        this.limiteJugadores = limiteJugadores;
        this.jugadores = jugadores;
    }

    public String getHost() {
        return hostUser;
    }

    public void setHostUser(String hostUser) {
        this.hostUser = hostUser;
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

    public void actualizarDatosSala(String host, int limiteJugadores, TipoNivel nivel) {
        this.hostUser = host;
        this.limiteJugadores = limiteJugadores;
        this.nivel = nivel;
        notifyAllObservers();
    }

    public String getJugadorPrincipalUser() {
        return jugadorPrincipalUser;
    }

    public void setJugadorPrincipalUser(String jugadorPrincipalUser) {
        System.out.println("[SalaSubject] Seteando jugadorPrincipalUser: " + jugadorPrincipalUser);
        this.jugadorPrincipalUser = jugadorPrincipalUser;
        System.out.println("[SalaSubject] Notificando observadores...");
        notifyAllObservers();
    }
    
    

}
