package eventos.eventos_aplicacion;

import dtos.aplicacion.JugadorDTO;
import enums.TipoEvento;
import eventos.Evento;

/**
 *
 * @author norma
 */
public class EventoSalirSalaEspera extends Evento {
    
    JugadorDTO jugador;
    
    public EventoSalirSalaEspera(String userSender) {
        super(TipoEvento.SALIR_SALA_ESPERA, userSender);
    }
    
}
