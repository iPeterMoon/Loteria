package modelo;

import java.util.Stack;

/**
 * Clase singleton que representa el cantador de cartas del juego.
 *
 * @author Alici
 */
public class Cantador {

    /**
     * Carta que esta siendo actualmente cantada
     */
    private int cartaActual;
    /**
     * Mazo (representado como una pila) de cartas que tiene el cantador
     */
    private Stack<Integer> mazo;

    /**
     * Instancia unica de la clase Cantador
     */
    private static Cantador instance;

    /**
     * Constructor vacio
     */
    private Cantador() {
    }

    /**
     * Metodo para obtener la instancia unica del cantador
     *
     * @return la instancia de {@link Cantador}
     */
    public static Cantador getInstance() {
        if (instance == null) {
            instance = new Cantador();
        }
        return instance;
    }

    /**
     * Método para obtener la carta actual que esta siendo cantada
     *
     * @return Entero que representa el número de la carta actual
     */
    public int getCartaActual() {
        return cartaActual;
    }

    /**
     * Método para asignar la carta que esta siendo cantada actualmente.
     *
     * @param cartaActual Entero que representa el número de la carta actual
     */
    public void setCartaActual(int cartaActual) {
        this.cartaActual = cartaActual;
    }

    /**
     * Método para obtener el mazo de cartas que quedan
     *
     * @return Pila de cartas que tiene el cantador
     */
    public Stack<Integer> getMazo() {
        return mazo;
    }

    /**
     * Método para asignar el mazo de cartas al cantador
     *
     * @param mazo Pila de cartas a asignar al cantador
     */
    public void setMazo(Stack<Integer> mazo) {
        this.mazo = mazo;
    }

}
