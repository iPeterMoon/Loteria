package interfaces;

import dtos.JugadorDTO;
import eventos.eventos_aplicacion.EventoFicha;
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
     * Inicia una partida de lotería con los elementos de configuración ya hechos 
     * y los jugadores ya contemplados.
     */
    public void iniciarPartida();

    /**
     * Muestra el frame de la partida, tambien se encarga de ocultar el frame de
     * configuración
     */
    public void mostrarFramePartida();

    /**
     * Método para obtener el jugador principal, es decir el que es dueño de la
     * vista
     *
     * @return Jugador dueño de la vista
     */
    public JugadorDTO getJugadorPrincipal();

    /**
     * Metodo que establece el jugador principal, el que está ejecutando esta
     * instancia del juego
     */
    public void setJugadorPrincipal(JugadorDTO jugadorPrincipal);

    /**
     * Metodo para agregar un jugador secundario al juego
     * @param jugadorSecundario Jugador a agregar al juego
     */
    public void agregarJugadorSecundario(JugadorDTO jugadorSecundario);

    /**
     * Metodo para colocar una ficha en alguna de las tarjetas de los jugadores
     */
    public void colocarFicha(EventoFicha ficha);
}
