/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import util.Subject;

/**
 * Clase que representa una jugada dentro del juego, implementada como un sujeto
 * (Subject) dentro del patrón de diseño Observer.
 *
 * Permite registrar y notificar observadores cuando cambian las propiedades de
 * la jugada, como el estado.
 *
 * @author rocha
 */
public class JugadaSubject extends Subject {

    /**
     * Nombre de la jugada.
     */
    private String nombre;

    private String jugador;

    /**
     * Cantidad de puntos que otorga la jugada.
     */
    private int puntos;

    /**
     * Constructor vacío.
     */
    public JugadaSubject() {
    }

    /**
     * Constructor que inicializa una jugada con los valores dados.
     *
     * @param nombre Nombre de la jugada.
     * @param jugador
     * @param puntos Puntos que otorga la jugada.
     */
    public JugadaSubject(String nombre, String jugador, int puntos) {
        this.nombre = nombre;
        this.jugador = jugador;
        this.puntos = puntos;
    }

    /**
     * Método para obtener el nombre de la jugada.
     *
     * @return Cadena que representa el nombre de la jugada.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Método para asignar un nuevo nombre a la jugada.
     *
     * @param nombre Cadena con el nombre de la jugada.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getJugador() {
        return jugador;
    }

    public void setJugador(String jugador) {
        this.jugador = jugador;
    }

    /**
     * Método para obtener la cantidad de puntos de la jugada.
     *
     * @return Entero que representa los puntos que otorga la jugada.
     */
    public int getPuntos() {
        return puntos;
    }

    /**
     * Método para asignar un nuevo valor de puntos a la jugada.
     *
     * @param puntos Entero con los puntos que otorga la jugada.
     */
    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    @Override
    public String toString() {
        return "JugadaSubject{" + "nombre=" + nombre + ", jugador=" + jugador + ", puntos=" + puntos + '}';
    }

}
