package control;

import java.awt.Point;
import modelo.IModeloControl;

/**
 * Clase Control que mantiene una conexion entre las clases
 * @author Jp
 */
public class ControlSeleccionarCarta {

    private final IModeloControl controlModelo;

    /**
     * Constructor que recibe la dependencia de IModeloControl.
     * @param controlModelo La instancia de la interfaz de control del modelo.
     */
    public ControlSeleccionarCarta(IModeloControl controlModelo){
        if (controlModelo == null) {
            throw new IllegalArgumentException("La dependencia IModeloControl no puede ser nula.");
        }
        this.controlModelo = controlModelo;
    }

    /**
     * Metodo de control para colocar una ficha
     * @param posicion posicion de la ficha
     */
    public void colocarFicha(Point posicion) {
            controlModelo.colocarFicha(posicion);
    }
}
