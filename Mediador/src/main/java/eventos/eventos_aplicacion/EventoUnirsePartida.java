/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eventos.eventos_aplicacion;

import enums.TipoEvento;
import eventos.Evento;

/**
 *
 * @author norma
 */
public class EventoUnirsePartida extends Evento{
    
    private String nickname;
    private int avatar;
    
    public EventoUnirsePartida(String userSender, String nickname, int avatar) {
        super(TipoEvento.UNIRSE_SALA, userSender);
        this.nickname = nickname;
        this.avatar = avatar;
    }
}
