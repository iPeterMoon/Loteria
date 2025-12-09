/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import util.IObserver;
import util.Subject;
import ventanas.FrameConfiguracion;

/**
 *
 * @author Alici
 */
public class ModelObserverConfig implements IObserver {

    @Override
    public void update(Object object) {
        FrameConfiguracion frame = FrameConfiguracion.getInstancia();
        if (object instanceof Subject subject) {
            frame.actualizarVista(subject);
        }

    }
}
