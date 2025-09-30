/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modeloJuego;

import java.util.Stack;

/**
 *
 * @author Alici
 */
public class Cantador {

    private int cartaActual;
    private Stack<Integer> mazo;

    public Cantador() {
    }

    public int getCartaActual() {
        return cartaActual;
    }

    public void setCartaActual(int cartaActual) {
        this.cartaActual = cartaActual;
    }

    public Stack<Integer> getMazo() {
        return mazo;
    }

    public void setMazo(Stack<Integer> mazo) {
        this.mazo = mazo;
    }

}
