package eventos.eventos_aplicacion;

import enums.TipoEvento;
import eventos.Evento;

public class EventoIniciarPartida extends Evento {

    public EventoIniciarPartida(String userSender) {
        super(TipoEvento.INICIAR_PARTIDA, userSender);
    }

}
