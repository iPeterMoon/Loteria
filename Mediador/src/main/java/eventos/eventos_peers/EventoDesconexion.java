package eventos.eventos_peers;

import dtos.PeerInfo;
import enums.TipoEvento;

public class EventoDesconexion extends Evento {
    
    private static final long serialVersionUID = 200L; // ID único para serialización
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