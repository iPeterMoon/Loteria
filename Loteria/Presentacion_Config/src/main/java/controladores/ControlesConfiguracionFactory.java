package controladores;

import modelo.IModeloControlAplicacion;
import modelo.IModeloControlNegocio;

/**
 *
 * @author Alici
 */
public class ControlesConfiguracionFactory {

    private static ControlesConfiguracionFactory instancia;

    private ControlConfiguracion controlConfiguracion;
    private ControlAplicacion controlAplicacion;
    private ControlIniciarPartida controlIniciarPartida;

    /**
     * Constructor privado para evitar la instanciación externa.
     */
    private ControlesConfiguracionFactory() {
    }

    public static ControlesConfiguracionFactory getInstance() {
        if (instancia == null) {
            instancia = new ControlesConfiguracionFactory();
        }
        return instancia;
    }

    public void inicializar(IModeloControlNegocio modeloControl, IModeloControlAplicacion modeloAplicacion) {
        if (this.controlConfiguracion != null) {
            //Evitar doble inicialización
            return;
        }
        this.controlConfiguracion = new ControlConfiguracion(modeloControl);

        if (this.controlAplicacion != null) {
            //Evitar doble inicialización
            return;
        }
        this.controlAplicacion = new ControlAplicacion(modeloAplicacion);
        
        if (this.controlIniciarPartida != null) {
            //Evitar doble inicialización
            return;
        }
        this.controlIniciarPartida = new ControlIniciarPartida(modeloControl);
        
    }

    /**
     * Metodo que regresa el control para la selección de una carta
     *
     * @return el Control para seleccionar una carta
     */
    public ControlAplicacion getControlAplicacion() {
        if (controlAplicacion == null) {
            throw new IllegalStateException("El Registro de Controles no ha sido inicializado. Llame a inicializar() en Arrancador");
        }
        return controlAplicacion;
    }

    public ControlConfiguracion getControlConfiguracion() {
        if (controlConfiguracion == null) {
            throw new IllegalStateException("El Registro de Controles no ha sido inicializado. Llame a inicializar() en Arrancador");
        }
        return controlConfiguracion;
    }
    
    public ControlIniciarPartida getControlIniciarPartida() {
        if(controlIniciarPartida == null){
            throw new IllegalStateException("El Registro de Controles no ha sido inicializado. Llame a inicializar() en el Arrancador");
        }
        return controlIniciarPartida;
    }

}
