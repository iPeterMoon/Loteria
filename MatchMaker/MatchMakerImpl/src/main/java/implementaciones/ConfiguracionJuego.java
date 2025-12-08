package implementaciones;

import enums.JugadasDisponibles;
import enums.TipoNivel;
import java.util.Map;

/**
 *
 * @author norma
 */
public class ConfiguracionJuego {

    private int limiteJugadores;
    private int puntajeMax;
    private TipoNivel dificultad;
    private Map<JugadasDisponibles, Integer> puntajes;

    public ConfiguracionJuego() {
    }

    public ConfiguracionJuego(int limiteJugadores, int puntajeMax, TipoNivel dificultad, Map<JugadasDisponibles, Integer> puntajes) {
        this.limiteJugadores = limiteJugadores;
        this.puntajeMax = puntajeMax;
        this.dificultad = dificultad;
        this.puntajes = puntajes;
    }

    public int getLimiteJugadores() {
        return limiteJugadores;
    }

    public void setLimiteJugadores(int limiteJugadores) {
        this.limiteJugadores = limiteJugadores;
    }

    public int getPuntajeMax() {
        return puntajeMax;
    }

    public void setPuntajeMax(int puntajeMax) {
        this.puntajeMax = puntajeMax;
    }

    public TipoNivel getDificultad() {
        return dificultad;
    }

    public void setDificultad(TipoNivel dificultad) {
        this.dificultad = dificultad;
    }

    public Map<JugadasDisponibles, Integer> getPuntajes() {
        return puntajes;
    }

    public void setPuntajes(Map<JugadasDisponibles, Integer> puntajes) {
        this.puntajes = puntajes;
    }
}
