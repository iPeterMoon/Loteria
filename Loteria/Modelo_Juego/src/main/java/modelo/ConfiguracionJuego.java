/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import enums.JugadasDisponibles;
import enums.TipoNivel;
import java.util.Map;

/**
 *
 * @author Alici
 */
public class ConfiguracionJuego {

    private int limiteJugadores;
    private int puntajeMax;
    private TipoNivel dificultad;
    private Map<JugadasDisponibles, Integer> puintajes;

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

    public Map<JugadasDisponibles, Integer> getPuintajes() {
        return puintajes;
    }

    public void setPuintajes(Map<JugadasDisponibles, Integer> puintajes) {
        this.puintajes = puintajes;
    }

}
