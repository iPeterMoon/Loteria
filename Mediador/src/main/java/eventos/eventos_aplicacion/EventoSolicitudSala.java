package eventos.eventos_aplicacion;

import enums.TipoEvento;
import eventos.Evento;

/**
 *
 * @author norma
 */
public class EventoSolicitudSala extends Evento {
    
    public EventoSolicitudSala(String userSender) {
        super(TipoEvento.SOLICITUD_SALA, userSender);
    }
}
