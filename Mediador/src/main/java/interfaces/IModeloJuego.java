package interfaces;

import dtos.aplicacion.JugadorDTO;
import dtos.aplicacion.NuevoUsuarioDTO;
import enums.JugadasDisponibles;
import enums.TipoNivel;
import eventos.eventos_aplicacion.EventoFicha;
import eventos.eventos_aplicacion.EventoJugada;
import java.awt.Point;
import java.util.List;

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
     * Inicia una partida de lotería con los elementos de configuración ya
     * hechos y los jugadores ya contemplados.
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
     *
     * @param jugadorPrincipal
     */
    public void setJugadorPrincipal(JugadorDTO jugadorPrincipal);

    /**
     * Metodo para agregar un jugador secundario al juego
     *
     * @param jugadorSecundario Jugador a agregar al juego
     */
    public void agregarJugadorSecundario(JugadorDTO jugadorSecundario);

    /**
     * Metodo para colocar una ficha en alguna de las tarjetas de los jugadores
     *
     * @param ficha
     */
    public void colocarFicha(EventoFicha ficha);

    /**
     * Actualiza la carta actual mediante la vista.
     *
     * @param cartaActual Número de carta cantada actual.
     */
    public void actualizarCarta(int cartaActual);

    /**
     * Método para validar alguna jugada
     *
     * @param jugada la jugada a validar
     */
    public void validarJugada(JugadasDisponibles jugada);

    /**
     * Metodo para hacer una jugada
     *
     * @param eventoJugada el evento
     */
    public void cantarJugada(EventoJugada eventoJugada);

    /**
     * Método para unirse a partida.
     *
     * @param usuario El usuario a entrar a la partida
     */
    public void unirseSala(NuevoUsuarioDTO usuario);

    /**
     * Método que actualiza la sala (los jugadores).
     *
     * @param jugadores Los jugadores de la sala.
     */
    public void actualizarSala(List<JugadorDTO> jugadores);

    public void configurarUsuarioNuevaSala(NuevoUsuarioDTO usuario);
    
    public void actualizarJugadoresSala(List<JugadorDTO> jugadores);
    
    /**
     * Método que actualiza los datos de la sala (limite de jugadores y nivel).
     * @param limiteJugadores El limite de jugadores en la sala.
     * @param nivel El nivel de la partida.
     */
    public void actualizarDatosSala(int limiteJugadores, TipoNivel nivel);
}
