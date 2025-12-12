package modelo;

import util.Subject;

/**
 * Clase que actúa como Sujeto (Subject) en el patrón Observador, responsable de
 * gestionar el estado del avatar seleccionado por el usuario.
 *
 * Mantiene el número de avatar actual y notifica a sus observadores (vistas)
 * cada vez que el avatar cambia, típicamente al moverse a la izquierda o a la
 * derecha.
 *
 * @author Alici
 */
public class AvatarSubject extends Subject {

    /**
     * Número de identificación del avatar actualmente seleccionado.
     * Inicialmente se establece en 1.
     */
    private int numeroAvatar = 1;

    /**
     * Número máximo de avatares disponibles en la aplicación.
     */
    private final int MAX_AVATARES = 13;

    /**
     * Mueve la selección del avatar una posición hacia la izquierda. Si la
     * selección se encuentra en el primer avatar (1), salta al último avatar
     * disponible (MAX_AVATARES).
     *
     * Después del cambio, notifica a todos los observadores.
     */
    public void moverIzquierda() {
        if (numeroAvatar - 1 >= 1) {
            numeroAvatar -= 1;
        } else {
            // Regresar al último avatar si está en el primero
            numeroAvatar = MAX_AVATARES;
        }
        this.notifyAllObservers();
    }

    /**
     * Mueve la selección del avatar una posición hacia la derecha. Si la
     * selección se encuentra en el último avatar (MAX_AVATARES), salta al
     * primer avatar disponible (1).
     *
     * <p>
     * Después del cambio, notifica a todos los observadores.</p>
     */
    public void moverDerecha() {
        if (numeroAvatar + 1 <= MAX_AVATARES) {
            numeroAvatar += 1;
        } else {
            // Regresar al primer avatar si está en el último
            numeroAvatar = 1;
        }
        this.notifyAllObservers();
    }

    /**
     * Obtiene el número de identificación del avatar actualmente seleccionado.
     *
     * @return El número de avatar actual (entre 1 y MAX_AVATARES).
     */
    public int getNumeroAvatar() {
        return numeroAvatar;
    }
}
