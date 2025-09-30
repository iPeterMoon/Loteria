package vista;

import modelo.Subject;

public class ModelObserver implements IObserver{

    @Override
    public void update(Subject subject) {
        FrameJuego frame = FrameJuego.getInstance();
        frame.actualizarVista(subject);
    }
    
}
