package eventos.eventos_aplicacion;

import enums.TipoEvento;
import eventos.Evento;

/**
 * Evento que lleva la semilla con la que se barajea el mazo.
 * 
 * @author Peter
 */
public class EventoSemilla extends Evento{

    /**
     * Semilla con la que se barajea 
     */
    private final Long semilla;

    /**
     * Constructor para semilla
     * @param userSender Usuario que envía la semilla
     * @param semilla Semilla con la que se va a barajear las.
     */
    public EventoSemilla(String userSender, Long semilla) {
        super(TipoEvento.SEMILLA ,userSender);
        this.semilla = semilla;
    }

    /**
     * @return la semilla con la que se barajea el mazo
     */
    public Long getSemilla(){
        return semilla;
    }

}
