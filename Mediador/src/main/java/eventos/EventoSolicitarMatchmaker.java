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
public class EventoSolicitarMatchmaker extends Evento {

    private PeerInfo peer;

    public EventoSolicitarMatchmaker(PeerInfo peer, String userSender) {
        super(TipoEvento.SOLICITAR_MATCHMAKER, userSender);
        this.peer = peer;
    }
}
