package controladores;

import modelo.IModeloControlNegocio;

/**
 *
 * @author petermoon
 */
public class ControlIniciarPartida {
    private final IModeloControlNegocio controlModelo;

    public ControlIniciarPartida(IModeloControlNegocio controlModelo) {
        if (controlModelo == null) {
            throw new IllegalArgumentException("La dependencia IModeloControlConfiguracion no puede ser nula.");
        }
        this.controlModelo = controlModelo;
    }
    
    public void iniciarPartida(){
        controlModelo.iniciarPartida();
    }
}
