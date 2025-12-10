package procesadores;

import enums.TipoEvento;
import eventos.Evento;
import eventos.eventos_aplicacion.EventoInfoSala;
import implementaciones.Matchmaker;
import implementaciones.Sala;
import util.ConfigLoader;

/**
 *
 * @author petermoon
 */
public class ManejadorFinJuego extends ManejadorEventos {

    private final Sala sala = Sala.getInstance();
    
    @Override
    public void procesar(Evento evento) {
        if (evento.getTipoEvento().equals(TipoEvento.FIN_JUEGO)) {
            EventoInfoSala eventoInfoSala = new EventoInfoSala(ConfigLoader.getInstance().getUsuarioMatchmaker(), null);
            Matchmaker.getInstance().broadcast(eventoInfoSala);
            
            eliminarSala();
            
        } else if (next != null){
            next.procesar(evento);
        }
    }
    
    private void eliminarSala() {
        sala.setHost(null);
        sala.setConfiguracion(null);
        sala.limpiarJugadores();
    }
    
}
