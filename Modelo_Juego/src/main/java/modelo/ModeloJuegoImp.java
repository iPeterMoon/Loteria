/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.awt.Point;

/**
 *
 * @author Alici
 */
public class ModeloJuegoImp implements IModeloJuego {

    IModeloVista modeloVista = new ModeloVistaFacade();

    @Override
    public void validaMovimiento(Point posicion) {
       // Se debe de obtener la tarjeta del jugador
       // obtener la carta en la posicion dada.
       // verificar la carta actual
       //si son iguales es decir la carta en la posicion actual y la carta cantada actual
            // modeloVista.colocarFicha(posicion);
       // en caso contrario no pasaria nada
    }

}
