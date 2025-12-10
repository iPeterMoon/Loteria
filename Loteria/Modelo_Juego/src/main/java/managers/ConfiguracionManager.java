/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package managers;

import dtos.aplicacion.ConfiguracionJuegoDTO;
import dtos.aplicacion.MensajeDTO;
import dtos.aplicacion.NuevoUsuarioDTO;
import enums.JugadasDisponibles;
import enums.TipoMensajePantalla;
import enums.TipoNivel;
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
 *
 * @author Alici
 */
public class ConfiguracionManager {
    
    private IPeer componentePeer;
    private IModeloVistaConfiguracion modeloVistaConfiguracion;
    private final int numeroJugadasDisponibles = 4;
    private NuevoUsuarioDTO usuarioNuevaSala;

    public void configurarUsuarioNuevaSala(NuevoUsuarioDTO usuarioNuevo) {
        if (validarUsuario(usuarioNuevo)) {
            usuarioNuevo.setEsHost(true);
            usuarioNuevaSala = usuarioNuevo;
        }
    }

    public void inicializar(IPeer peer, IModeloVistaConfiguracion modeloVistaConfiguracion) {
        if (this.componentePeer != null) {
            return;
        }
        this.componentePeer = peer;
        this.modeloVistaConfiguracion = modeloVistaConfiguracion;
    }

    public void crearNuevaSala(ConfiguracionJuegoDTO configuracionSala) {
        if (validarConfiguracionSala(configuracionSala)) {
            EventoCrearSala eventoSalaCreada = new EventoCrearSala(configuracionSala, usuarioNuevaSala, usuarioNuevaSala.getNickname());
            Jugador jugadorPrincipal = new Jugador();
            jugadorPrincipal.setNickname(usuarioNuevaSala.getNickname());
            jugadorPrincipal.setFotoPerfil(usuarioNuevaSala.getIdAvatarSeleccionado());

            //Validar que no exista una sala ya creada
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
            
            // enviar evento a matchmaker para que se lo envié a todos
            componentePeer.directMessage(eventoSalaCreada, ConfigLoader.getInstance().getUsuarioMatchmaker());
        }
    }

    public void obtenerSala() {
        EventoSolicitudSala solicitarSala = new EventoSolicitudSala(null);
        componentePeer.directMessage(solicitarSala, ConfigLoader.getInstance().getUsuarioMatchmaker());
    }

    public boolean configurarUsuarioUnirseSala(NuevoUsuarioDTO usuarioNuevo) {
        return validarUsuario(usuarioNuevo);
    }

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
            MensajeDTO mensaje = new MensajeDTO("Usuario invalido", "<html>El nombre de usuario debe tener un máximo de 20 carácteres</html>", false, TipoMensajePantalla.VALIDACION_USUARIO);
            ModeloJuegoFacade.getInstance().mostrarMensaje(mensaje);
            return false;
        }

        MensajeDTO mensaje = new MensajeDTO("Éxito", "<html>Se ha configurado exitosamente el usuario</html>", true, TipoMensajePantalla.VALIDACION_USUARIO);
        ModeloJuegoFacade.getInstance().mostrarMensaje(mensaje);
        return true;
    }

    private boolean validarConfiguracionSala(ConfiguracionJuegoDTO configuracion) {
        if (configuracion == null || configuracion.getPuntajes().isEmpty() || configuracion.getDificultad() == null) {
            MensajeDTO mensaje = new MensajeDTO("Configuración invalida", "<html>Debe seleccionar una dificultad</html>", false, TipoMensajePantalla.VALIDACION_CONFIG_PARTIDA);
            ModeloJuegoFacade.getInstance().mostrarMensaje(mensaje);
            return false;
        }

        if (configuracion.getPuntajeMax() <= 0) {
            MensajeDTO mensaje = new MensajeDTO("Configuración invalida", "<html>La puntuacion máxima no puede ser negativa o igual a cero</html>", false, TipoMensajePantalla.VALIDACION_CONFIG_PARTIDA);
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
