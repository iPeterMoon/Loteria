/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import enums.Pantalla;
import util.Subject;

/**
 *
 * @author Alici
 */
public class PantallaActualSubject extends Subject {

    private Pantalla pantallaActual;

    public void setPantallaActual(Pantalla pantallaActual) {
        this.pantallaActual = pantallaActual;
        notifyAllObservers();
    }

    public Pantalla getPantallaActual() {
        return pantallaActual;
    }

}
