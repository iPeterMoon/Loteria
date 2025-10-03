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

    /**
     * Coloca una ficha en la posicion indicada dentro del tablero este metodo
     * delega la operacion al objeto {@code controlModelo} siempre que dicho
     * objeto haya sido previamente incializando en caso de que
     * {@code controlModelo} sea {@code null} se lanza una excepcion para
     * indicar que el modelo aun no esta disponible
     *
     * @param posicion
     */
    public static void colocarFicha(Point posicion) {
        if (controlModelo != null) {
            controlModelo.colocarFicha(posicion);
        } else {
            throw new IllegalStateException("ControlModelo no inicializado");
        }
    }
}
