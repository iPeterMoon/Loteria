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

    /**
     * Estado actual de la jugada.
     */
    private boolean estado;

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
     * @param estado Estado de la jugada (activa o no).
     * @param puntos Puntos que otorga la jugada.
     */
    public JugadaSubject(String nombre, boolean estado, int puntos) {
        this.nombre = nombre;
        this.estado = estado;
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

    /**
     * Método para obtener el estado actual de la jugada.
     *
     * @return true si la jugada ya fue cantada, false en caso contrario.
     */
    public boolean isEstado() {
        return estado;
    }

    /**
     * Método para asignar un nuevo estado a la jugada.
     *
     * @param estado Valor booleano que indica el estado de la jugada.
     */
    public void setEstado(boolean estado) {
        this.estado = estado;
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

    /**
     * Método que devuelve una representación en cadena del estado de la jugada,
     * incluyendo nombre, estado y puntos.
     *
     * @return Cadena con la información de la jugada.
     */
    @Override
    public String toString() {
        return "JugadaSubject{" + "nombre=" + nombre + ", estado=" + estado + ", puntos=" + puntos + '}';
    }

}
