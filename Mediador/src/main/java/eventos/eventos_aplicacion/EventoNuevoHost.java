package eventos.eventos_aplicacion;

import enums.TipoEvento;

/**
 *
 * @author petermoon
 */
public class EventoNuevoHost extends eventos.Evento{
    
    private String nuevoHostUser;
    
    public EventoNuevoHost(String nuevoHostUser, String userSender) {
        super(TipoEvento.NUEVO_HOST, userSender);
        this.nuevoHostUser = nuevoHostUser;
    }

    public String getNuevoHostUser() {
        return nuevoHostUser;
    }
    
}
