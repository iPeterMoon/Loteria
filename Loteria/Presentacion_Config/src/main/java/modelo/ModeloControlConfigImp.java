package modelo;

import dtos.aplicacion.ConfiguracionJuegoDTO;
import dtos.aplicacion.NuevoUsuarioDTO;
import interfaces.aplicacion.IModeloJuego;

/**
 * Implementación de la interfaz IModeloControlNegocio.
 *
 * Actúa como la capa de control de negocio, recibiendo peticiones de los
 * controladores y delegando la lógica de negocio principal (configuración de
 * usuarios, salas, inicio de partida) al IModeloJuego (Manager/Servicio).
 *
 * También se encarga de inyectar datos del estado actual del modelo de vista
 * (como el avatar seleccionado) en los DTOs antes de pasarlos al Modelo de
 * Juego.
 *
 * @author norma
 */
public class ModeloControlConfigImp implements IModeloControlNegocio {

    /**
     * Referencia a la interfaz principal del modelo de juego, donde reside la
     * lógica de negocio para la configuración de salas y usuarios.
     */
    private IModeloJuego modeloJuego;

    /**
     * Constructor de la implementación del control de configuración de negocio.
     *
     * @param modeloJuego La implementación de la interfaz IModeloJuego para la
     * delegación de tareas.
     */
    public ModeloControlConfigImp(IModeloJuego modeloJuego) {
        this.modeloJuego = modeloJuego;
    }

    /**
     * Procesa la solicitud para que un usuario se una a una sala existente.
     * Obtiene el avatar seleccionado del modelo de vista antes de delegar la
     * acción.
     *
     * @param usuarioNuevo DTO con los datos del usuario que desea unirse.
     */
    @Override
    public void unirseSala(NuevoUsuarioDTO usuarioNuevo) {
        // Asigna el avatar seleccionado actualmente en la vista al DTO
        usuarioNuevo.setIdAvatarSeleccionado(ModeloVistaConfiguracionFacade.getInstance().getAvatarSubject().getNumeroAvatar());
        modeloJuego.unirseSala(usuarioNuevo);
    }

    /**
     * Procesa la solicitud para configurar un usuario que será el anfitrión de
     * una nueva sala. Obtiene el avatar seleccionado del modelo de vista antes
     * de delegar la acción.
     *
     * @param usuarioNuevo DTO con los datos del usuario que creará la sala.
     */
    @Override
    public void configurarUsuarioNuevaSala(NuevoUsuarioDTO usuarioNuevo) {
        // Asigna el avatar seleccionado actualmente en la vista al DTO
        usuarioNuevo.setIdAvatarSeleccionado(ModeloVistaConfiguracionFacade.getInstance().getAvatarSubject().getNumeroAvatar());
        modeloJuego.configurarUsuarioNuevaSala(usuarioNuevo);
    }

    /**
     * Procesa la configuración de la partida definida por el anfitrión,
     * delegando la creación de la sala al modelo de juego.
     *
     * @param configuracion DTO con los parámetros de configuración del juego.
     */
    @Override
    public void configurarPartida(ConfiguracionJuegoDTO configuracion) {
        modeloJuego.crearNuevaSala(configuracion);
    }

    /**
     * Procesa la solicitud para abandonar la sala actual.
     */
    @Override
    public void abandonarSala() {
        modeloJuego.abandonarSala();
    }

    /**
     * Procesa la solicitud para iniciar la partida de juego.
     */
    @Override
    public void iniciarPartida() {
        modeloJuego.iniciarPartida();
    }

}
