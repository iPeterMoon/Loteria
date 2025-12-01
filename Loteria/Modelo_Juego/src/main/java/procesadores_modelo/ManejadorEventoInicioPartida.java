package procesadores_modelo;

import enums.TipoEvento;
import eventos.Evento;
import modelo.ModeloJuegoFacade;

public class ManejadorEventoInicioPartida extends ManejadorEventos{

    @Override
    public void procesar(Evento evento) {
        if (evento.getTipoEvento().equals(TipoEvento.INICIAR_PARTIDA)) {
            ModeloJuegoFacade.getInstance().mostrarFramePartida();
        } else if (next != null) {
            next.procesar(evento);
        }    
    }

}
