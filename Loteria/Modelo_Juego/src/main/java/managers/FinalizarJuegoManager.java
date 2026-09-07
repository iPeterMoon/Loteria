package managers;

import dtos.aplicacion.JugadorDTO;
import dtos.aplicacion.MensajeDTO;
import dtos.peer.PeerInfo;
import eventos.eventos_aplicacion.EventoFinJuego;
import eventos.eventos_aplicacion.EventoPeerDesconectado;
import interfaces.aplicacion.IModeloVistaConfiguracion;
import interfaces.aplicacion.IModeloVistaJuego;
import interfaces.peer.IPeer;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;
import mappers.JugadorMapperModelo;
import modelo.Jugador;
import modelo.ModeloJuegoFacade;
import modelo.Sala;
import util.ConfigLoader;

/**
 *
 * @author petermoon
 */
public class FinalizarJuegoManager {
    private IPeer componentePeer;
    private IModeloVistaJuego vistaJuego;
    private IModeloVistaConfiguracion vistaConfiguracion;
    private CantadorManager cantadorManager;
    /**
     * Inicializa el Manager para finalizar la partida o juego
     * @param peer Peer del jugador que está finalizando la partida
     * @param vistaJuego Interfaz para la presentación del juego.
     * @param vistaConfiguracion Interfaz para la presentación de la configuración
     * @param cantadorManager Manager del cantador de loteria
     */
    public void inicializar(IPeer peer, IModeloVistaJuego vistaJuego, 
            IModeloVistaConfiguracion vistaConfiguracion, CantadorManager cantadorManager) {
        if (this.componentePeer != null || this.vistaJuego != null
                || this.vistaConfiguracion != null || this.cantadorManager != null) {
            return;
        }
        this.componentePeer = peer;
        this.vistaJuego = vistaJuego;
        this.vistaConfiguracion = vistaConfiguracion;
        this.cantadorManager = cantadorManager;
    }
    
    public void finalizarRonda(String motivo) {
        System.out.println("Se acabo la ronda: " + motivo);

        if (cantadorManager != null) {
            cantadorManager.detenerCantador();
        }

        SwingUtilities.invokeLater(() -> {
            if (vistaJuego != null) {
                vistaJuego.cerrarVentana();
            }
        });

        // Si el juego terminó completamente, mostrar menú principal
        // Si es solo fin de ronda, mostrar sala de espera
        if (motivo.startsWith("JUEGO TERMINADO")) {
            Sala sala = Sala.getInstance();
            sala.setPartidaEnCurso(false);
            if (vistaConfiguracion != null) {
                SwingUtilities.invokeLater(() -> vistaConfiguracion.mostrarMenuPrincipal());
            }
        } else {
            if (vistaConfiguracion != null) {
                SwingUtilities.invokeLater(() -> vistaConfiguracion.mostrarSalaEspera());

                Sala sala = Sala.getInstance();
                sala.setPartidaEnCurso(false);
                List<JugadorDTO> todosLosJugadores = new ArrayList<>();

                if (sala.getJugadorPrincipal() != null) {
                    todosLosJugadores.add(JugadorMapperModelo.toDTO(sala.getJugadorPrincipal(), true));
                }

                for (Jugador j : sala.getJugadoresSecundario()) {
                    todosLosJugadores.add(JugadorMapperModelo.toDTO(j, false));
                }

                ModeloJuegoFacade.getInstance().actualizarJugadoresSala(todosLosJugadores);

                verificarSiAlguienGanoElJuego();
            }
        }
    }

    /**
     * Verifica si algún jugador ha alcanzado o superado el puntaje máximo. Si
     * es así, inicia el proceso de cierre del juego.
     *
     * @return true si el juego terminó, false si continúa.
     */
    public boolean verificarSiAlguienGanoElJuego() {
        Sala sala = Sala.getInstance();
        int puntajeMeta = sala.getConfiguracion().getPuntajeMax();

        Jugador ganador = null;
        int maxPuntajeActual = -1;

        if (sala.getJugadorPrincipal() != null) {
            if (sala.getJugadorPrincipal().getPuntos() > maxPuntajeActual) {
                maxPuntajeActual = sala.getJugadorPrincipal().getPuntos();
                ganador = sala.getJugadorPrincipal();
            }
        }

        for (Jugador j : sala.getJugadoresSecundario()) {
            if (j.getPuntos() > maxPuntajeActual) {
                maxPuntajeActual = j.getPuntos();
                ganador = j;
            }
        }

        if (ganador != null && maxPuntajeActual >= puntajeMeta) {
            cerrarJuegoDefinitivo(ganador.getNickname());
            return true;
        }

        return false;
    }
    
    public void cerrarJuegoDefinitivo(String nombreGanador) {
        SwingUtilities.invokeLater( () -> {
            MensajeDTO mensaje = new MensajeDTO(
                    "¡FIN DEL JUEGO!",
                    "<html><center>El jugador " + nombreGanador + " ha ganado la partida.<br>La sala se cerrará.</center></html>",
                    true,
                    enums.TipoMensajePantalla.INFORMACION
            );
            ModeloJuegoFacade.getInstance().mostrarMensaje(mensaje);
        });

        String userSender = Sala.getInstance().getNicknameJugadorPrincipal();
        if (userSender == null) {
            return;
        }

        detenerJuegoLocal();

        EventoFinJuego evento = new EventoFinJuego(userSender, nombreGanador);
        componentePeer.directMessage(evento, ConfigLoader.getInstance().getUsuarioMatchmaker());
    }

