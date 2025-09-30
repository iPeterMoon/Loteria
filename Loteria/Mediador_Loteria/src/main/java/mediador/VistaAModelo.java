package mediador;

import java.awt.Point;
import modelo.IModeloJuego;
import modelo.ModeloJuegoImp;

/**
 *
 * @author pedro
 */
public class VistaAModelo {
    
    public void colocarFicha(Point posicion){
        IModeloJuego modeloJuego = new ModeloJuegoImp();
        modeloJuego.validaMovimiento(posicion);
    }
}
