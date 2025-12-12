package modelo;

import enums.Pantalla;

/**
 * Implementación de la interfaz IModeloControlAplicacion.
 *
 * Actúa como la capa de control de la aplicación dentro del modelo, gestionando
 * las acciones provenientes de los controladores de la UI y delegando estas
 * peticiones al ModeloVistaConfiguracionFacade para actualizar el estado del
 * modelo y notificar a la vista.
 *
 * @author Alici
 */
public class ModeloControlAplicacion implements IModeloControlAplicacion {

    /**
     * Fachada central del modelo que maneja la lógica de actualización de los
     * sujetos (Subjects) y notifica a la vista.
     */
    ModeloVistaConfiguracionFacade modeloFacade = ModeloVistaConfiguracionFacade.getInstance();

    /**
     * Solicita al modelo de la vista cambiar el avatar seleccionado.
     *
     * @param accion Indica la dirección o el tipo de cambio del avatar (ej. 1
     * para siguiente, -1 para anterior).
     */
    @Override
    public void siguienteAvatar(int accion) {
        modeloFacade.actualizarAvatar(accion);
    }

    /**
     * Solicita al modelo de la vista actualizar la pantalla actual de la
     * aplicación para la navegación de la UI.
     *
     * @param pantallaSiguiente El enumerador Pantalla que indica la nueva vista
     * a mostrar.
     */
    public void siguientePantalla(Pantalla pantallaSiguiente) {
        modeloFacade.actualizarPantalla(pantallaSiguiente);
    }

}
