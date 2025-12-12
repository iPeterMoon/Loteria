package modelo;

import dtos.aplicacion.ConfiguracionJuegoDTO;
import dtos.aplicacion.NuevoUsuarioDTO;

/**
 * Interfaz que define las operaciones de control de negocio relacionadas con la
 * configuración, la gestión de usuarios y el inicio de partidas.
 *
 * Actúa como el contrato que deben seguir las implementaciones de la capa de
 * negocio (ModeloControlConfigImp) para recibir peticiones de los controladores
 * de configuración.
 *
 * @author norma
 */
public interface IModeloControlNegocio {

    /**
     * Procesa la solicitud para que un usuario se una a una sala existente.
     *
     * @param usuario DTO con los datos de configuración del usuario que desea
     * unirse.
     */
    public void unirseSala(NuevoUsuarioDTO usuario);

    /**
     * Procesa la solicitud para configurar un usuario que actuará como
     * anfitrión al crear una nueva sala de juego.
     *
     * @param usuarioNuevo DTO con los datos de configuración del usuario que
     * creará la sala.
     */
    public void configurarUsuarioNuevaSala(NuevoUsuarioDTO usuarioNuevo);

    /**
     * Procesa la solicitud para establecer la configuración específica de la
     * partida (reglas, dificultad, límites).
     *
     * @param configuracion DTO con los parámetros de configuración del juego.
     */
    public void configurarPartida(ConfiguracionJuegoDTO configuracion);

    /**
     * Procesa la solicitud para que el usuario actual abandone la sala de
     * juego.
     */
    public void abandonarSala();

    /**
     * Procesa la solicitud para iniciar la partida de juego. Esta acción la
     * realiza típicamente el anfitrión de la sala.
     */
    public void iniciarPartida();
}
