package control;

import java.awt.Point;
import modelo.IModeloControl;
import modelo.ModeloControlImp;
import modeloJuego.ModeloJuegoImp;

/**
 * Clase Control que mantiene una conexion entre las clases
 * @author Jp
 */
public class ControlSeleccionarJugada {

    // Método para inicializar el control
    private static IModeloControl controlModelo = new ModeloControlImp();
    /**
     * Metodo de control para colocar una ficha
     * @param posicion posicion de la ficha
     */
    public static void colocarFicha(Point posicion) {
        if (controlModelo != null) {
            controlModelo.colocarFicha(posicion);
        } else {
            throw new IllegalStateException("ControlModelo no inicializado");
        }
    }
}
