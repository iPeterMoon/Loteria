package vista;

import util.IObserver;
import util.Subject;

/**
 * Clase que representa el ModeloObserver que implementa de IObserver para estar
 * actualizando los cambios durante el juego.
 *
 * @author nose
 */
public class ModelObserver implements IObserver {

    /**
     * Metodo que llama la instacia del frame para actualizar la vista.
     *
     * @param object representacion del sujeto (Objeto de tipo Subject).
     */
    @Override
    public void update(Object object) {
        FrameJuego frame = FrameJuego.getInstance();
        if (object instanceof Subject subject) {
            frame.actualizarVista(subject);
        }
    }

}
