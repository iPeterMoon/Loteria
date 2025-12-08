package eventos.eventos_peers;

import dtos.peer.PeerInfo;
import enums.TipoEvento;
import eventos.Evento;

public class EventoHeartbeat extends Evento{

    private final PeerInfo info;

    public EventoHeartbeat(PeerInfo info, String userSender){
        super(TipoEvento.HEARTBEAT, userSender);
        this.info = info;
    }

    public PeerInfo getInfo(){
        return info;
    } 
}