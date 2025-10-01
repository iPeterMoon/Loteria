package dtos;

import java.awt.Point;
import java.io.Serializable;

/**
 * DTO para pasar los datos necesarios para colocar una ficha.
 *
 * @author Alici
 */
public class FichaDTO implements Serializable {
    private static final long serialVersionUID = 1L; // Importante para la serialización

    /**
     * Nombre del jugador que colocó la ficha
     */
    private String nicknameJugador;
    /**
     * Posición de la ficha colocada en el tablero
     */
    private Point posicion;

    /**
     * Constructor con el nombre del jugador y la posición de la ficha colocada
     *
     * @param nicknameJugador Nombre del jugador que colocó la ficha
     * @param posicion Posición en el tablero de la ficha colocada
     */
    public FichaDTO(String nicknameJugador, Point posicion) {
        this.nicknameJugador = nicknameJugador;
        this.posicion = posicion;
    }

    /**
     * Método para obtener el nombre del jugador que colocó la ficha
     *
     * @return Cadena de texto con el nombre del jugador
     */
    public String getNicknameJugador() {
        return nicknameJugador;
    }

    /**
     * Método para obtener la posición de la ficha colocada en el tablero
     *
     * @return Punto con las coordenadas de la ficha colocada en el tablero
     */
    public Point getPosicion() {
        return posicion;
    }

    @Override
    public String toString() {
        return "nicknameJugador: "+ nicknameJugador + ", Posicion: "+posicion.toString(); 
    }
}
