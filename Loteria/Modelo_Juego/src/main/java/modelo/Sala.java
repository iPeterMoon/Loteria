/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.LinkedList;
import java.util.List;

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

    private static Sala instance;

    private Sala() {
        jugadoresSecundario = new LinkedList<>();
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

}
