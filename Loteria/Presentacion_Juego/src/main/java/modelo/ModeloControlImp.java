package modelo;

import java.awt.Point;

import interfaces.IModeloJuego;

/**
 * Implementación de la interfaz IModeloControl.
 *
 * Recibe las acciones del control y las delega para actualizar el estado del
 * juego.
 *
 * @author rocha
 */
public class ModeloControlImp implements IModeloControl {

    private IModeloJuego modeloJuego;

    /**
     * Constructor vacío
     */
    public ModeloControlImp(IModeloJuego modeloJuego) {
        this.modeloJuego = modeloJuego;
    }

    /**
     * Coloca una ficha en la posición indicada.
     *
     * que se encarga de verificar y procesar la jugada dentro del modelo del
     * juego mediante el objeto IModeloJuego.
     *
     * @param posicion Objeto Point que representa la coordenada (x, y) donde se
     * desea colocar la ficha.
     */
    @Override
    public void colocarFicha(Point posicion) {
        modeloJuego.validaMovimiento(posicion);
    }

    /**
     * Devuelve la interfaz del modelo del juego que utiliza para comunicarse.
     * @return La interfaz del modelo de juego.
     */
    public IModeloJuego getModeloJuego(){
        return modeloJuego;
    }
}
