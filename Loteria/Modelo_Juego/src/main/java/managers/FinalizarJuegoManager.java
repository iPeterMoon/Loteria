package managers;

import dtos.aplicacion.JugadorDTO;
import dtos.aplicacion.MensajeDTO;
import eventos.eventos_aplicacion.EventoFinJuego;
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

        if (vistaJuego != null) {
            vistaJuego.cerrarVentana();
        }

        // Si el juego terminó completamente, mostrar menú principal
        // Si es solo fin de ronda, mostrar sala de espera
        if (motivo.startsWith("JUEGO TERMINADO")) {
            Sala sala = Sala.getInstance();
            sala.setPartidaEnCurso(false);
            if (vistaConfiguracion != null) {
                vistaConfiguracion.mostrarMenuPrincipal();
            }
        } else {
            if (vistaConfiguracion != null) {
                vistaConfiguracion.mostrarSalaEspera();

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
        
        EventoFinJuego evento = new EventoFinJuego(Sala.getInstance().getJugadorPrincipal().getNickname(), nombreGanador);
        componentePeer.directMessage(evento, ConfigLoader.getInstance().getUsuarioMatchmaker());
    }
}

