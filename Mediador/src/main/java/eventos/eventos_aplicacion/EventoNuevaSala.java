package eventos.eventos_aplicacion;

import dtos.aplicacion.SalaDTO;
import enums.TipoEvento;
import eventos.Evento;

/**
 *
 * @author Alici
 */
public class EventoNuevaSala extends Evento {

    private SalaDTO nuevaSala;

    public EventoNuevaSala(String userSender) {
        super(TipoEvento.NUEVA_SALA, userSender);
    }

    public EventoNuevaSala(String userSender, SalaDTO nuevaSala) {
        super(TipoEvento.NUEVA_SALA, userSender);
        this.nuevaSala = nuevaSala;
    }

    public SalaDTO getNuevaSala() {
        return nuevaSala;
    }

    public void setNuevaSala(SalaDTO nuevaSala) {
        this.nuevaSala = nuevaSala;
    }

}
