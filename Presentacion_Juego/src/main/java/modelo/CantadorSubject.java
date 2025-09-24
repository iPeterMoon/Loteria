/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author rocha
 */
public class CantadorSubject extends Subject {
    private int cartaActual;

    public CantadorSubject() {
    }

    public int getCartaActual() {
        return cartaActual;
    }
    
    public void actualizarCarta(int carta) {
        this.cartaActual = carta;
    }

    @Override
    public String toString() {
        return "CantadorSubject{" + "cartaActual=" + cartaActual + '}';
    }
    
}
