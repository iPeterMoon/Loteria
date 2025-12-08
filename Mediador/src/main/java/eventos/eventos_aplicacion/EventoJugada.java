/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eventos.eventos_aplicacion;

import enums.TipoEvento;
import eventos.Evento;

/**
 *
 * @author Jp
 */
public class EventoJugada extends Evento {

    private String tipoJugada;

    public EventoJugada(String userSender, String tipoJugada) {
        super(TipoEvento.JUGADA, userSender);
        this.tipoJugada = tipoJugada;
    }

    public String getTipoJugada() {
        return tipoJugada;
    }

}
