/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import enums.JugadasDisponibles;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Alici
 */
public class Sala {

    /**
     * Lista de jugadores secundarios al jugador de la vista principal
     */
    private List<Jugador> jugadoresSecundario;
    /**
     * Jugador principal que tiene la vista principal
     */
    private Jugador jugadorPrincipal;
    /**
     * Jugador host de la ronda
     */
    private String host;

    private ConfiguracionJuego configuracion;

    private Map<JugadasDisponibles, Boolean> estaDisponible;

    private static Sala instance;

    private Sala() {
        jugadoresSecundario = new LinkedList<>();

        estaDisponible = new HashMap<>();
        resetearJugadasDisponibles();
    }

    public static Sala getInstance() {
        if (instance == null) {
            instance = new Sala();
        }
        return instance;
    }

    /**
     * Método para obtener el jugador principal, es decir el que es dueño de la
     * vista
     *
     * @return Jugador dueño de la vista
     */
    public Jugador getJugadorPrincipal() {
        return jugadorPrincipal;
    }

    /**
     * Método para asignar el jugador principal, es decir el que es dueño de la
     * vista
     *
     * @param jugadorPrincipal Jugador que es dueño de la vista
     */
    public void setJugadorPrincipal(Jugador jugadorPrincipal) {
        this.jugadorPrincipal = jugadorPrincipal;
    }

    public List<Jugador> getJugadoresSecundario() {
        return jugadoresSecundario;
    }

    public void setJugadoresSecundario(List<Jugador> jugadoresSecundario) {
        this.jugadoresSecundario = jugadoresSecundario;
    }

    public void agregarJugadorSecundario(Jugador jugador) {
        jugadoresSecundario.add(jugador);
    }

    public ConfiguracionJuego getConfiguracion() {
        return configuracion;
    }

    public void setConfiguracion(ConfiguracionJuego configuracion) {
        this.configuracion = configuracion;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public boolean salaCreada() {
        return host != null && configuracion != null;
    }
    
    public boolean isInSala(String user){
        if (jugadorPrincipal.getNickname().equals(user)){
            return true;
        }
        for(Jugador jugador : jugadoresSecundario){
            if(jugador.getNickname().equals(user)){
                return true;
            }
        }
        return false;
    }

    public void resetearJugadasDisponibles() {
        for (JugadasDisponibles jugada : JugadasDisponibles.values()) {
            estaDisponible.put(jugada, Boolean.TRUE);
        }
    }

    public void agregarPuntaje(String nickname, JugadasDisponibles jugada) {
        estaDisponible.put(jugada, Boolean.FALSE);

        for (Jugador jugador : jugadoresSecundario) {
            if (jugador.getNickname().equals(nickname)) {
                jugador.setPuntos(jugador.getPuntos() + configuracion.getPuintajes().get(jugada));
                return;
            }
        }
        if (jugadorPrincipal.getNickname().equals(nickname)) {
            jugadorPrincipal.setPuntos(jugadorPrincipal.getPuntos() + configuracion.getPuintajes().get(jugada));
        }
        
        imprimirPuntajes();
    }

    private void imprimirPuntajes() {
        for (Jugador jugador : jugadoresSecundario) {
            System.out.println(jugador.getNickname() + ": " + jugador.getPuntos());
        }
        System.out.println(jugadorPrincipal.getNickname() + ": " + jugadorPrincipal.getPuntos());
    }

}
