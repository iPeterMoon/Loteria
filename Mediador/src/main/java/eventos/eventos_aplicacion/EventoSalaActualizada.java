package eventos.eventos_aplicacion;

import dtos.aplicacion.JugadorDTO;
import enums.TipoEvento;
import eventos.Evento;
import java.util.List;

/**
 *
 * @author norma
 */
public class EventoSalaActualizada extends Evento {
    
    private List<JugadorDTO> jugadores;
    
    public EventoSalaActualizada(String userSender, List<JugadorDTO> jugadores) {
        super(TipoEvento.SALA_ACTUALIZADA, userSender);
        this.jugadores = jugadores;
    }

    public List<JugadorDTO> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<JugadorDTO> jugadores) {
        this.jugadores = jugadores;
    }

    @Override
    public String toString() {
        return "EventoSalaActualizada{" + "jugadores=" + jugadores + '}';
    }
    
}
