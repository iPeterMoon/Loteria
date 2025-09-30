package modelo;

import interfaces.IModeloJuego;
import java.awt.Point;
/**
 *
 * @author rocha
 */
public class ModeloControlImp implements IModeloControl {

    private IModeloJuego modeloJuego;

    public ModeloControlImp(IModeloJuego modeloJuego) {
        this.modeloJuego = modeloJuego;
    }

    @Override
    public void colocarFicha(Point posicion) {
        modeloJuego.validaMovimiento(posicion);
    }
}
