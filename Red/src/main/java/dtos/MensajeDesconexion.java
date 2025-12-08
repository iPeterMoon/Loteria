package dtos;

import enums.TipoEvento;
import eventos.Evento;

/**
 * @author SDavidLedesma
 * Contenido del evento de salida voluntaria. Este objeto se serializa a JSON
 * y se envía al Servidor.
 */
public class MensajeDesconexion extends Evento {
    
    // Este campo contiene la PeerInfo completa del que se desconecta.
    private final PeerInfo peer; 
    
    /**
     * Constructor para la desconexión voluntaria.
     * @param peer La información completa del Peer que está saliendo.
     */
    public MensajeDesconexion(PeerInfo peer) {
        // Llama al constructor de la clase base 'Evento'
        // El userSender se extrae directamente del DTO PeerInfo.
        super(TipoEvento.PEER_DESCONECTADO, peer.getUser()); 
        this.peer = peer;
    }

    public PeerInfo getPeer() {
        return peer;
    }
}