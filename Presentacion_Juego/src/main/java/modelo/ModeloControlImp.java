package modelo;

import java.awt.Point;
import modeloJuego.IModeloJuego;
import modeloJuego.ModeloJuegoImp;

/**
 * Implementación de la interfaz IModeloControl.
 *
 * Recibe las acciones del control y las delega para actualizar el estado del
 * juego.
 *
 * @author rocha
 */
public class ModeloControlImp implements IModeloControl {

    /**
     * Constructor vacío
     */
    public ModeloControlImp() {
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
        IModeloJuego modeloJuego = ModeloJuegoImp.getInstance();
        modeloJuego.validaMovimiento(posicion);
    }
}
