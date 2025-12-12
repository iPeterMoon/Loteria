package modelo;

import dtos.aplicacion.JugadorSalaEsperaDTO;
import enums.TipoNivel;
import java.util.List;
import java.util.ArrayList;
import util.Subject;

/**
 * Clase que actúa como Sujeto (Subject) en el patrón Observador, responsable de
 * mantener y notificar los cambios en el estado de la sala de juego.
 *
 * Implementa el patrón Singleton para asegurar una única instancia de la
 * información de la sala. Mantiene datos como el anfitrión, el nivel, el límite
 * de jugadores y la lista actual de participantes.
 *
 * @author norma
 */
public class SalaSubject extends Subject {

    /**
     * Nombre de usuario del anfitrión (host) de la sala.
     */
    private String hostUser;

    /**
     * Nivel de dificultad de la partida.
     */
    private TipoNivel nivel;

    /**
     * Número máximo de jugadores permitidos en la sala.
     */
    private int limiteJugadores;

    /**
     * Lista de jugadores actualmente en la sala de espera.
     */
    private List<JugadorSalaEsperaDTO> jugadores;

    /**
     * Nombre de usuario del jugador que está utilizando esta instancia de la
     * aplicación (jugador local).
     */
    private String jugadorPrincipalUser;

    /**
     * Instancia única de la clase (Singleton).
     */
    private static SalaSubject instance;

    /**
     * Constructor privado para asegurar el patrón Singleton. Inicializa la
     * lista de jugadores.
     */
    private SalaSubject() {
        this.jugadores = new ArrayList<>();
    }

    /**
     * Devuelve la instancia única de SalaSubject.
     *
     * @return La instancia Singleton de SalaSubject.
     */
    public static SalaSubject getInstance() {
        if (instance == null) {
            instance = new SalaSubject();
        }
        return instance;
    }

    /**
     * Configura todos los datos esenciales de la sala y notifica a los
     * observadores.
     *
     * @param hostUser Nombre de usuario del anfitrión.
     * @param nivel Nivel de dificultad de la partida.
     * @param limiteJugadores Límite de jugadores permitido.
     * @param jugadores Lista inicial de jugadores.
     */
    public void configurarSala(String hostUser, TipoNivel nivel, int limiteJugadores, List<JugadorSalaEsperaDTO> jugadores) {
        this.hostUser = hostUser;
        this.nivel = nivel;
        this.limiteJugadores = limiteJugadores;
        this.jugadores = jugadores;
        notifyAllObservers();
    }

    /**
     * Obtiene el nombre de usuario del anfitrión.
     *
     * @return El nombre de usuario del anfitrión.
     */
    public String getHost() {
        return hostUser;
    }

    /**
     * Establece un nuevo anfitrión y notifica a los observadores.
     *
     * @param hostUser El nuevo nombre de usuario del anfitrión.
     */
    public void setHostUser(String hostUser) {
        this.hostUser = hostUser;
        notifyAllObservers();
    }

    /**
     * Obtiene el nivel de dificultad de la partida.
     *
     * @return El TipoNivel actual.
     */
    public TipoNivel getNivel() {
        return nivel;
    }

    /**
     * Establece el nivel de dificultad de la partida. Nota: Este método no
     * notifica observadores.
     *
     * @param nivel El nuevo TipoNivel.
     */
    public void setNivel(TipoNivel nivel) {
        this.nivel = nivel;
    }

    /**
     * Obtiene el límite máximo de jugadores en la sala.
     *
     * @return El límite de jugadores.
     */
    public int getLimiteJugadores() {
        return limiteJugadores;
    }

    /**
     * Establece el límite máximo de jugadores en la sala. Nota: Este método no
     * notifica observadores.
     *
     * @param limiteJugadores El nuevo límite.
     */
    public void setLimiteJugadores(int limiteJugadores) {
        this.limiteJugadores = limiteJugadores;
    }

    /**
     * Obtiene la lista actual de jugadores en la sala de espera.
     *
     * @return Lista de JugadorSalaEsperaDTO.
     */
    public List<JugadorSalaEsperaDTO> getJugadores() {
        return jugadores;
    }

    /**
     * Establece la lista de jugadores y notifica a los observadores.
     *
     * @param jugadores La nueva lista de jugadores.
     */
    public void setJugadores(List<JugadorSalaEsperaDTO> jugadores) {
        this.jugadores = jugadores;
        notifyAllObservers();
    }

    /**
     * Actualiza simultáneamente el anfitrión, el límite de jugadores y el nivel
     * de la sala, y notifica a los observadores.
     *
     * @param host El nombre de usuario del anfitrión.
     * @param limiteJugadores El nuevo límite de jugadores.
     * @param nivel El nuevo nivel de dificultad.
     */
    public void actualizarDatosSala(String host, int limiteJugadores, TipoNivel nivel) {
        this.hostUser = host;
        this.limiteJugadores = limiteJugadores;
        this.nivel = nivel;
        notifyAllObservers();
    }

    /**
     * Obtiene el nombre de usuario del jugador principal (jugador local).
     *
     * @return El nombre de usuario local.
     */
    public String getJugadorPrincipalUser() {
        return jugadorPrincipalUser;
    }

    /**
     * Establece el nombre de usuario del jugador principal (jugador local) y
     * notifica a los observadores.
     *
     * @param jugadorPrincipalUser El nombre de usuario del jugador principal.
     */
    public void setJugadorPrincipalUser(String jugadorPrincipalUser) {
        System.out.println("[SalaSubject] Seteando jugadorPrincipalUser: " + jugadorPrincipalUser);
        this.jugadorPrincipalUser = jugadorPrincipalUser;
        System.out.println("[SalaSubject] Notificando observadores...");
        notifyAllObservers();
    }
}
