package procesadores;

import dtos.ConfiguracionJuegoDTO;
import dtos.JugadorDTO;
import dtos.SalaDTO;
import enums.TipoEvento;
import enums.TipoNivel;
import eventos.Evento;
import eventos.eventos_aplicacion.EventoInfoSala;
import eventos.eventos_aplicacion.EventoSalaActualizada;
import eventos.eventos_aplicacion.EventoUnirseSala;
import implementaciones.Matchmaker;
import implementaciones.Sala;

/**
 *
 * @author norma
 */
public class ManejadorUnirseSala extends ManejadorEventos {

    Matchmaker matchmaker = Matchmaker.getInstance();
    Sala sala = Sala.getInstance();
    
    @Override
    public void procesar(Evento evento) {
        if (evento.getTipoEvento().equals(TipoEvento.UNIRSE_SALA)) {
            manejarUnirseSala((EventoUnirseSala) evento);
        } else if (next != null) {
            next.procesar(evento);
        }
    }

    private void manejarUnirseSala(EventoUnirseSala evento) {
        
        JugadorDTO nuevoJugador = new JugadorDTO();
        nuevoJugador.setNickname(evento.getNickname());
        nuevoJugador.setFotoPerfil(evento.getAvatar());
        
        sala.agregarJugador(nuevoJugador);
                
        EventoInfoSala eventoPeticionInfoSala = new EventoInfoSala("MATCHMAKER", obtenerSalaActual());
        matchmaker.directMessage(eventoPeticionInfoSala, evento.getUserSender());
                
        EventoSalaActualizada eventoSalaActualizada = new EventoSalaActualizada("MATCHMAKER",sala.getJugadores());
        matchmaker.broadcast(eventoSalaActualizada);
        
    }
    
    private SalaDTO obtenerSalaActual(){
        ConfiguracionJuegoDTO configuracionJuego = new ConfiguracionJuegoDTO(sala.getConfiguracion().getLimiteJugadores()
                , sala.getConfiguracion().getPuntajeMax(), sala.getConfiguracion().getDificultad(), sala.getConfiguracion().getPuntajes());
        SalaDTO salaActual = new SalaDTO(sala.getJugadores(), sala.getHost(), configuracionJuego);
        return salaActual;
    }
}

