package eventos.eventos_peers;

import dtos.peer.PeerInfo;
import enums.TipoEvento;
import eventos.Evento;

/**
 *
 * @author pedro
 */
public class EventoNuevoPeer extends Evento {

    private final PeerInfo peer;

    public EventoNuevoPeer(PeerInfo peer, String userSender) {
        super(TipoEvento.NUEVO_PEER, userSender);
        this.peer = peer;
    }

    public PeerInfo getPeer() {
        return peer;
    }
}
