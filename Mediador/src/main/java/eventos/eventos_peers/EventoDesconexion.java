package eventos.eventos_peers;

import dtos.peer.PeerInfo;
import enums.TipoEvento;
import eventos.Evento;

public class EventoDesconexion extends Evento {
    
    private final PeerInfo peerCaido; // El peer que se desconectó

    public EventoDesconexion(PeerInfo peerCaido) {
        // Llamamos al constructor del padre: (Tipo, Remitente)
        super(TipoEvento.PEER_DESCONECTADO, "DISCOVERY"); 
        this.peerCaido = peerCaido;
    }

    public PeerInfo getPeerCaido() {
        return peerCaido;
    }
    
    @Override
    public String toString() {
        return "EventoDesconexion{peerCaido=" + peerCaido.getIp() + "}";
    }
}