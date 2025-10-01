/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.awt.Image;
import java.awt.Point;

/**
 * Clase que representa a un jugador dentro del juego, implementada como un
 * sujeto (Subject) en el patrón de diseño Observer.
 *
 * Permite notificar a sus observadores cada vez que se actualizan ciertos
 * atributos o se colocan fichas en la tarjeta.
 *
 * @author rocha
 */
public class JugadorSubject extends Subject {

    /**
     * Nombre de usuario del jugador.
     */
    private String nickname;

    /**
     * Puntaje acumulado por el jugador.
     */
    private int puntaje;

    /**
     * Imagen asociada al jugador (avatar o foto).
     */
    private Image foto;

    /**
     * Tarjeta que posee el jugador.
     */
    private Tarjeta tarjeta;

    /**
     * Indica si este jugador es el jugador principal.
     */
    private boolean jugadorPrincipal;

    /**
     * Constructor vacío.
     */
    public JugadorSubject() {
    }

    /**
     * Constructor que inicializa un jugador con los valores dados.
     *
     * @param nickname Nombre de usuario del jugador.
     * @param puntaje Puntaje inicial del jugador.
     * @param foto Imagen asociada al jugador.
     * @param tarjeta Tarjeta que pertenece al jugador.
     * @param jugadorPrincipal Indica si este jugador es el principal.
     */
    public JugadorSubject(String nickname, int puntaje, Image foto, Tarjeta tarjeta, boolean jugadorPrincipal) {
        this.nickname = nickname;
        this.puntaje = puntaje;
        this.foto = foto;
        this.tarjeta = tarjeta;
        this.jugadorPrincipal = jugadorPrincipal;
    }

    /**
     * Método que coloca una ficha en la posición indicada dentro de la tarjeta
     * del jugador.
     *
     * Después de colocar la ficha, notifica a todos los observadores
     * registrados.
     *
     * @param posicion Coordenada (x, y) donde se debe colocar la ficha, de
     * acuerdo al Map.
     */
    public void colocarFicha(Point posicion) {
        this.tarjeta.agregarFicha(posicion);
        this.notifyAllObservers();
    }

    /**
     * Método para obtener el nombre de usuario del jugador.
     *
     * @return Cadena con el nickname del jugador.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Método para asignar un nuevo nombre de usuario al jugador.
     *
     * @param nickname Cadena con el nombre de usuario.
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Método para obtener el puntaje acumulado del jugador.
     *
     * @return Entero con el puntaje del jugador.
     */
    public int getPuntaje() {
        return puntaje;
    }

    /**
     * Método para asignar un nuevo puntaje al jugador.
     *
     * @param puntaje Entero con el puntaje que se asignará al jugador.
     */
    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    /**
     * Método para obtener la imagen asociada al jugador.
     *
     * @return Objeto Image con la foto del jugador.
     */
    public Image getFoto() {
        return foto;
    }

    /**
     * Método para asignar una nueva imagen al jugador.
     *
     * @param foto Objeto Image con la foto a asignar.
     */
    public void setFoto(Image foto) {
        this.foto = foto;
    }

    /**
     * Método para obtener la tarjeta del jugador.
     *
     * @return Objeto Tarjeta que representa la tarjeta del jugador.
     */
    public Tarjeta getTarjeta() {
        return tarjeta;
    }

    /**
     * Método para asignar una tarjeta al jugador.
     *
     * @param tarjeta Objeto Tarjeta que se asignará al jugador.
     */
    public void setTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }

    /**
     * Método para verificar si este jugador es el jugador principal.
     *
     * @return true si es el jugador principal, false en caso contrario.
     */
    public boolean isJugadorPrincipal() {
        return jugadorPrincipal;
    }

    /**
     * Método para asignar si el jugador es principal o secundario.
     *
     * @param jugadorPrincipal Valor booleano que indica si es el jugador
     * principal.
     */
    public void setJugadorPrincipal(boolean jugadorPrincipal) {
        this.jugadorPrincipal = jugadorPrincipal;
    }

    /**
     * Método que devuelve una representación en cadena del estado del jugador,
     * incluyendo nickname, puntaje, foto, tarjeta y si es jugador principal.
     *
     * @return Cadena con la información del jugador.
     */
    @Override
    public String toString() {
        return "JugadorSubject{" + "nickname=" + nickname + ", puntaje=" + puntaje + ", foto=" + foto + ", tarjeta=" + tarjeta + ", jugadorPrincipal=" + jugadorPrincipal + '}';
    }

}
