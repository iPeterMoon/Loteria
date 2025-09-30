package control;

import java.awt.Point;
import modelo.IModeloControl;
import modelo.ModeloControlImp;
import modeloJuego.ModeloJuegoImp;

/**
 *
 * @author Jp
 */
public class ControlSeleccionarJugada {

    // Método para inicializar el control
    private static IModeloControl controlModelo = new ModeloControlImp();

    public static void colocarFicha(Point posicion) {
        if (controlModelo != null) {
            controlModelo.colocarFicha(posicion);
        } else {
            throw new IllegalStateException("ControlModelo no inicializado");
        }
    }
}
