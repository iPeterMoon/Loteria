package modelo;

import interfaces.IModeloVistaJuego;
import java.util.LinkedList;
import java.util.List;

import dtos.aplicacion.FichaDTO;
import dtos.aplicacion.JugadaDTO;
import dtos.aplicacion.JugadorDTO;
import mappers.JugadorMapperVista;
import vista.FrameJuego;
import interfaces.IObserver;
import vista.ModelObserver;

/**
 * Clase que implementa la fachada entre el modelo de la vista y el modelo del
 * juego.
 *
 * Asegura que no se interactúe directamente con las clases internas del modelo,
 * manteniendo una separación clara.
 *
 * Implementa el patrón Singleton para garantizar que solo exista una única
 * instancia en toda la aplicación.
 *
 * @author rocha
 */
public class ModeloVistaFacade implements IModeloVistaJuego {

    private static ModeloVistaFacade instancia;

    public static ModeloVistaFacade getInstance() {
        if(instancia == null) {
            instancia = new ModeloVistaFacade();
        }
        return instancia;
    }

    /**
     * Lista de jugadores que participan en el juego.
     */
    private List<JugadorSubject> jugadores;

    /**
     * Observador
     */
    private IObserver observer = new ModelObserver();
    
    private CantadorSubject cantador;
    
    /**
     * Constructor privado que inicializa la lista de jugadores.
     *
     * Se restringe la creación de instancias externas para mantener el control
     * del Singleton.
     */
    private ModeloVistaFacade() {
        this.jugadores = new LinkedList<>();
        this.cantador = new CantadorSubject();
        configurarCantador();
    }

    /**
     * Indica que un jugador ha colocado una ficha en el tablero.
     *
     * El método busca al jugador correspondiente mediante su nickname y delega
     * en él la acción de colocar la ficha en la posición especificada.
     *
     * @param fichaDTO Objeto con los datos del nickname y posición.
     */
    @Override
    public void colocarFicha(FichaDTO fichaDTO) {
        for (JugadorSubject jugador : jugadores) {
            if (jugador.getNickname().equals(fichaDTO.getNicknameJugador())) {
                jugador.colocarFicha(fichaDTO.getPosicion());
                break;
            }
        }
    }

    /**
     * Agrega un nuevo jugador a la lista de jugadores registrados en el modelo.
     *
     * @param jugador Objeto JugadorSubject a agregar.
     */
    private void agregarJugador(JugadorSubject jugador) {
        jugadores.add(jugador);
    }

    /**
     * Método para agregar el jugador principal
     *
     * @param jugadorPrincipal DTO con los datos del jugador principal
     */
    @Override
    public void agregarJugadorPrincipal(JugadorDTO jugadorPrincipal) {
        JugadorSubject jugador = JugadorMapperVista.toJugadorSubject(jugadorPrincipal);
        jugador.addObserver(observer);
        agregarJugador(jugador);
        jugador.notifyAllObservers();
    }

    /**
     * Método para agregar un jugador secundario
     *
     * @param jugadorSecundario DTO con los datos del jugador secundario
     */
    @Override
    public void agregarJugadorSecundario(JugadorDTO jugadorSecundario) {
        JugadorSubject jugador = JugadorMapperVista.toJugadorSubject(jugadorSecundario);
        jugador.addObserver(observer);
        agregarJugador(jugador);
        jugador.notifyAllObservers();
    }

    /**
     * Método para inicializar el frame del juego y configurarlo
     */
    @Override
    public void iniciarFrameJuego() {
        FrameJuego frame = FrameJuego.getInstance();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
    }
    
    private void configurarCantador() {
        cantador.addObserver(observer);
    }
    
    @Override
    public void actualizarCarta(int carta) {
        cantador.actualizarCarta(carta);
    }

    @Override
    public void cantarJugada(JugadaDTO jugadaDTO) {
        System.out.println("JUGADA");
        System.out.println(jugadaDTO.toString());
    }
}
