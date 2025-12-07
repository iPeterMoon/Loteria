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
}
