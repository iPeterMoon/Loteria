package modelo;

import enums.JugadasDisponibles;
import enums.TipoNivel;
import java.util.Map;

/**
 * Clase de modelo que representa la configuración y las reglas específicas de
 * una partida de juego.
 *
 * Contiene parámetros definidos por el anfitrión como el límite de jugadores,
 * el puntaje máximo para ganar, la dificultad de la partida, y la asignación de
 * puntos para cada tipo de jugada disponible.
 *
 * @author Alici
 */
public class ConfiguracionJuego {

    /**
     * El número máximo de jugadores permitidos en la sala.
     */
    private int limiteJugadores;

    /**
     * El puntaje máximo que un jugador debe alcanzar para ganar la partida.
     */
    private int puntajeMax;

    /**
     * El nivel de dificultad de la partida.
     */
    private TipoNivel dificultad;

    /**
     * Mapa que almacena la puntuación asignada a cada tipo de jugada
     * disponible. La clave es el tipo de jugada (JugadasDisponibles) y el valor
     * es el puntaje (Integer).
     */
    private Map<JugadasDisponibles, Integer> puintajes;

    /**
     * Obtiene el límite máximo de jugadores en la sala.
     *
     * @return El límite de jugadores.
     */
    public int getLimiteJugadores() {
        return limiteJugadores;
    }

    /**
     * Establece el límite máximo de jugadores en la sala.
     *
     * @param limiteJugadores El nuevo límite de jugadores.
     */
    public void setLimiteJugadores(int limiteJugadores) {
        this.limiteJugadores = limiteJugadores;
    }

    /**
     * Obtiene el puntaje máximo requerido para ganar la partida.
     *
     * @return El puntaje máximo.
     */
    public int getPuntajeMax() {
        return puntajeMax;
    }

    /**
     * Establece el puntaje máximo requerido para ganar la partida.
     *
     * @param puntajeMax El nuevo puntaje máximo.
     */
    public void setPuntajeMax(int puntajeMax) {
        this.puntajeMax = puntajeMax;
    }

    /**
     * Obtiene el nivel de dificultad de la partida.
     *
     * @return El TipoNivel de dificultad.
     */
    public TipoNivel getDificultad() {
        return dificultad;
    }

    /**
     * Establece el nivel de dificultad de la partida.
     *
     * @param dificultad El nuevo TipoNivel de dificultad.
     */
    public void setDificultad(TipoNivel dificultad) {
        this.dificultad = dificultad;
    }

    /**
     * Obtiene el mapa que define los puntajes asociados a cada tipo de jugada.
     *
     * @return El mapa de puntajes por jugada.
     */
    public Map<JugadasDisponibles, Integer> getPuintajes() {
        return puintajes;
    }

    /**
     * Establece el mapa que define los puntajes asociados a cada tipo de
     * jugada.
     *
     * @param puintajes El nuevo mapa de puntajes por jugada.
     */
    public void setPuintajes(Map<JugadasDisponibles, Integer> puintajes) {
        this.puintajes = puintajes;
    }

}
