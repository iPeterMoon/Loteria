/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 * Clase que representa el cantador de cartas del juego implementado como sujeto
 * (Subject) dentro del patrón de diseño Observer.
 *
 * Su función es mantener y notificar el valor de la carta actual que está
 * siendo cantada para que los observadores puedan reaccionar ante cambios en el
 * estado.
 *
 * @author rocha
 */
public class CantadorSubject extends Subject {

    /**
     * Número de la carta que está siendo actualmente cantada.
     */
    private int cartaActual;

    /**
     * Constructor vacío.
     */
    public CantadorSubject() {
    }

    /**
     * Método para obtener la carta actual cantada.
     *
     * @return Entero que representa el número de la carta actual.
     */
    public int getCartaActual() {
        return cartaActual;
    }

    /**
     * Método para actualizar el valor de la carta actual cantada.
     *
     * @param carta Entero que representa el número de la nueva carta.
     */
    public void actualizarCarta(int carta) {
        this.cartaActual = carta;
        this.notifyAllObservers();
    }

    /**
     * Método que devuelve una representación en cadena del estado del objeto,
     * mostrando la carta actual que está siendo cantada.
     *
     * @return Cadena con la información del objeto CantadorSubject.
     */
    @Override
    public String toString() {
        return "CantadorSubject{" + "cartaActual=" + cartaActual + '}';
    }

}
