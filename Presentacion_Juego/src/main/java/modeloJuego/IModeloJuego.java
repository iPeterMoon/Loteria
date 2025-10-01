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

    /**
     * Método para agregar un jugador principal
     * @param jugador Jugador Principal
     */
    public void agregarJugadorPrincipal(Jugador jugador);

    /**
     * Método para agregar un jugador secundario
     * @param jugador Jugador Secundario
     */
    public void agregarJugadorSecundario(Jugador jugador);
}
