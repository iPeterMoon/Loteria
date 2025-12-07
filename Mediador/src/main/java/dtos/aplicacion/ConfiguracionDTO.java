/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos.aplicacion;

import enums.JugadasDisponibles;
import enums.TipoNivel;
import java.util.Map;

/**
 *
 * @author Alici
 */
public class ConfiguracionDTO {

    private int limiteJugadores;
    private int puntajeMax;
    private Map<JugadasDisponibles, Integer> puntajes;
    private TipoNivel dificultad;

    public ConfiguracionDTO() {
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

    public Map<JugadasDisponibles, Integer> getPuntajes() {
        return puntajes;
    }

    public void setPuntajes(Map<JugadasDisponibles, Integer> puntajes) {
        this.puntajes = puntajes;
    }

    public TipoNivel getDificultad() {
        return dificultad;
    }

    public void setDificultad(TipoNivel dificultad) {
        this.dificultad = dificultad;
    }

}
