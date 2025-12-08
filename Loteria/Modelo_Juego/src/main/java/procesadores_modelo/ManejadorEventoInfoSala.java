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
import modelo.Jugador;
import modelo.ModeloJuegoFacade;
import modelo.Sala;

/**
 *
 * @author norma
 */
public class ManejadorEventoInfoSala extends ManejadorEventos {

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

        if (evento.getSala() == null) {
            modeloJuegoFacade.cambiarTipoConfiguracion(TipoConfiguracion.CREAR_SALA);
        } else {
            SalaDTO salaDTO = evento.getSala();
            configurarSala(salaDTO);
        }

    }

    private void configurarSala(SalaDTO salaDTO) {
        Sala sala = Sala.getInstance();
        sala.setConfiguracion(SalaMapperModelo.toConfiguracionJuego(salaDTO));
        sala.setHost(salaDTO.getJugadorHost());
        List<JugadorDTO> jugadoresDTO = salaDTO.getJugadores();
        actualizarJugadoresSecundario(jugadoresDTO);
        actualizarVista();
    }

    private synchronized void actualizarJugadoresSecundario(List<JugadorDTO> jugadores) {
        Sala sala = Sala.getInstance();
        List<Jugador> jugadoresSecundarios = new ArrayList<>();
        for (JugadorDTO jugadorDTO : jugadores) {
            jugadoresSecundarios.add(JugadorMapperModelo.toJugador(jugadorDTO));
        }
        sala.setJugadoresSecundario(jugadoresSecundarios);
    }

    private void actualizarVista() {
        Sala sala = Sala.getInstance();

        modeloJuegoFacade.actualizarDatosSala(
                sala.getHost(),
                sala.getConfiguracion().getLimiteJugadores(),
                sala.getConfiguracion().getDificultad());
        List<JugadorDTO> jugadoresDTO = new ArrayList<>();
        for (Jugador jugador : sala.getJugadoresSecundario()) {
            if (sala.getJugadorPrincipal() != null) {
                boolean isPrincipal = jugador.getNickname().equals(sala.getJugadorPrincipal().getNickname());
                jugadoresDTO.add(JugadorMapperModelo.toDTO(jugador, isPrincipal));
            } else {
                jugadoresDTO.add(JugadorMapperModelo.toDTO(jugador, false));
            }
        }
        modeloJuegoFacade.actualizarJugadoresSala(jugadoresDTO);

        modeloJuegoFacade.cambiarTipoConfiguracion(TipoConfiguracion.UNIRSE_SALA);
    }
}
