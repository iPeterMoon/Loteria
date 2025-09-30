/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modeloJuego;

import java.awt.Point;
import java.util.Map;

/**
 *
 * @author Alici
 */
public class Tarjeta {

    private Map<Point, Integer> cartas;
    private Map<Point, Boolean> fichas;

    public Tarjeta() {
    }

    public Tarjeta(Map<Point, Integer> cartas) {
        this.cartas = cartas;
    }

    public Map<Point, Integer> getCartas() {
        return cartas;
    }

    public void setCartas(Map<Point, Integer> cartas) {
        this.cartas = cartas;
    }

    public Map<Point, Boolean> getFichas() {
        return fichas;
    }

    public void setFichas(Map<Point, Boolean> fichas) {
        this.fichas = fichas;
    }

    public void addFicha(Point punto, Boolean esSeleccionado) {
        fichas.put(punto, esSeleccionado);
    }

}
