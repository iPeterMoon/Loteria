package modelo;

import java.awt.Point;
import modeloJuego.IModeloJuego;
import modeloJuego.ModeloJuegoImp;

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
