package controladores;

import dtos.aplicacion.ConfiguracionJuegoDTO;
import dtos.aplicacion.NuevoUsuarioDTO;
import modelo.IModeloControlNegocio;

/**
 * Clase controladora responsable de manejar todas las operaciones relacionadas
 * con la configuración del usuario y la sala de juego. Actúa como intermediario
 * entre la capa de presentación (vista) y la capa de negocio (modelo) para la
 * inicialización y configuración de partidas.
 *
 * @author Alici
 */
public class ControlConfiguracion {

    /**
     * Referencia a la interfaz del modelo de negocio, utilizada para invocar la
     * lógica de configuración y gestión de salas.
     */
    private final IModeloControlNegocio controlModelo;

    /**
     * Constructor de la clase ControlConfiguracion.
     *
     * @param controlModelo La implementación de la interfaz
     * IModeloControlNegocio que maneja la lógica de negocio. No puede ser nula.
     * @throws IllegalArgumentException si el parámetro controlModelo es nulo.
     */
    public ControlConfiguracion(IModeloControlNegocio controlModelo) {
        if (controlModelo == null) {
            throw new IllegalArgumentException("La dependencia IModeloControlConfiguracion no puede ser nula.");
        }
        this.controlModelo = controlModelo;
    }

    /**
     * Configura el usuario actual y solicita al modelo que cree una nueva sala
     * de juego. Este método se utiliza típicamente cuando un usuario decide ser
     * el anfitrión de una partida.
     *
     * @param nuevoUsuario Objeto DTO que contiene los datos de configuración
     * del usuario que crea la sala.
     */
    public void configurarUsuarioNuevaSala(NuevoUsuarioDTO nuevoUsuario) {
        controlModelo.configurarUsuarioNuevaSala(nuevoUsuario);
    }

    /**
     * Configura el usuario actual y solicita al modelo que lo una a una sala de
     * juego existente.
     *
     * @param nuevoUsuario Objeto DTO que contiene los datos de configuración
     * del usuario que se une a la sala.
     */
    public void unirseSala(NuevoUsuarioDTO nuevoUsuario) {
        controlModelo.unirseSala(nuevoUsuario);
    }

    /**
     * Envía la configuración específica de la partida al modelo para establecer
     * los parámetros del juego en la sala.
     *
     * @param configuracionJuego Objeto DTO con los parámetros de configuración
     * de la partida.
     */
    public void configurarPartida(ConfiguracionJuegoDTO configuracionJuego) {
        controlModelo.configurarPartida(configuracionJuego);
    }

    /**
     * Notifica al modelo de negocio que el usuario actual desea abandonar la
     * sala de juego.
     */
    public void abandonarSala() {
        controlModelo.abandonarSala();
    }
}
