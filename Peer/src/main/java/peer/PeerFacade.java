package peer;

import eventos.Evento;
import util.IObserver;
import interfaces.peer.IPeer;

/**
 * Clase Fachada para proporcionar un único punto de acceso simple para todas
 * las operaciones P2P.
 *
 * @author norma
 */
public class PeerFacade implements IPeer {

    /**
     * Instancia única de la clase Peer.
     */
    private final Peer peer;

    /**
     * Constructor que obtiene la instancia de Peer.
     */
    public PeerFacade() {
        this.peer = Peer.getInstance();
    }

    /**
     * Inicia los servicios de la lógica de Peer.
     */
    public void start() {
        this.peer.start();
    }

    /**
     * Envía un Evento a todos los Peers conectados (broadcast).
     *
     * @param evento Evento a transmitir.
     */
    public void broadcastEvento(Evento evento) {
        this.peer.broadcastEvento(evento);
    }

    /**
     * Envía un mensaje directo (Evento) a un usuario específico.
     *
     * @param evento Evento a transmitir.
     * @param user Usuario del Peer destinatario.
     */
    public void directMessage(Evento evento, String user) {
        this.peer.directMessage(evento, user);
    }

    /**
     * Establece el nombre de usuario del Peer.
     *
     * @param user
     */
    public void setUser(String user) {
        this.peer.setUser(user);
    }

    /**
     * Registra un observador para recibir notificaciones.
     *
     * @param observer Objeto que implementa IObserver.
     */
    public void setObserver(IObserver observer) {
        this.peer.setObserver(observer);
    }

    /**
     * Lógica de cierre y desconexión controlada del Peer
     */
    public void abandonar() {
        this.peer.abandonar(); 
    }

    @Override
    public void stop() {
        this.peer.stop();
    }

}
