package modelo;

import java.util.LinkedList;
import java.util.List;

import dtos.FichaDTO;

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
public class ModeloVistaFacade implements IModeloVista {

    /**
     * Lista de jugadores que participan en el juego.
     */
    private List<JugadorSubject> jugadores;

    /**
     * Instancia única de la fachada.
     */
    private static ModeloVistaFacade modeloVista;

    /**
     * Constructor privado que inicializa la lista de jugadores.
     *
     * Se restringe la creación de instancias externas para mantener el control
     * del Singleton.
     */
    private ModeloVistaFacade() {
        this.jugadores = new LinkedList<>();
    }

    /**
     * Devuelve la instancia de la fachada. Si no existe, la crea al momento de
     * invocarse.
     *
     * @return Instancia única de la clase.
     */
    public static ModeloVistaFacade getInstance() {
        if (modeloVista == null) {
            modeloVista = new ModeloVistaFacade();
        }
        return modeloVista;
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
    public void agregarJugador(JugadorSubject jugador) {
        jugadores.add(jugador);
    }
}
