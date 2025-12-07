package procesadores;

import dtos.aplicacion.ConfiguracionJuegoDTO;
import dtos.aplicacion.JugadorDTO;
import dtos.aplicacion.SalaDTO;
import enums.TipoEvento;
import eventos.Evento;
import eventos.eventos_aplicacion.EventoInfoSala;
import eventos.eventos_aplicacion.EventoSolicitudSala;
import implementaciones.Matchmaker;
import implementaciones.Sala;

/**
 *
 * @author norma
 */
public class ManejadorSolicitudSala extends ManejadorEventos {

    Matchmaker matchmaker = Matchmaker.getInstance();
    Sala sala = Sala.getInstance();
    
    @Override
    public void procesar(Evento evento) {
        if (evento.getTipoEvento().equals(TipoEvento.SOLICITUD_SALA)) {
            manejarSolicitudSala((EventoSolicitudSala) evento);
        } else if (next != null) {
            next.procesar(evento);
        }
    }
    
    private void manejarSolicitudSala(EventoSolicitudSala evento) {
        JugadorDTO nuevoJugador = new JugadorDTO();      
        EventoInfoSala eventoPeticionInfoSala = new EventoInfoSala("MATCHMAKER", obtenerSalaActual());
        matchmaker.directMessage(eventoPeticionInfoSala, evento.getUserSender()); 
    }
    
     private SalaDTO obtenerSalaActual(){
        ConfiguracionJuegoDTO configuracionJuego = new ConfiguracionJuegoDTO(sala.getConfiguracion().getLimiteJugadores()
                , sala.getConfiguracion().getPuntajeMax(), sala.getConfiguracion().getDificultad(), sala.getConfiguracion().getPuntajes());
        SalaDTO salaActual = new SalaDTO(sala.getJugadores(), sala.getHost(), configuracionJuego);
        return salaActual;
    }
    

}
