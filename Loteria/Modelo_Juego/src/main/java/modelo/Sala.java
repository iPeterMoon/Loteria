/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

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
    private Jugador host;

    private static Sala instance;

    private Sala() {
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

    public Jugador getHost() {
        return host;
    }

    public void setHost(Jugador host) {
        this.host = host;
    }

}
