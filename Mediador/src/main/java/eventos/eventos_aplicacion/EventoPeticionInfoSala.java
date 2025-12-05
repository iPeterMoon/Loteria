package eventos.eventos_aplicacion;

import enums.TipoEvento;
import eventos.Evento;

/**
 *
 * @author norma
 */
public class EventoPeticionInfoSala extends Evento {
    
    public EventoPeticionInfoSala(String userSender) {
        super(TipoEvento.PETICION_INFO_SALA, userSender);
    }
}
