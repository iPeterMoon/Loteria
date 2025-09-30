/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.awt.Point;

/**
 *
 * @author rocha
 */
public class TarjetaSubject extends Subject {
    private int[][] cartas;
    private boolean[][] fichas;

    public TarjetaSubject(int[][] cartas) {
        this.cartas = cartas;
        this.fichas = new boolean[4][4];
    }
    
    public void agregarFicha(Point posicion) {
        this.fichas[posicion.x][posicion.y] = true;
    }
}
