package procesadorEventos;

import dtos.PeerInfo;
import eventos.EventoNuevoPeer;
import peer.PeersConectados;

/**
 *
 * @author pedro
 */
public class ProcesadorConexiones {
    
    public static void registrarPeer(EventoNuevoPeer evento){
        PeerInfo info = evento.getPeer();
        PeersConectados peers = PeersConectados.getInstance();
        peers.registrarPeer(info);
    }
}
