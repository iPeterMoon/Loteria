package interfaces.aplicacion;

import dtos.aplicacion.FichaDTO;
import dtos.aplicacion.JugadaDTO;
import dtos.aplicacion.JugadorDTO;

/**
 * Interfaz que define el contrato para la comunicación entre el modelo de la
 * vista y el modelo del juego.
 *
 * @author rocha
 */
public interface IModeloVistaJuego {

    /**
     * Indica al modelo del juego que se debe colocar una ficha de acuerdo con
     * la información contenida en el DTO recibido.
     *
     * @param fichaDTO Objeto FichaDTO que contiene los datos de la ficha
     */
    public void colocarFicha(FichaDTO fichaDTO);
    
    public void cantarJugada(JugadaDTO jugadaDTO);

    /**
     * Método para agregar el jugador principal
     *
     * @param jugadorPrincipal DTO con los datos del jugador principal
     */
    public void agregarJugadorPrincipal(JugadorDTO jugadorPrincipal);

    /**
     * Método para agregar un jugador secundario
     *
     * @param jugadorSecundario DTO con los datos del jugador secundario
     */
    public void agregarJugadorSecundario(JugadorDTO jugadorSecundario);

    /**
     * Método para inicializar el frame del juego y configurarlo
     */
    public void iniciarFrameJuego();
    
    /**
     * Método para actualizar la carta cantada.
     * @param carta Número de carta cantada actual.
     */
    public void actualizarCarta(int carta);
    
    /**
     * Metodo para eliminar a un jugador en caso de desconexión
     * @param user User del jugador que se va a eliminar.
     */
    public void eliminarJugadorSecundario(String user);

}
