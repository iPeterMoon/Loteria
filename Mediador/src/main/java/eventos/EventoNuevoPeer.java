package eventos;

import dtos.PeerInfo;
import enums.TipoEvento;

/**
 *
 * @author pedro
 */
public class EventoNuevoPeer extends Evento {

    private PeerInfo peer;

    public EventoNuevoPeer(PeerInfo peer, String userSender) {
        super(TipoEvento.NUEVO_PEER, userSender);
        this.peer = peer;
    }

    public PeerInfo getPeer() {
        return peer;
    }
}
