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
public class EventoFinRonda extends Evento {

    private String motivo;

    public EventoFinRonda(String userSender, String motivo) {
        super(TipoEvento.FIN_RONDA, userSender);
        this.motivo = motivo;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

}
