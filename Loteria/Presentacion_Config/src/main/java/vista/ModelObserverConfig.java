package vista;

import util.IObserver;
import util.Subject;
import ventanas.FrameConfiguracion;

/**
 * Clase que actúa como el Observador principal (Observer) en el patrón
 * Observador/Sujeto (Subject).
 *
 * Esta clase es responsable de escuchar los cambios en los Subjects del modelo
 * y de notificar a la ventana principal de la configuración para que actualice
 * su contenido o realice acciones de navegación.
 *
 * @author Alici
 */
public class ModelObserverConfig implements IObserver {

    /**
     * Método invocado por los Subjects cuando su estado cambia.
     *
     * @param object El objeto (Sujeto) que ha notificado el cambio.
     */
    @Override
    public void update(Object object) {
        FrameConfiguracion frame = FrameConfiguracion.getInstancia();
        if (object instanceof Subject subject) {
            frame.actualizarVista(subject);
        }
    }
}
