package procesadores;

import dtos.aplicacion.ConfiguracionJuegoDTO;
import dtos.aplicacion.JugadorDTO;
import dtos.aplicacion.NuevoUsuarioDTO;
import dtos.aplicacion.SalaDTO;
import enums.TipoEvento;
import eventos.Evento;
import eventos.eventos_aplicacion.EventoCrearSala;
import eventos.eventos_aplicacion.EventoNuevaSala;
import implementaciones.ConfiguracionJuego;
import implementaciones.Matchmaker;
import implementaciones.Sala;
import java.util.List;
import mappers.ConfiguracionMapperMatchmaker;
import mappers.JugadorMapperMatchmaker;
import mappers.SalaMapperMatchmaker;
import util.ConfigLoader;

/**
 *
 * @author norma
 */
public class ManejadorSalaCreada extends ManejadorEventos {

    @Override
    public void procesar(Evento evento) {
        if (evento.getTipoEvento().equals(TipoEvento.SALA_CREADA)) {
            EventoCrearSala eventoSalaCreada = (EventoCrearSala) evento;

            ConfiguracionJuegoDTO configuracionDTO = eventoSalaCreada.getConfiguracionSala();
            NuevoUsuarioDTO hostDTO = eventoSalaCreada.getUsuarioHost();

            ConfiguracionJuego configuracion = ConfiguracionMapperMatchmaker.toConfiguracionJuego(configuracionDTO);
            JugadorDTO host = JugadorMapperMatchmaker.toJugadorDTO(hostDTO);

            Sala.getInstance().configurarNuevaSala(configuracion, host);
            SalaDTO salaDTO = SalaMapperMatchmaker.toSalaDTO(configuracionDTO);

            EventoNuevaSala eventoNuevaSala = new EventoNuevaSala(ConfigLoader.getInstance().getUsuarioMatchmaker(), salaDTO);

            Matchmaker.getInstance().broadcast(eventoNuevaSala);
        }

    }

}
