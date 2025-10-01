package modeloJuego;

import java.awt.Point;

/**
 * Interfaz para definir los métodos necesarios en el modelo del juego
 *
 * @author Alici
 */
public interface IModeloJuego {

    /**
     * Método para validar un movimiento de colocación de ficha en el tablero
     *
     * @param posicion Posición del tablero donde se quiere colocar la ficha
     */
    public void validaMovimiento(Point posicion);
}
