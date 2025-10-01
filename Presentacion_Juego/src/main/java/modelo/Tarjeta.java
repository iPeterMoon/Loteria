package modelo;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase que representa una tarjeta de juego.
 *
 * La tarjeta está compuesta por un conjunto de cartas (identificadas con un
 * número) y un conjunto de posiciones donde se colocan las fichas. Cada
 * posición se identifica mediante un objeto Point (coordenadas x, y).
 *
 * @author rocha
 */
public class Tarjeta {

    /**
     * Mapa que contiene las cartas asignadas a cada posición de la tarjeta. La
     * clave es un objeto Point que representa la coordenada (x, y), y el valor
     * es un número entero que identifica la carta en esa posición.
     */
    private Map<Point, Integer> cartas;

    /**
     * Mapa que representa si una ficha ha sido colocada en una posición
     * específica. La clave es un objeto Point que representa la coordenada (x,
     * y), y el valor es un booleano que representa la ficha.
     */
    private Map<Point, Boolean> fichas;

    /**
     * Constructor que inicializa la tarjeta con las cartas dadas y marca todas
     * las posiciones como sin fichas.
     *
     * @param cartas Mapa con las cartas asociadas a cada posición de la
     * tarjeta.
     */
    public Tarjeta(Map<Point, Integer> cartas) {
        this.cartas = cartas;
        fichas = new HashMap<>();
        iniciarMapaFichas();
    }

    /**
     * Método privado que inicializa el mapa de fichas, asignando false a todas
     * las posiciones.
     */
    private void iniciarMapaFichas() {
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                fichas.put(new Point(x, y), false);
            }
        }
    }

    /**
     * Coloca una ficha en la posición indicada de la tarjeta.
     *
     * @param posicion Objeto Point que indica la coordenada (x, y) donde se
     * colocará la ficha.
     */
    public void agregarFicha(Point posicion) {
        fichas.put(posicion, true);
    }

    /**
     * Reinicia la tarjeta, eliminando todas las fichas y dejando todas las
     * posiciones como vacías.
     */
    public void reiniciarTarjeta() {
        iniciarMapaFichas();
    }

    /**
     * Obtiene el mapa con el estado actual de las fichas en la tarjeta.
     *
     * @return Mapa de posiciones y su estado.
     */
    public Map<Point, Boolean> getFichas() {
        return fichas;
    }
}
