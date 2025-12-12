package enums;

/**
 * Enumerador que define las posibles pantallas o vistas de la interfaz de
 * usuario de la aplicación.
 *
 * Cada constante tiene asociado un nombre interno (String) que puede ser
 * utilizado para la gestión del CardLayout en la vista principal o para
 * propósitos de navegación.
 *
 * @author Alici
 */
public enum Pantalla {
    /**
     * Pantalla del menú principal de la aplicación.
     */
    MENU("menu"),
    /**
     * Pantalla para que el usuario configure su avatar y nombre antes de crear
     * o unirse a una sala.
     */
    CONFIGURAR_USUARIO("configurar_usuario"),
    /**
     * Pantalla para que el anfitrión configure los parámetros de la partida.
     */
    CONFIGURAR_PARTIDA("configurar_partida"),
    /**
     * Pantalla de sala de espera cuando el usuario ya se ha unido a la partida.
     */
    SALA_ESPERA("sala_espera"),
    /**
     * Pantalla de sala de espera inicial, utilizada para mostrar información de
     * la sala antes de que el usuario haya configurado su perfil y se haya
     * unido formalmente.
     */
    SALA_ESPERA_NO_UNIDO("sala_espera_no_unido"),
    /**
     * Indicador para la acción de cerrar la aplicación o ventana.
     */
    CERRAR("cerrar");

    /**
     * Nombre interno de la pantalla, utilizado para la identificación en el
     * CardLayout.
     */
    private final String nombre;

    /**
     * Constructor para inicializar una constante del enumerador con su nombre
     * asociado.
     *
     * @param nombre El nombre de la pantalla.
     */
    Pantalla(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el nombre interno de la pantalla.
     *
     * @return El nombre (String) asociado a la constante del enumerador.
     */
    public String getNombre() {
        return nombre;
    }

}
