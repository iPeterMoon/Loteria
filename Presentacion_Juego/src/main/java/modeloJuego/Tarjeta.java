package modeloJuego;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase que representa la tarjeta de cartas de la loteria
 *
 * @author Alici
 */
public class Tarjeta {

    /**
     * Mapa de valores punto-entero que representan las cartas que existen en la
     * tarjeta. El punto representa la posición y el entero el número de la
     * carta que se encuentra en dicha posición
     */
    private Map<Point, Integer> cartas;
    /**
     * Mapa de valores punto-boolean que representa las fichas colocadas en la
     * tarjeta. El punto representa la posición en la tarjeta y el boolean si es
     * que hay o no una ficha sobre dicha posición
     */
    private Map<Point, Boolean> fichas;

    /**
     * Constructor vacio
     */
    public Tarjeta() {
        fichas = new HashMap<>();
        reiniciarFichas();
    }

    private void reiniciarFichas() {
        for (int i = 0; i < 16; i++) {
            int fila = i / 4;
            int col = i % 4;
            Point posicion = new Point(fila, col);
            fichas.put(posicion, false);
        }
    }

    /**
     * Constuctor con las cartas que estan en la tarjeta
     *
     * @param cartas Mapa que indica las posiciones de las cartas en la tarjeta
     */
    public Tarjeta(Map<Point, Integer> cartas) {
        fichas = new HashMap<>();
        this.cartas = cartas;
        reiniciarFichas();
    }

    /**
     * Método para obtener el mapa de cartas que tiene la tarjeta
     *
     * @return Mapa punto-entero que indica la posicion de cada carta en la
     * tarjeta
     */
    public Map<Point, Integer> getCartas() {
        return cartas;
    }

    /**
     * Método para asignar el mapa de cartas que tiene la tarjeta
     *
     * @param cartas Mapa punto-entero que indica la posición de cada carta en
     * la tarjeta
     */
    public void setCartas(Map<Point, Integer> cartas) {
        this.cartas = cartas;
    }

    /**
     * Método para obtener las posiciones y si hay o no una ficha marcada en
     * dicha posición de la tarjeta
     *
     * @return Mapa punto-boolean que indica si hay o no ficha en la posición de
     * la carta.
     */
    public Map<Point, Boolean> getFichas() {
        return fichas;
    }

    /**
     * Método para asignar las posiciones donde hay y no hay fichas marcadas.
     *
     * @param fichas Mapa punto-boolean que indica si hay o no ficha en la
     * posición de la carta.
     */
    public void setFichas(Map<Point, Boolean> fichas) {
        this.fichas = fichas;
    }

    /**
     * ESTE METODO ES TEMPORAL POR LO QUE SU IMPLEMENTACIÓN PODRIA CAMBIAR
     *
     *
     * Método para agregar una ficha en el punto dado.
     *
     * @param punto Punto donde se va a asignar el valor de la ficha a
     * verdadero, indicando que hay una ficha en dicha posición.
     */
    public void addFicha(Point punto) {
        fichas.put(punto, true);
    }

}
