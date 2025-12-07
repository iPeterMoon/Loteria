package eventos.eventos_aplicacion;

import dtos.SalaDTO;
import enums.TipoEvento;
import eventos.Evento;

/**
 *
 * @author norma
 */
public class EventoPeticionInfoSala extends Evento {
    
    private SalaDTO sala;
    
    public EventoPeticionInfoSala(String userSender, SalaDTO sala) {
        super(TipoEvento.PETICION_INFO_SALA, userSender);
        this.sala = sala;
    }

    public SalaDTO getSala() {
        return sala;
    }

    public void setSala(SalaDTO sala) {
        this.sala = sala;
    }
   
}
