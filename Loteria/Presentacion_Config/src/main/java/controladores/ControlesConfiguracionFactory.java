package controladores;

import modelo.IModeloControlAplicacion;
import modelo.IModeloControlNegocio;

/**
 * Fábrica (Factory) e implementador del patrón Singleton para la gestión
 * centralizada de todas las instancias de las clases controladoras de la
 * aplicación.
 *
 * Esta clase asegura que solo exista una instancia de sí misma y proporciona un
 * punto de acceso único para inicializar y obtener los controladores
 * principales: ControlConfiguracion, ControlAplicacion y ControlIniciarPartida.
 *
 * @author Alici
 */
public class ControlesConfiguracionFactory {

    /**
     * Instancia única de la fábrica (Singleton).
     */
    private static ControlesConfiguracionFactory instancia;

    /**
     * Controlador para las operaciones de configuración de usuario y sala.
     */
    private ControlConfiguracion controlConfiguracion;

    /**
     * Controlador para la navegación y gestión del flujo de la aplicación
     * (pantallas, ventanas).
     */
    private ControlAplicacion controlAplicacion;

    /**
     * Controlador para iniciar y gestionar las lógicas iniciales de la partida.
     */
    private ControlIniciarPartida controlIniciarPartida;

    /**
     * Constructor privado para evitar la instanciación externa y asegurar el
     * patrón Singleton.
     */
    private ControlesConfiguracionFactory() {
    }

    /**
     * Devuelve la instancia única de la fábrica (Singleton). Si la instancia no
     * existe, la crea antes de devolverla.
     *
     * @return La instancia única de ControlesConfiguracionFactory.
     */
    public static ControlesConfiguracionFactory getInstance() {
        if (instancia == null) {
            instancia = new ControlesConfiguracionFactory();
        }
        return instancia;
    }

    /**
     * Inicializa todos los controladores de la aplicación inyectando sus
     * respectivas dependencias del modelo. Este método debe ser llamado una
     * única vez al inicio de la aplicación (generalmente en la clase
     * Arrancador).
     *
     * @param modeloControl Implementación del modelo para la lógica de negocio
     * (ControlConfiguracion, ControlIniciarPartida).
     * @param modeloAplicacion Implementación del modelo para la lógica de la
     * aplicación (ControlAplicacion).
     */
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
     * Obtiene la instancia del controlador de Aplicación, utilizado para la
     * gestión de la navegación y el flujo de la UI.
     *
     * @return El ControlAplicacion inicializado.
     * @throws IllegalStateException Si la fábrica no ha sido inicializada
     * previamente con el método inicializar().
     */
    public ControlAplicacion getControlAplicacion() {
        if (controlAplicacion == null) {
            throw new IllegalStateException("El Registro de Controles no ha sido inicializado. Llame a inicializar() en Arrancador");
        }
        return controlAplicacion;
    }

    /**
     * Obtiene la instancia del controlador de Configuración, utilizado para
     * manejar la lógica de configuración de usuarios y salas.
     *
     * @return El ControlConfiguracion inicializado.
     * @throws IllegalStateException Si la fábrica no ha sido inicializada
     * previamente con el método inicializar().
     */
    public ControlConfiguracion getControlConfiguracion() {
        if (controlConfiguracion == null) {
            throw new IllegalStateException("El Registro de Controles no ha sido inicializado. Llame a inicializar() en Arrancador");
        }
        return controlConfiguracion;
    }

    /**
     * Obtiene la instancia del controlador de Inicio de Partida, utilizado para
     * la lógica previa al comienzo del juego.
     *
     * @return El ControlIniciarPartida inicializado.
     * @throws IllegalStateException Si la fábrica no ha sido inicializada
     * previamente con el método inicializar().
     */
    public ControlIniciarPartida getControlIniciarPartida() {
        if (controlIniciarPartida == null) {
            throw new IllegalStateException("El Registro de Controles no ha sido inicializado. Llame a inicializar() en el Arrancador");
        }
        return controlIniciarPartida;
    }

}
