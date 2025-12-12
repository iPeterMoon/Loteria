package managers;

import dtos.aplicacion.ConfiguracionJuegoDTO;
import dtos.aplicacion.MensajeDTO;
import dtos.aplicacion.NuevoUsuarioDTO;
import enums.JugadasDisponibles;
import enums.TipoMensajePantalla;
import eventos.eventos_aplicacion.EventoCrearSala;
import eventos.eventos_aplicacion.EventoSolicitudSala;
import interfaces.aplicacion.IModeloVistaConfiguracion;
import interfaces.peer.IPeer;
import java.util.List;
import java.util.Map;
import modelo.Jugador;
import modelo.ModeloJuegoFacade;
import modelo.Sala;
import util.ConfigLoader;

/**
 * Clase Manager responsable de gestionar la lógica de configuración del usuario
 * y de la sala de juego.
 *
 * Se encarga de la validación de datos de usuario y configuración de partida, y
 * orquesta la comunicación con el componente de red (IPeer) para la creación y
 * solicitud de salas.
 *
 * @author Alici
 */
public class ConfiguracionManager {

    /**
     * Componente para la comunicación Peer-to-Peer de la aplicación.
     */
    private IPeer componentePeer;

    /**
     * Interfaz del modelo de vista de configuración para notificar cambios a la
     * capa de presentación (UI).
     */
    private IModeloVistaConfiguracion modeloVistaConfiguracion;

    /**
     * Constante que define el número esperado de jugadas disponibles para la
     * configuración.
     */
    private final int numeroJugadasDisponibles = 4;

    /**
     * Almacena temporalmente los datos del usuario que está creando una nueva
     * sala.
     */
    private NuevoUsuarioDTO usuarioNuevaSala;

    /**
     * Configura y valida los datos de un nuevo usuario que desea ser el
     * anfitrión de una sala de juego.
     *
     * @param usuarioNuevo DTO con los datos del usuario a configurar.
     */
    public void configurarUsuarioNuevaSala(NuevoUsuarioDTO usuarioNuevo) {
        if (validarUsuario(usuarioNuevo)) {
            usuarioNuevo.setEsHost(true);
            usuarioNuevaSala = usuarioNuevo;
        }
    }

    /**
     * Inicializa las dependencias principales del manager: el componente de red
     * y el modelo de vista.
     *
     * @param peer La implementación de la interfaz IPeer.
     * @param modeloVistaConfiguracion La implementación de la interfaz
     * IModeloVistaConfiguracion.
     */
    public void inicializar(IPeer peer, IModeloVistaConfiguracion modeloVistaConfiguracion) {
        if (this.componentePeer != null) {
            // Evita doble inicialización
            return;
        }
        this.componentePeer = peer;
        this.modeloVistaConfiguracion = modeloVistaConfiguracion;
    }

    /**
     * Procesa la creación de una nueva sala de juego.
     *
     * Realiza la validación de la configuración, inicializa al jugador
     * principal, establece el usuario en el componente P2P y envía un evento de
     * sala creada al Matchmaker para su difusión.
     *
     * @param configuracionSala DTO con los parámetros de la partida
     * (dificultad, puntos, etc.).
     */
    public void crearNuevaSala(ConfiguracionJuegoDTO configuracionSala) {
        if (validarConfiguracionSala(configuracionSala)) {
            EventoCrearSala eventoSalaCreada = new EventoCrearSala(configuracionSala, usuarioNuevaSala, usuarioNuevaSala.getNickname());
            Jugador jugadorPrincipal = new Jugador();
            jugadorPrincipal.setNickname(usuarioNuevaSala.getNickname());
            jugadorPrincipal.setFotoPerfil(usuarioNuevaSala.getIdAvatarSeleccionado());

            // Validar que no exista una sala ya creada
            if (Sala.getInstance().salaCreada()) {
                MensajeDTO mensaje = new MensajeDTO("Error al crear sala", "<html>Ya fue creada una sala</html>", false, TipoMensajePantalla.VALIDACION_CONFIG_PARTIDA);
                ModeloJuegoFacade.getInstance().mostrarMensaje(mensaje);
                return;
            }

            Sala.getInstance().setJugadorPrincipal(jugadorPrincipal);
            componentePeer.setUser(usuarioNuevaSala.getNickname());

            // Actualizar SalaSubject a través de la fachada para que la UI se entere
            if (modeloVistaConfiguracion != null) {
                System.out.println("[ConfiguracionManager] Actualizando jugadorPrincipal: " + usuarioNuevaSala.getNickname());
                modeloVistaConfiguracion.actualizarJugadorPrincipal(usuarioNuevaSala.getNickname());
            } else {
                System.err.println("[ConfiguracionManager] modeloVistaConfiguracion es NULL!");
            }

            // Enviar evento a matchmaker para que se lo envíe a todos
            componentePeer.directMessage(eventoSalaCreada, ConfigLoader.getInstance().getUsuarioMatchmaker());
        }
    }

    /**
     * Envía una solicitud al Matchmaker para obtener información sobre una sala
     * de juego disponible a la cual unirse.
     */
    public void obtenerSala() {
        EventoSolicitudSala solicitarSala = new EventoSolicitudSala(null);
        componentePeer.directMessage(solicitarSala, ConfigLoader.getInstance().getUsuarioMatchmaker());
    }

    /**
     * Configura y valida los datos de un nuevo usuario que desea unirse a una
     * sala existente.
     *
     * @param usuarioNuevo DTO con los datos del usuario a configurar.
     * @return true si la validación del usuario es exitosa, false en caso
     * contrario.
     */
    public boolean configurarUsuarioUnirseSala(NuevoUsuarioDTO usuarioNuevo) {
        return validarUsuario(usuarioNuevo);
    }

