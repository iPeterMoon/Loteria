package modelo;

import dtos.aplicacion.ConfiguracionJuegoDTO;
import interfaces.aplicacion.IModeloJuego;
import interfaces.aplicacion.IModeloVistaJuego;
import java.awt.Point;
import dtos.aplicacion.FichaDTO;
import dtos.aplicacion.JugadaDTO;
import dtos.aplicacion.JugadorDTO;
import dtos.aplicacion.JugadorSalaEsperaDTO;
import dtos.aplicacion.MensajeDTO;
import dtos.aplicacion.NuevoUsuarioDTO;
import eventos.eventos_aplicacion.EventoFicha;
import eventos.eventos_aplicacion.EventoJugada;
import interfaces.aplicacion.IModeloVistaConfiguracion;
import interfaces.peer.IPeer;
import java.util.ArrayList;
import java.util.List;
import managers.CantadorManager;
import managers.CantarJugadaManager;
import managers.InicioPartidaManager;
import managers.MovimientoManager;
import managers.SalaManager;
import mappers.JugadorMapperModelo;
import enums.JugadasDisponibles;
import enums.TipoConfiguracion;
import managers.ConfiguracionManager;
import enums.TipoNivel;

/**
 * Clase que implementa los métodos de la interfaz IModeloJuego
 *
 * @author Alici
 */
public class ModeloJuegoFacade implements IModeloJuego {

    private static ModeloJuegoFacade instancia;
    // Dejo espacio para el modeloVistaConfiguración
    private IModeloVistaJuego vistaJuego;
    private IModeloVistaConfiguracion vistaConfiguracion;

    private final MovimientoManager movimientoManager = new MovimientoManager();
    private final InicioPartidaManager inicioPartidaManager = new InicioPartidaManager();
    private final CantadorManager cantadorManager = new CantadorManager();
    private final CantarJugadaManager cantarJugadaManager = new CantarJugadaManager();
    private final SalaManager unirsePartidaManager = new SalaManager();
    private final ConfiguracionManager configuracionManager = new ConfiguracionManager();

    private ModeloJuegoFacade() {
    }

    public static ModeloJuegoFacade getInstance() {
        if (instancia == null) {
            instancia = new ModeloJuegoFacade();
        }
        return instancia;
    }

    public void inicializar(IModeloVistaJuego modeloVistaJuego, IModeloVistaConfiguracion modeloVistaConfiguracion,
            IPeer peer) {
        if (this.vistaJuego != null) {
            // Asegura que no se inicialice dos veces
            return;
        }
        this.vistaJuego = modeloVistaJuego;
        this.vistaConfiguracion = modeloVistaConfiguracion;

        movimientoManager.inicializar(peer);
        configuracionManager.inicializar(peer, modeloVistaConfiguracion);
        inicioPartidaManager.inicializar(peer, modeloVistaJuego);
        cantadorManager.inicializar(peer);
        cantarJugadaManager.inicializar(peer);
        unirsePartidaManager.inicializar(peer, modeloVistaConfiguracion);
    }

