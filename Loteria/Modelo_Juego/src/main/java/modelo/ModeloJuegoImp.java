package modelo;

import com.google.gson.Gson;
import interfaces.IModeloJuego;
import interfaces.IModeloVista;
import java.awt.Point;

import dtos.FichaDTO;
import java.util.List;

import dtos.JugadorDTO;
import enums.TipoEvento;
import eventos.Evento;
import eventos.eventos_aplicacion.EventoFicha;
import interfaces.IObserver;
import interfaces.IPeer;
import mappers.JugadorMapperModelo;

/**
 * Clase que implementa los métodos de la interfaz IModeloJuego
 *
 * @author Alici
 */
public class ModeloJuegoImp implements IModeloJuego, IObserver {

    private static ModeloJuegoImp instancia;
    private IModeloVista vista;
    private IPeer componentePeer;
    /**
     * Lista de jugadores secundarios al jugador de la vista principal
     */
    private List<Jugador> jugadoresSecundario;
    /**
     * Jugador principal que tiene la vista principal
     */
    private Jugador jugadorPrincipal;
    /**
     * Jugador host de la ronda
     */
    private Jugador host;

    private ModeloJuegoImp() {
    }

    public static ModeloJuegoImp getInstance() {
        if (instancia == null) {
            instancia = new ModeloJuegoImp();
        }
        return instancia;
    }

    public void inicializar(IModeloVista modeloVista, IPeer peer) {
        if (this.vista != null) {
            //Asegura que no se inicialice dos veces
            return;
        }
        this.vista = modeloVista;

        if (this.componentePeer != null) {
            return;
        }
        this.componentePeer = peer;
    }

    /**
     * Método para obtener el jugador principal, es decir el que es dueño de la
     * vista
     *
     * @return Jugador dueño de la vista
     */
    public JugadorDTO getJugadorPrincipal() {
        return JugadorMapperModelo.toDTO(jugadorPrincipal, true);
    }

    /**
     * Método para asignar el jugador principal, es decir el que es dueño de la
     * vista
     *
     * @param jugadorPrincipal Jugador que es dueño de la vista
     */
    @Override
    public void setJugadorPrincipal(JugadorDTO jugadorPrincipal) {
        this.jugadorPrincipal = JugadorMapperModelo.toJugador(jugadorPrincipal);
    }

    /**
     * Constructor con los atributos de jugadores secundarios, jugador principal
     * y host de la ronda.
     *
     * @param jugadoresSecundario
     * @param jugadorPrincipal
     * @param host
     */
    public ModeloJuegoImp(List<Jugador> jugadoresSecundario, Jugador jugadorPrincipal, Jugador host) {
        this.jugadoresSecundario = jugadoresSecundario;
        this.jugadorPrincipal = jugadorPrincipal;
        this.host = host;
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
        if (jugadorPrincipal == null || jugadorPrincipal.getTarjeta() == null) {
            return; // No hay jugador principal o tarjeta asignada
        }

        Tarjeta tarjeta = jugadorPrincipal.getTarjeta();
        Integer numeroCarta = tarjeta.getCartas().get(posicion);

        if (numeroCarta == null) {
            return; // La posición no corresponde a ninguna carta en la tarjeta
        }

        // Carta actual del cantador (singleton)
        Cantador cantador = Cantador.getInstance();
        int cartaActual = cantador.getCartaActual();

        //Verificar si la posición ya tiene una ficha
        if (tarjeta.getFichas() != null && Boolean.TRUE.equals(tarjeta.getFichas().get(posicion))) {
            System.out.println("Ya hay una ficha en " + posicion + ", se ignora.");
            return; // Evita volver a validar o colocar ficha en esa posición
        }

        // Validación
        if (numeroCarta == cartaActual) {
            // Colocar ficha en la tarjeta
            tarjeta.addFicha(posicion);

            // Crear DTO y notificar a la vista
            FichaDTO ficha = new FichaDTO(jugadorPrincipal.getNickname(), posicion);
            colocarFicha(ficha);

            EventoFicha eventoFicha = new EventoFicha(jugadorPrincipal.getNickname(), posicion);
            componentePeer.broadcastEvento(eventoFicha);
            // Print temporal 
            System.out.println("Ficha colocada correctamente en " + posicion + " por " + jugadorPrincipal.getNickname() + " (Carta: " + cartaActual + ")");
        } else {
            // Print temporal para movimientos inválidos
            System.out.println("Movimiento inválido en " + posicion + " (Carta en tarjeta: " + numeroCarta + ", Carta cantada: " + cartaActual + ")");
        }

    }

    /**
     * Metodo que llama a la vista para colocar la ficha en la tarjeta de un
     * jugador
     *
     * @param ficha DTO con la posicion de la ficha y el jugador a quien va a
     * colocarse la ficha.
     */
    @Override
    public void colocarFicha(FichaDTO ficha) {
        vista.colocarFicha(ficha);
    }

    /**
     * Método de la interfaz IObserver que el componente de peer (Subject)
     * utiliza para notificar a sus Observadores.
     *
     * @param object
     */
    @Override
    public void update(Object object) {
        if (object instanceof Evento evento) {
            procesarEventos(evento);
        }
    }

    /**
     * Procesa los eventos del juego que llegan del componente de peer
     *
     * @param evento
     */
    private void procesarEventos(Evento evento) {
        // CAMBIAR A CADENA DE RESPONSABILIDAD
        if (evento.getTipoEvento() == TipoEvento.FICHA) {

            // Castear a evento concreto
            EventoFicha eventoFicha = (EventoFicha) evento;
            System.out.println("[ModeloJuego] Procesando evento: " + evento.toString());

            FichaDTO fichaDTO = new FichaDTO(
                    eventoFicha.getUserSender(),
                    eventoFicha.getPosicion()
            );

            colocarFicha(fichaDTO);
        }
    }

}
