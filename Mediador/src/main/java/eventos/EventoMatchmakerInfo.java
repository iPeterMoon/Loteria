/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eventos;

import dtos.PeerInfo;
import enums.TipoEvento;

/**
 *
 * @author Jp
 */
public class EventoMatchmakerInfo extends Evento {

    private final PeerInfo peer;

    public EventoMatchmakerInfo(PeerInfo peer, String userSender) {
        super(TipoEvento.MATCHMAKER_INFO, userSender);
        this.peer = peer;
    }

}
