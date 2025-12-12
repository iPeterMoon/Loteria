package modelo;

import enums.TipoConfiguracion;
import util.Subject;

/**
 * Clase que actúa como Sujeto (Subject) en el patrón Observador, encargada de
 * gestionar el estado actual de la configuración de la partida, principalmente
 * si el usuario está creando una sala o uniéndose a una existente.
 *
 * Notifica a los observadores (vistas o controladores) cada vez que el tipo de
 * configuración cambia, permitiendo que la interfaz se adapte dinámicamente.
 *
 * @author Alici
 */
public class ConfiguracionSubject extends Subject {

    /**
     * Define el modo de configuración actual: crear una nueva sala, unirse a
     * una existente, o cualquier otro tipo definido en TipoConfiguracion.
     */
    private TipoConfiguracion tipoConfiguracion;

    /**
     * Constructor por defecto de ConfiguracionSubject. Inicializa el tipo de
     * configuración en CREAR_SALA por defecto.
     */
    public ConfiguracionSubject() {
        setTipoConfiguracion(TipoConfiguracion.CREAR_SALA);
    }

    /**
     * Obtiene el tipo de configuración actual.
     *
     * @return El TipoConfiguracion actualmente establecido.
     */
    public TipoConfiguracion getTipoConfiguracion() {
        return tipoConfiguracion;
    }

    /**
     * Establece un nuevo tipo de configuración y notifica a todos los
     * observadores registrados sobre el cambio.
     *
     * @param tipoConfiguracion El nuevo TipoConfiguracion a establecer.
     */
    public void setTipoConfiguracion(TipoConfiguracion tipoConfiguracion) {
        this.tipoConfiguracion = tipoConfiguracion;
        notifyAllObservers();
    }
}
