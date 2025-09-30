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
public class ModeloControlImp implements IModeloControl {

    public ModeloControlImp() {
    }

    @Override
    public void colocarFicha(Point posicion) {
        IModeloJuego modeloJuego = new ModeloJuegoImp();
        modeloJuego.validaMovimiento(posicion);
    }
}
