package eventos.eventos_aplicacion;

import dtos.SalaDTO;
import enums.TipoEvento;
import eventos.Evento;

/**
 *
 * @author norma
 */
public class EventoInfoSala extends Evento {
    
    private SalaDTO sala;
    
    public EventoInfoSala(String userSender, SalaDTO sala) {
        super(TipoEvento.INFO_SALA, userSender);
        this.sala = sala;
    }

    public SalaDTO getSala() {
        return sala;
    }

    public void setSala(SalaDTO sala) {
        this.sala = sala;
    }
   
}
