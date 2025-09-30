package modelo;

import java.awt.Point;
import mediador.VistaAModelo;

/**
 *
 * @author rocha
 */
public class ModeloControlImp implements IModeloControl {

    public ModeloControlImp() {
    }

    @Override
    public void colocarFicha(Point posicion) {
        VistaAModelo mediador = new VistaAModelo();
        mediador.colocarFicha(posicion);
    }
}
