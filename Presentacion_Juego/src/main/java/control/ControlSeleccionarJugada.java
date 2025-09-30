/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import java.awt.Point;
import modelo.IModeloControl;
import modelo.ModeloControlImp;

/**
 *
 * @author Jp
 */
public class ControlSeleccionarJugada {

    private static IModeloControl controlModelo;

    // Método para inicializar el control
    public static void setControlModelo(IModeloControl control) {
        controlModelo = control;
    }

    public static void colocarFicha(Point posicion) {
        if (controlModelo != null) {
            controlModelo.colocarFicha(posicion);
        } else {
            throw new IllegalStateException("ControlModelo no inicializado");
        }
    }
}
