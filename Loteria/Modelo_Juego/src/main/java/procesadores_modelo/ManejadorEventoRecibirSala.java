package procesadores_modelo;

import dtos.aplicacion.JugadorDTO;
import dtos.aplicacion.SalaDTO;
import enums.TipoConfiguracion;
import enums.TipoEvento;
import eventos.Evento;
import eventos.eventos_aplicacion.EventoInfoSala;
import java.util.ArrayList;
import java.util.List;
import mappers.JugadorMapperModelo;
import mappers.SalaMapperModelo;
import modelo.ConfiguracionJuego;
import modelo.Jugador;
import modelo.ModeloJuegoFacade;
import modelo.Sala;

/**
 *
 * @author norma
 */
public class ManejadorEventoRecibirSala extends ManejadorEventos {

    private ModeloJuegoFacade modeloJuegoFacade = ModeloJuegoFacade.getInstance();

    @Override
    public void procesar(Evento evento) {
        if (evento.getTipoEvento().equals(TipoEvento.INFO_SALA)) {
            manejarPeticionInfoSala((EventoInfoSala) evento);
        } else if (next != null) {
            next.procesar(evento);
        }
    }

    private void manejarPeticionInfoSala(EventoInfoSala evento) {
        Sala sala = Sala.getInstance();
        if (!sala.salaCreada()) {
            if (evento.getSala() == null) {
                modeloJuegoFacade.cambiarTipoConfiguracion(TipoConfiguracion.CREAR_SALA);
                return;
            } else {
                configurarSala(evento.getSala());
                modeloJuegoFacade.cambiarTipoConfiguracion(TipoConfiguracion.UNIRSE_SALA);

            }
        }
        List<JugadorDTO> jugadoresDTO = evento.getSala().getJugadores();
        actualizarJugadoresSecundario(jugadoresDTO);

        ModeloJuegoFacade.getInstance().actualizarDatosSala(evento.getSala().getConfiguracion().getLimiteJugadores(),
                evento.getSala().getConfiguracion().getDificultad());
        ModeloJuegoFacade.getInstance().actualizarJugadoresSala(jugadoresDTO);
    }

    private void configurarSala(SalaDTO salaDTO) {
        Sala sala = Sala.getInstance();
        sala.setConfiguracion(SalaMapperModelo.toConfiguracionJuego(salaDTO));
        sala.setHost(salaDTO.getJugadorHost());
    }

    private void actualizarJugadoresSecundario(List<JugadorDTO> jugadores) {
        Sala sala = Sala.getInstance();
        List<Jugador> jugadoresSecundarios = new ArrayList<>();
        for (JugadorDTO jugadorDTO : jugadores) {
            if (jugadorDTO.getNickname().equals(sala.getJugadorPrincipal().getNickname())) {
                jugadoresSecundarios.add(JugadorMapperModelo.toJugador(jugadorDTO));
            }
        }
        sala.setJugadoresSecundario(jugadoresSecundarios);
    }
}
