package interfaces;

import dtos.PeerInfo;
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
     * Obtiene la información de este peer (ID, IP, Puerto).
     * @return La información del peer local.
     */
    PeerInfo getMyInfo();
}
