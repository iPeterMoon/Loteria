package vista;

import modelo.Subject;
/**
 * Clase que representa el ModeloObserver que
 * implementa de IObserver para estar actualizando los
 * cambios durante el juego.
 * @author nose
 */
public class ModelObserver implements IObserver{
    /**
     * Metodo que llama la instacia del frame 
     * para actualizar la vista.
     * @param subject representacion del sujeto.
     */
    @Override
    public void update(Subject subject) {
        FrameJuego frame = FrameJuego.getInstance();
        frame.actualizarVista(subject);
    }
    
}
