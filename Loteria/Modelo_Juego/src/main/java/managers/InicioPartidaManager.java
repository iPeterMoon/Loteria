package managers;

import interfaces.IPeer;
import modelo.Cantador;
import modelo.Sala;

import java.util.Random;
import java.util.Stack;

import eventos.eventos_aplicacion.EventoSemilla;
import interfaces.IModeloVistaJuego;

/**
 * Manejador para el inicio de una nueva partida.
 * 
 * @author Peter
 */
public class InicioPartidaManager {

    private IPeer componentePeer;
    private IModeloVistaJuego modeloVista;

    /**
     * Inicializa el Manager para iniciar la partida
     * @param peer Peer del jugador que está iniciando la partida
     * @param modeloVista Interfaz para la presentación del juego.
     */
    public void inicializar(IPeer peer, IModeloVistaJuego modeloVista) {
        if (this.componentePeer != null || this.modeloVista != null) {
            return;
        }
        this.componentePeer = peer;
        this.modeloVista = modeloVista;
    }

    /**
     * Metodo orquestador para iniciar la partida, hace todo lo necesario
     * para barajear el mazo y mostrar la pantalla de la partida.
     */
    public void iniciarPartida() {
        barajearMazo();
    }

    /**
     * Metodo que barajea el mazo generando una semilla y llamando al metodo de preparar
     * mazo del Cantador
     */
    private void barajearMazo() {
        Random random = new Random();
        Long semilla = random.nextLong();
        Cantador.getInstance().prepararMazo(semilla);
        generarEventoSemilla(semilla);
    }
    
    /**
     * Genera un evento con la semilla para pasarselo a los demás jugadores y que
     * ellos barajeen su mazo con la misma semilla
     * @param semilla Semilla con la que se barajeará el mazo
     */
    private void generarEventoSemilla(Long semilla){
        String userSender = Sala.getInstance().getJugadorPrincipal().getNickname();
        EventoSemilla eventoSeed = new EventoSemilla(userSender, semilla);
        componentePeer.broadcastEvento(eventoSeed);
    }

}
