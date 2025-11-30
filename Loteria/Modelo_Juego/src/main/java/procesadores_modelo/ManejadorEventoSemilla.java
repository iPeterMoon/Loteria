package procesadores_modelo;

import enums.TipoEvento;
import eventos.Evento;
import eventos.eventos_aplicacion.EventoSemilla;
import modelo.Cantador;

/**
 * Manejador de un evento de Semilla, se encarga de preparar el mazo con la
 * semilla que recibe
 * 
 * @author Peter
 */
public class ManejadorEventoSemilla extends ManejadorEventos {


    @Override
    public void procesar(Evento evento) {
        if (evento.getTipoEvento().equals(TipoEvento.SEMILLA)) {
            EventoSemilla eventoSemilla = (EventoSemilla) evento;
            Cantador.getInstance().prepararMazo(eventoSemilla.getSemilla());
        } else if (next != null) {
            next.procesar(evento);
        }
    }

}
