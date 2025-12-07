package dtos.aplicacion;

import java.awt.Point;
import java.util.Map;

/**
 * Objeto de Transferencia de Datos que representa el estado de una tarjeta de
 * juego, incluyendo la distribución de cartas y la posición de las fichas.
 *
 * @author Alici
 */
public class TarjetaDTO {

    /**
     * Mapa que almacena las cartas. La clave es la posición (Point) en el
     * tablero y el valor es un identificador entero (Integer) de la carta o su
     * contenido.
     */
    private Map<Point, Integer> cartas;

    /**
     * Mapa que almacena la posición de las fichas. La clave es la posición
     * (Point) en el tablero y el valor es un booleano (Boolean) que indica la
     * presencia/estado de una ficha.
     */
    private Map<Point, Boolean> fichas;

    /**
     * Constructor por defecto para TarjetaDTO.
     */
    public TarjetaDTO() {
    }

    /**
     * Constructor para inicializar un TarjetaDTO con los mapas de cartas y
     * fichas.
     *
     * @param cartas Mapa que representa la distribución de las cartas en el
     * tablero.
     * @param fichas Mapa que representa la distribución o estado de las fichas
     * en el tablero.
     */
    public TarjetaDTO(Map<Point, Integer> cartas, Map<Point, Boolean> fichas) {
        this.cartas = cartas;
        this.fichas = fichas;
    }

    /**
     * Obtiene el mapa de cartas del tablero.
     *
     * @return El mapa donde la clave es la posición (Point) y el valor es el
     * identificador de la carta (Integer).
     */
    public Map<Point, Integer> getCartas() {
        return cartas;
    }

    /**
     * Establece el mapa de cartas del tablero.
     *
     * @param cartas El nuevo mapa de cartas a establecer.
     */
    public void setCartas(Map<Point, Integer> cartas) {
        this.cartas = cartas;
    }

    /**
     * Obtiene el mapa de fichas del tablero.
     *
     * @return El mapa donde la clave es la posición (Point) y el valor es el
     * estado de la ficha (Boolean).
     */
    public Map<Point, Boolean> getFichas() {
        return fichas;
    }

    /**
     * Establece el mapa de fichas del tablero.
     *
     * @param fichas El nuevo mapa de fichas a establecer.
     */
    public void setFichas(Map<Point, Boolean> fichas) {
        this.fichas = fichas;
    }

}
