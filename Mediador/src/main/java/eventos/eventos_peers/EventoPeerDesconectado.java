package eventos.eventos_peers;

import java.util.List;

import dtos.PeerInfo;
import enums.TipoEvento;
import eventos.Evento;

/**
 * @author SDavidLedesma Evento enviado por el Servidor cuando un Peer ha sido
 * desconectado o ha abandonado la partida. Contiene la lista actualizada de
 * Peers.
 */
public class EventoPeerDesconectado extends Evento {

    private final PeerInfo peerDesconectado;
    private final List<PeerInfo> peersActualizados;

    public EventoPeerDesconectado(PeerInfo peerDesconectado, List<PeerInfo> peersActualizados) {
        super(TipoEvento.PEER_DESCONECTADO, peerDesconectado.getUser());
        this.peerDesconectado = peerDesconectado;
        this.peersActualizados = peersActualizados;
    }

    public PeerInfo getPeerDesconectado() {
        return peerDesconectado;
    }

    public List<PeerInfo> getPeersActualizados() {
        return peersActualizados;
    }

    @Override
    public String toString() {
        return "PeerDesconectado{" + "peer=" + peerDesconectado.getUser() + ", totalPeers=" + peersActualizados.size()
                + '}';
    }
}
