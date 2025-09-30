/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.awt.Point;

import dtos.FichaDTO;
import interfaces.IModeloVista;

/**
 *
 * @author Alici
 */
public class ModeloJuegoImp implements IModeloJuego {

    private IModeloVista vista;

    public ModeloJuegoImp(IModeloVista vista) {
        this.vista = vista;
    }
    
    @Override
    public void validaMovimiento(Point posicion) {
       // Se debe de obtener la tarjeta del jugador
       // obtener la carta en la posicion dada.
       // verificar la carta actual
       //si son iguales es decir la carta en la posicion actual y la carta cantada actual
            // modeloVista.colocarFicha(posicion);
       // en caso contrario no pasaria nada
       validarMovimientoMock(posicion);
    }

    /**
     * METODO POR MIENTRAS PARA PROBAR EL FLUJO EN LA VISTA
     * @param posicion
     */
    public void validarMovimientoMock(Point posicion){
        FichaDTO ficha = new FichaDTO("Jerson", posicion);
        vista.colocarFicha(ficha);
    }


}
