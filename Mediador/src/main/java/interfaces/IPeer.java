package interfaces;

import eventos.Evento;

/**
 *
 * @author pedro
 */
public interface IPeer {
    /**
     * Inicia todos los servicios del peer (Red, Descubrimiento, Hilos).
     */
    void start();

    /**
     * Detiene todos los servicios y notifica al servidor.
     */
    void stop();

    /**
     * Envía un evento a todos los demás peers en la partida.
     * @param evento El evento a transmitir.
     */
    void broadcastEvento(Evento evento);
    
    /**
     * Establece el nombre de usuario del peer.
     */
    void setUser(String user);

    /**
     * Establece un observer que escuchará los eventos que le lleguen al Peer
     * @param observer Observer que escuchará los eventos que le lleguen al Peer
     */
    void setObserver(IObserver observer);
    
    /**
     * Envía un evento a un usuario individual.
     * @param user El usuario al cual le mandará el evento.
     * @param evento El evento a transmitir.
     */
    void directMessage(Evento evento, String user);
}