    /**
     * Método para obtener el jugador principal, es decir el que es dueño de la
     * vista
     *
     * @return Jugador dueño de la vista
     */
    @Override
    public JugadorDTO getJugadorPrincipal() {
        Sala sala = Sala.getInstance();
        if(sala.getJugadorPrincipal() != null){
            return JugadorMapperModelo.toDTO(Sala.getInstance().getJugadorPrincipal(), true);
        } else {
            return null;
        }
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
        if(jugadorPrincipal != null){
            sala.setJugadorPrincipal(JugadorMapperModelo.toJugador(jugadorPrincipal));
            vistaConfiguracion.actualizarJugadorPrincipal(jugadorPrincipal.getNickname());
        } else {
            sala.setJugadorPrincipal(null);
        }
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
     *
     * @param cartaActual Número de carta cantada actual.
     */
    @Override
    public void actualizarCarta(int cartaActual) {
        vistaJuego.actualizarCarta(cartaActual);
    }

    /**
     * Valida la jugada recibida como parámetro y la envía al gestor encargado
     * de procesar el canto de la jugada.
     *
     * @param jugada representa la jugada a validar.
     */
    @Override
    public void validarJugada(JugadasDisponibles jugada) {
        cantarJugadaManager.cantarJugada(jugada);
    }

    /**
     * Recibe un evento de jugada, construye un objeto {@link JugadaDTO} con la
     * información del usuario y el tipo de jugada, y notifica a la vista para
     * que se muestre el canto de la jugada.
     *
     * @param eventoJugada Objeto que contiene los datos del evento de la
     * jugada, incluyendo el usuario que la realizó y el tipo de jugada.
     */
    @Override
    public void cantarJugada(EventoJugada eventoJugada) {
        String usuario = eventoJugada.getUserSender();
        String jugada = eventoJugada.getTipoJugada();
        int puntaje = Sala.getInstance().getConfiguracion().getPuintajes().get(JugadasDisponibles.valueOf(jugada));
        JugadaDTO jugadaDTO = new JugadaDTO(usuario, jugada, puntaje);
        
        vistaJuego.cantarJugada(jugadaDTO);
        Sala.getInstance().agregarPuntaje(eventoJugada.getUserSender(), JugadasDisponibles.valueOf(eventoJugada.getTipoJugada()));
        if (eventoJugada.getTipoJugada().equals("LLENA")) {
            cantadorManager.detenerCantador();
            finalizarRonda("El jugador " + eventoJugada.getUserSender() + "ganó");
        }
    }

    /**
     * Método para unirse a partida.
     *
     * @param usuario El usuario a entrar a la partida
     */
    @Override
    public void unirseSala(NuevoUsuarioDTO usuario) {
        boolean usuarioValido = configuracionManager.configurarUsuarioUnirseSala(usuario);
        if (usuarioValido) {
            unirsePartidaManager.unirseSala(usuario);
        }
    }

    /**
     * Método para abandonar la sala de espera.
     *
     */
    @Override
    public void abandonarSala() {
        unirsePartidaManager.abandonarSala();
    }

    /**
     * Método que actualiza la sala (los jugadores).
     *
     * @param jugadores Los jugadores de la sala.
     */
    @Override
    public void actualizarJugadoresSala(List<JugadorDTO> jugadores) {
        List<JugadorSalaEsperaDTO> jugadoresSalaEspera = new ArrayList<>();
        for (JugadorDTO dto : jugadores) {
            jugadoresSalaEspera.add(JugadorMapperModelo.toSalaEsperaDTO(dto));
        }
        vistaConfiguracion.actualizarJugadoresSala(jugadoresSalaEspera);
    }

    /**
     * Método que actualiza los datos de la sala (limite de jugadores y nivel).
     *
     * @param host El host de la sala.
     * @param limiteJugadores El limite de jugadores en la sala.
     * @param nivel El nivel de la partida.
     */
    @Override
    public void actualizarDatosSala(String host, int limiteJugadores, TipoNivel nivel) {
        vistaConfiguracion.actualizarDatosSala(host, limiteJugadores, nivel);
    }

    @Override
    public void configurarUsuarioNuevaSala(NuevoUsuarioDTO usuario) {
        configuracionManager.configurarUsuarioNuevaSala(usuario);
    }

    public void mostrarMensaje(MensajeDTO mensaje) {
        vistaConfiguracion.actualizarMensaje(mensaje);
    }

    @Override
    public void actualizarSala(List<JugadorDTO> jugadores) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
        // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void cambiarTipoConfiguracion(TipoConfiguracion tipoConfiguracion) {
        vistaConfiguracion.actualizarTipoConfiguracion(tipoConfiguracion);
    }

    @Override
    public void obtenerSala() {
        configuracionManager.obtenerSala();
    }

    @Override
    public void crearNuevaSala(ConfiguracionJuegoDTO configuracionJuego) {
        configuracionManager.crearNuevaSala(configuracionJuego);
    }

    public void cerrarSalaEspera() {
        vistaConfiguracion.cerrarVentana();
    }

    public void eliminarJugadorDePartida(String user) {
        vistaJuego.eliminarJugadorSecundario(user);
    }

    @Override
    public void finalizarRonda(String motivo) {
        System.out.println("Se acabo la ronda: " + motivo);

        if (cantadorManager != null) {
            cantadorManager.detenerCantador();
            System.out.println("Se acabo la ronda");
        }
    }

}
