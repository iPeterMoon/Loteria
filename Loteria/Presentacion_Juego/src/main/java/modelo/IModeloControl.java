/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modelo;

import java.awt.Point;

/**
 * Interfaz que define el contrato para la comunicación entre el modelo de la
 * vista y el control.
 *
 * @author rocha
 */
public interface IModeloControl {

    /**
     * Coloca una ficha en la posición indicada.
     *
     * @param posicion Objeto Point que representa la coordenada (x, y) donde se
     * colocó la ficha.
     */
    public void colocarFicha(Point posicion);

    /**
     * Verifica si se cumple la condición para la jugada de tipo llena.
     * <p>
     * Este método evalúa el estado actual del juego para determinar si la
     * jugada es válida según sus reglas.
     * </p>
     */
    public void verificarJugadaLlena();

    /**
     * Verifica si se cumple la condición para la jugada de tipo chorro.
     * <p>
     * Este método evalúa el estado actual del juego para determinar si la
     * jugada es válida según sus reglas.
     * </p>
     */
    public void verificarJugadaChorro();

    /**
     * Verifica si se cumple la condición para la jugada de tipo centro.
     * <p>
     * Este método evalúa el estado actual del juego para determinar si la
     * jugada es válida según sus reglas.
     * </p>
     */
    public void verificarJugadaCentro();

    /**
     * Verifica si se cumple la condición para la jugada de tipo cuatro
     * esquinas.
     * <p>
     * Este método evalúa el estado actual del juego para determinar si la
     * jugada es válida según sus reglas.
     * </p>
     */
    public void verificarJugadaCuatroEsquinas();
    
    public void abandonarPartida();

}