    /**
     * Declara a este jugador ganador porque el resto de los oponentes
     * activos abandonó la ronda, pero SIN cerrar/eliminar la sala: se usa
     * cuando todavía queda alguien más presente en la sala (p. ej. un
     * jugador que abandonó la partida y se reincorporó como espectador).
     * A diferencia de {@link #cerrarJuegoDefinitivo}, no se notifica al
     * Matchmaker ni se destruye la sala — simplemente se regresa a la
     * pantalla de la sala de espera para que quien quede pueda seguir
     * usándola.
     *
     * @param nombreGanador Nickname del jugador que gana por abandono.
     */
    public void ganarPorAbandono(String nombreGanador) {
        SwingUtilities.invokeLater(() -> {
            // "exitoso" = false a propósito: PanelSalaEspera.actualizarMensaje
            // reacciona a un mensaje "exitoso" que no sea de "FIN DEL JUEGO"
            // volviendo a navegar a la pantalla de sala de espera, lo cual
            // pisa (oculta) el botón "Iniciar Partida" que ya dejamos bien
            // puesto más abajo. Aquí solo queremos mostrar el aviso, sin ese
            // efecto secundario de renavegación.
            MensajeDTO mensaje = new MensajeDTO(
                    "¡Ganaste la ronda!",
                    "<html><center>Los demás jugadores abandonaron la partida.<br>Ganaste por abandono.</center></html>",
                    false,
                    enums.TipoMensajePantalla.INFORMACION
            );
            ModeloJuegoFacade.getInstance().mostrarMensaje(mensaje);
        });

        detenerJuegoEnCurso();

        SwingUtilities.invokeLater(() -> {
            if (vistaConfiguracion != null) {
                vistaConfiguracion.mostrarSalaEspera();
            }
        });

        Sala sala = Sala.getInstance();

        // La vista de la sala de espera guarda su propia copia del host
        // (SalaSubject.hostUser), separada de Sala.host, y solo se
        // resincroniza cuando llega un ManejadorEventoNuevoHost o similar.
        // Si hubo alguna reasignación de host antes de este punto, esa copia
        // puede haber quedado desactualizada, y el botón "Iniciar Partida"
        // solo se muestra si coincide con el host actual — forzamos la
        // resincronización aquí para no depender de ese historial.
        if (sala.getConfiguracion() != null) {
            ModeloJuegoFacade.getInstance().actualizarDatosSala(
                    sala.getHost(),
                    sala.getConfiguracion().getLimiteJugadores(),
                    sala.getConfiguracion().getDificultad());
        }

        List<JugadorDTO> todosLosJugadores = new ArrayList<>();
        if (sala.getJugadorPrincipal() != null) {
            todosLosJugadores.add(JugadorMapperModelo.toDTO(sala.getJugadorPrincipal(), true));
        }
        for (Jugador j : sala.getJugadoresSecundario()) {
            todosLosJugadores.add(JugadorMapperModelo.toDTO(j, false));
        }
        ModeloJuegoFacade.getInstance().actualizarJugadoresSala(todosLosJugadores);
    }

    /**
     * Abandona una partida en curso por decisión propia del jugador. Notifica
     * a los demás peers (que procesarán esto igual que una desconexión real,
     * vía {@code ManejadorEventoDesconexion}), lo cual también llega al
     * Matchmaker y lo da de baja de la sala del lado del servidor (con
     * reasignación de host si aplicaba). El Matchmaker solo soporta una sala
     * a la vez por diseño, así que este cliente NO debe resetear la sala ni
     * navegar a crear/unirse a otra — esa sala sigue existiendo y en curso
     * para el resto. Solo dejamos de ser "jugador principal" (para que los
     * eventos de la siguiente ronda se ignoren localmente en vez de
     * procesarse a medias) y nos quedamos viendo la sala de espera como
     * espectadores hasta que la partida realmente termine, momento en el
     * que el Matchmaker sí resetea todo para todos vía
     * {@code ManejadorEventoInfoSala}.
     */
    public void abandonarPartida() {
        String nickname = Sala.getInstance().getNicknameJugadorPrincipal();
        if (nickname == null) {
            return;
        }

        if (componentePeer != null) {
            PeerInfo peerInfo = new PeerInfo(nickname, null, 0);
            componentePeer.broadcastEvento(new EventoPeerDesconectado(peerInfo));
        }

        detenerJuegoEnCurso();

        ModeloJuegoFacade.getInstance().setJugadorPrincipal(null);

        SwingUtilities.invokeLater(() -> {
            if (vistaConfiguracion != null) {
                vistaConfiguracion.mostrarSalaEspera();
            }
        });
    }

    /**
     * Detiene el cantador, cierra la ventana de partida y regresa a este
     * jugador a la sala de espera. Usado por {@link #cerrarJuegoDefinitivo},
     * donde esta pantalla es solo transicional: el Matchmaker enseguida
     * elimina la sala para todos y el reset completo (incluyendo la
     * navegación al menú principal) llega por separado vía
     * {@code ManejadorEventoInfoSala}.
     */
    private void detenerJuegoLocal() {
        detenerJuegoEnCurso();

        SwingUtilities.invokeLater(() -> {
            if (vistaConfiguracion != null) {
                vistaConfiguracion.mostrarSalaEspera();
            }
        });
    }

    /**
     * Detiene el cantador, marca la partida como no en curso y cierra la
     * ventana de partida si estaba abierta, sin navegar a ninguna otra
     * pantalla. Usado también cuando la sala se resetea completamente (p. ej.
     * el jugador local nunca detectó localmente el fin del juego) para que
     * no quede una ventana de partida obsoleta abierta.
     */
    public void detenerJuegoEnCurso() {
        if (cantadorManager != null) {
            cantadorManager.detenerCantador();
        }

        Sala.getInstance().setPartidaEnCurso(false);

        SwingUtilities.invokeLater(() -> {
            if (vistaJuego != null) {
                vistaJuego.cerrarVentana();
            }
        });
    }
}

