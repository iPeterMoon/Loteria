package implementaciones;

import dtos.aplicacion.JugadorDTO;
import dtos.aplicacion.SalaDTO;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author norma
 */
public class Sala {

    private final List<JugadorDTO> jugadores;
    private String jugadorHost;
    private ConfiguracionJuego configuracion;

    private static Sala instance;

    private Sala() {
        this.jugadores = new LinkedList<>();
    }

    public static Sala getInstance() {
        if (instance == null) {
            instance = new Sala();
        }
        return instance;
    }

    /**
     * Agrega un jugador a la lista de jugadores en la sala.
     *
     * @param jugador Jugador a agregar en la sala
     */
    public void agregarJugador(JugadorDTO jugador) {
        this.jugadores.add(jugador);
    }

    public void setJugadorHost(String jugadorHost) {
        this.jugadorHost = jugadorHost;
    }

    /**
     * La lista de jugadores en la sala.
     *
     * @return Lista de jugadores en la sala.
     */
    public List<JugadorDTO> getJugadores() {
        return jugadores;
    }

    /**
     * Establece el jugador que es el host de la sala con su nombre de usuario
     *
     * @param jugadorHost nombre de usuario del host
     */
    public void setHost(String jugadorHost) {
        this.jugadorHost = jugadorHost;
    }

    /**
     * @return El nombre de usuario del host
     */
    public String getHost() {
        return jugadorHost;
    }

    /**
     * Establece la configuración de la sala / partida
     *
     * @param configuracion Configuració de la sala / partida
     */
    public void setConfiguracion(ConfiguracionJuego configuracion) {
        this.configuracion = configuracion;
    }

    /**
     * @return La configuración del juego.
     */
    public ConfiguracionJuego getConfiguracion() {
        return configuracion;
    }

    public void configurarNuevaSala(ConfiguracionJuego configuracion, JugadorDTO host) {
        this.configuracion = configuracion;
        this.jugadorHost = host.getNickname();
        this.jugadores.add(host);
    }

    public boolean eliminarJugador(String nickname) {
        return jugadores.removeIf(jugador -> jugador.getNickname().equals(nickname));
    }

    public void limpiarJugadores() {
        this.jugadores.clear();
    }

}