    /**
     * Valida que el nombre de usuario cumpla con los requisitos (no vacío, no
     * duplicado, no reservado y longitud máxima).
     *
     * @param usuarioNuevo DTO con la información del usuario a validar.
     * @return true si el usuario es válido, false si no lo es, mostrando un
     * mensaje de error.
     */
    private boolean validarUsuario(NuevoUsuarioDTO usuarioNuevo) {
        if (usuarioNuevo == null || usuarioNuevo.getNickname().trim().isEmpty() || usuarioNuevo.getNickname().isBlank()) {
            MensajeDTO mensaje = new MensajeDTO("Usuario invalido", "<html>El nombre de usuario no puede estar vacío</html>", false, TipoMensajePantalla.VALIDACION_USUARIO);
            ModeloJuegoFacade.getInstance().mostrarMensaje(mensaje);
            return false;
        }

        List<Jugador> jugadoresSala = Sala.getInstance().getJugadoresSecundario();
        for (Jugador jugador : jugadoresSala) {
            if (jugador != null && jugador.getNickname().equals(usuarioNuevo.getNickname().trim())) {
                MensajeDTO mensaje = new MensajeDTO("Usuario invalido", "<html>Nombre de usuario ya seleccionado</html>", false, TipoMensajePantalla.VALIDACION_USUARIO);
                ModeloJuegoFacade.getInstance().mostrarMensaje(mensaje);
                return false;
            }
        }

        if (usuarioNuevo.getNickname().equals(ConfigLoader.getInstance().getUsuarioMatchmaker())) {
            MensajeDTO mensaje = new MensajeDTO("Usuario invalido", "<html>No es posible utilizar ese nombre de usuario</html>", false, TipoMensajePantalla.VALIDACION_USUARIO);
            ModeloJuegoFacade.getInstance().mostrarMensaje(mensaje);
            return false;
        }

        if (usuarioNuevo.getNickname().trim().length() > 12) {
            MensajeDTO mensaje = new MensajeDTO("Usuario invalido", "<html>El nombre de usuario debe tener un máximo de 10 carácteres</html>", false, TipoMensajePantalla.VALIDACION_USUARIO);
            ModeloJuegoFacade.getInstance().mostrarMensaje(mensaje);
            return false;
        }

        MensajeDTO mensaje = new MensajeDTO("Éxito", "<html>Se ha configurado exitosamente el usuario</html>", true, TipoMensajePantalla.VALIDACION_USUARIO);
        ModeloJuegoFacade.getInstance().mostrarMensaje(mensaje);
        return true;
    }

    /**
     * Valida que la configuración de la sala cumpla con los requisitos mínimos
     * y las restricciones del juego (dificultad, puntuación máxima, puntos por
     * jugada, límite de jugadores).
     *
     * @param configuracion DTO con los parámetros de la partida a validar.
     * @return true si la configuración es válida, false si no lo es, mostrando
     * un mensaje de error.
     */
    private boolean validarConfiguracionSala(ConfiguracionJuegoDTO configuracion) {
        if (configuracion == null || configuracion.getPuntajes().isEmpty() || configuracion.getDificultad() == null) {
            MensajeDTO mensaje = new MensajeDTO("Configuración invalida", "<html>Debe seleccionar una dificultad</html>", false, TipoMensajePantalla.VALIDACION_CONFIG_PARTIDA);
            ModeloJuegoFacade.getInstance().mostrarMensaje(mensaje);
            return false;
        }

        if (configuracion.getPuntajeMax() <= 0) {
            MensajeDTO mensaje = new MensajeDTO("Configuración invalida", "<html>La puntuación máxima no puede ser negativa o igual a cero</html>", false, TipoMensajePantalla.VALIDACION_CONFIG_PARTIDA);
            ModeloJuegoFacade.getInstance().mostrarMensaje(mensaje);
            return false;
        }

        Map<JugadasDisponibles, Integer> puntosPorJugada = configuracion.getPuntajes();
        if (puntosPorJugada.size() != this.numeroJugadasDisponibles) {
            MensajeDTO mensaje = new MensajeDTO("Configuración invalida", "<html>Se debe ingresar un valor para todas la jugadas disponibles</html>", false, TipoMensajePantalla.VALIDACION_CONFIG_PARTIDA);
            ModeloJuegoFacade.getInstance().mostrarMensaje(mensaje);
            return false;
        }

        for (Map.Entry<JugadasDisponibles, Integer> entry : puntosPorJugada.entrySet()) {
            if (entry.getValue() <= 0 || entry.getValue() > configuracion.getPuntajeMax()) {
                MensajeDTO mensaje = new MensajeDTO("Configuración invalida", "<html>La puntuación de la jugada no puede ser negativa, igual a cero o mayor a la puntuación máxima</html>", false, TipoMensajePantalla.VALIDACION_CONFIG_PARTIDA);
                ModeloJuegoFacade.getInstance().mostrarMensaje(mensaje);
                return false;
            }
        }

        if (configuracion.getLimiteJugadores() <= 1 || configuracion.getLimiteJugadores() > 4) {
            MensajeDTO mensaje = new MensajeDTO("Configuración invalida", "<html>El rango de jugadores es de 2 a 4 para la sala</html>", false, TipoMensajePantalla.VALIDACION_CONFIG_PARTIDA);
            ModeloJuegoFacade.getInstance().mostrarMensaje(mensaje);
            return false;
        }

        MensajeDTO mensaje = new MensajeDTO("Éxito", "<html>Se ha creado la sala</html>", true, TipoMensajePantalla.VALIDACION_CONFIG_PARTIDA);
        ModeloJuegoFacade.getInstance().mostrarMensaje(mensaje);
        return true;
    }
}
