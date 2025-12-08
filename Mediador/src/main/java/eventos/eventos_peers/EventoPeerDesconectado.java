package eventos.eventos_peers;

import java.util.List;

import dtos.peer.PeerInfo;
import enums.TipoEvento;
import eventos.Evento;

/**
 * @author SDavidLedesma Evento enviado por el Servidor cuando un Peer ha sido
 * desconectado o ha abandonado la partida. Contiene la lista actualizada de
 * Peers.
 */
public class EventoPeerDesconectado extends Evento {

    private final PeerInfo peerDesconectado;

    public EventoPeerDesconectado(PeerInfo peerDesconectado) {
        super(TipoEvento.PEER_DESCONECTADO, peerDesconectado.getUser());
        this.peerDesconectado = peerDesconectado;
    }

    public PeerInfo getPeerDesconectado() {
        return peerDesconectado;
    }

    @Override
    public String toString() {
        return "PeerDesconectado{" + "peer=" + peerDesconectado.getUser() + '}';
    }
}
