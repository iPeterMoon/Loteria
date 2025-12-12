package modelo;

import enums.Pantalla;

/**
 * Interfaz que define las operaciones que la capa de control de la aplicación
 * (ControlAplicacion) puede solicitar a la capa del Modelo para gestionar el
 * flujo de la aplicación y la selección de elementos de la interfaz, sin
 * exponer la complejidad interna del Modelo.
 *
 * @author Alici
 */
public interface IModeloControlAplicacion {

    /**
     * Solicita al modelo el cambio del avatar seleccionado por el usuario. Esta
     * acción debe ser delegada a la lógica que gestiona el avatar.
     *
     * @param accion Indica la dirección o el tipo de cambio del avatar (ej. 1
     * para siguiente, -1 para anterior).
     */
    public void siguienteAvatar(int accion);

    /**
     * Solicita al modelo cambiar la pantalla actual de la interfaz de usuario.
     * Esta acción debe ser delegada a la lógica que gestiona la navegación (ej.
     * un Subject de Pantallas).
     *
     * @param pantallaSiguiente El enumerador Pantalla que indica la nueva vista
     * a mostrar.
     */
    public void siguientePantalla(Pantalla pantallaSiguiente);
}
