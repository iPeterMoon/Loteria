package modelo;

import enums.Pantalla;
import util.Subject;

/**
 * Clase que actúa como Sujeto (Subject) en el patrón Observador, encargada de
 * gestionar el estado de la pantalla o vista actual que debe mostrarse en la
 * interfaz de usuario.
 *
 * Notifica a los observadores (típicamente el panel contenedor de la UI) cada
 * vez que se produce una solicitud de navegación.
 *
 * @author Alici
 */
public class PantallaActualSubject extends Subject {

    /**
     * El enumerador que representa la pantalla actual de la aplicación.
     */
    private Pantalla pantallaActual;

    /**
     * Establece la nueva pantalla actual y notifica a todos los observadores
     * registrados para que actualicen la vista.
     *
     * @param pantallaActual El enumerador Pantalla que representa la nueva
     * vista a mostrar.
     */
    public void setPantallaActual(Pantalla pantallaActual) {
        this.pantallaActual = pantallaActual;
        notifyAllObservers();
    }

    /**
     * Obtiene la pantalla actual que debe estar visible en la interfaz de
     * usuario.
     *
     * @return El enumerador Pantalla que indica la vista actual.
     */
    public Pantalla getPantallaActual() {
        return pantallaActual;
    }

}
