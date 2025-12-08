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
import util.ConfigLoader;

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
        EventoInfoSala eventoPeticionInfoSala = new EventoInfoSala(ConfigLoader.getInstance().getUsuarioMatchmaker(), obtenerSalaActual());
        matchmaker.broadcast(eventoPeticionInfoSala);
    }

    private SalaDTO obtenerSalaActual() {
        if (sala.getConfiguracion() == null && (sala.getJugadores() == null || sala.getJugadores().isEmpty()) && sala.getHost() == null) {
            return null;
        }
        ConfiguracionJuegoDTO configuracionJuegoDTO = new ConfiguracionJuegoDTO();
        configuracionJuegoDTO.setDificultad(sala.getConfiguracion().getDificultad());
        configuracionJuegoDTO.setLimiteJugadores(sala.getConfiguracion().getLimiteJugadores());
        configuracionJuegoDTO.setPuntajeMax(sala.getConfiguracion().getPuntajeMax());
        configuracionJuegoDTO.setPuntajes(sala.getConfiguracion().getPuntajes());
        SalaDTO salaActual = new SalaDTO(sala.getJugadores(), sala.getHost(), configuracionJuegoDTO);
        return salaActual;
    }

}
