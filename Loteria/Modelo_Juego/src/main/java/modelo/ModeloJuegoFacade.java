package modelo;

import interfaces.IModeloJuego;
import interfaces.IModeloVistaJuego;
import java.awt.Point;
import dtos.FichaDTO;
import dtos.JugadorDTO;
import eventos.eventos_aplicacion.EventoFicha;
import interfaces.IPeer;
import managers.CantadorManager;
import managers.InicioPartidaManager;
import managers.MovimientoManager;
import mappers.JugadorMapperModelo;

/**
 * Clase que implementa los métodos de la interfaz IModeloJuego
 *
 * @author Alici
 */
public class ModeloJuegoFacade implements IModeloJuego {

    private static ModeloJuegoFacade instancia;
    //Dejo espacio para el modeloVistaConfiguración
    private IModeloVistaJuego vistaJuego;

    private final MovimientoManager movimientoManager = new MovimientoManager();
    private final InicioPartidaManager inicioPartidaManager = new InicioPartidaManager();
    private final CantadorManager cantadorManager = new CantadorManager();

    private ModeloJuegoFacade() {
    }

    public static ModeloJuegoFacade getInstance() {
        if (instancia == null) {
            instancia = new ModeloJuegoFacade();
        }
        return instancia;
    }

    public void inicializar(IModeloVistaJuego modeloVista, IPeer peer) {
        if (this.vistaJuego != null) {
            //Asegura que no se inicialice dos veces
            return;
        }
        this.vistaJuego = modeloVista;

        movimientoManager.inicializar(peer);
        inicioPartidaManager.inicializar(peer, modeloVista);
        cantadorManager.inicializar(peer);
    }

    /**
     * Método para obtener el jugador principal, es decir el que es dueño de la
     * vista
     *
     * @return Jugador dueño de la vista
     */
    @Override
    public JugadorDTO getJugadorPrincipal() {
        return JugadorMapperModelo.toDTO(Sala.getInstance().getJugadorPrincipal(), true);
    }

    /**
     * Método para asignar el jugador principal, es decir el que es dueño de la
     * vista
     *
     * @param jugadorPrincipal Jugador que es dueño de la vista
     */
    @Override
    public void setJugadorPrincipal(JugadorDTO jugadorPrincipal) {
        Sala sala = Sala.getInstance();
        sala.setJugadorPrincipal(JugadorMapperModelo.toJugador(jugadorPrincipal));
    }
    
    /**
     * Valida un movimiento de colocación de ficha en la tarjeta del jugador
     * principal.
     *
     * 1. Obtiene la carta en la posición seleccionada. 2. Compara con la carta
     * actual cantada por el {@link Cantador}. 3. Si coinciden: - Coloca una
     * ficha en la tarjeta del jugador. - Crea un {@link FichaDTO} con la
     * posición y el nickname del jugador. - Notifica a la vista para que
     * actualice la interfaz.
     *
     * @param posicion Posición en la tarjeta donde el jugador intenta colocar
     * la ficha.
     */
    @Override
    public void validaMovimiento(Point posicion) {
        movimientoManager.validaMovimiento(posicion);
        mostrarFramePartida();
    }

    /**
     * Metodo que llama a la vista para colocar la ficha en la tarjeta de un
     * jugador
     *
     * @param ficha DTO con la posicion de la ficha y el jugador a quien va a
     * colocarse la ficha.
     */
    @Override
    public void colocarFicha(EventoFicha ficha) {
        FichaDTO fichaDTO = new FichaDTO(ficha.getUserSender(), ficha.getPosicion());
        vistaJuego.colocarFicha(fichaDTO);
    }

    @Override
    public void iniciarPartida() {
        inicioPartidaManager.iniciarPartida();
        inicioPartidaManager.mostrarFramePartida();
        cantadorManager.iniciarCanto();
    }

    @Override
    public void agregarJugadorSecundario(JugadorDTO jugadorSecundario) {
        Sala sala = Sala.getInstance();
        sala.agregarJugadorSecundario(JugadorMapperModelo.toJugador(jugadorSecundario));
    }

    @Override
    public void mostrarFramePartida() {
        inicioPartidaManager.mostrarFramePartida();
    }

    /**
     * Actualiza la carta actual mediante la vista.
     * @param cartaActual Número de carta cantada actual.
     */
    @Override
    public void actualizarCarta(int cartaActual) {
        vistaJuego.actualizarCarta(cartaActual);
    }
}
