package procesadores;

import java.util.List;

import dtos.aplicacion.ConfiguracionJuegoDTO;
import dtos.aplicacion.JugadorDTO;
import dtos.aplicacion.NuevoUsuarioDTO;
import dtos.aplicacion.SalaDTO;
import enums.TipoEvento;
import enums.TipoNivel;
import eventos.Evento;
import eventos.eventos_aplicacion.EventoInfoSala;
import eventos.eventos_aplicacion.EventoSalaActualizada;
import eventos.eventos_aplicacion.EventoUnirseSala;
import implementaciones.Matchmaker;
import implementaciones.Sala;
import util.ConfigLoader;

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

        int limiteJugadores = sala.getConfiguracion().getLimiteJugadores();
        int jugadoresActuales = sala.getJugadores().size();

        System.out.println("[DIAG][ManejadorUnirseSala] intento unirse=" + evento.getUsuario().getNickname()
                + " jugadoresActuales=" + jugadoresActuales + " limiteJugadores=" + limiteJugadores);

        if (jugadoresActuales >= limiteJugadores) {
            System.out.println("[DIAG][ManejadorUnirseSala] RECHAZADO por limite: " + evento.getUsuario().getNickname());
            return;
        }

        sala.agregarJugador(obtenerJugador(evento));

        System.out.println("[DIAG][ManejadorUnirseSala] jugadores tras agregar=" + jugadoresNicknames(sala.getJugadores()));

        EventoInfoSala eventoPeticionInfoSala = new EventoInfoSala(ConfigLoader.getInstance().getUsuarioMatchmaker(), obtenerSalaActual());
        matchmaker.directMessage(eventoPeticionInfoSala, evento.getUserSender());

        EventoSalaActualizada eventoSalaActualizada = new EventoSalaActualizada(ConfigLoader.getInstance().getUsuarioMatchmaker(), sala.getJugadores());
        matchmaker.broadcast(eventoSalaActualizada);

    }

    private JugadorDTO obtenerJugador(EventoUnirseSala evento) {
        NuevoUsuarioDTO usuarioDTO = evento.getUsuario();
        JugadorDTO nuevoJugador = new JugadorDTO();
        nuevoJugador.setNickname(usuarioDTO.getNickname());
        nuevoJugador.setFotoPerfil(usuarioDTO.getIdAvatarSeleccionado());

        return nuevoJugador;
    }

    private SalaDTO obtenerSalaActual() {
        ConfiguracionJuegoDTO configuracionJuego = new ConfiguracionJuegoDTO(sala.getConfiguracion().getLimiteJugadores(),
                sala.getConfiguracion().getPuntajeMax(), sala.getConfiguracion().getDificultad(), sala.getConfiguracion().getPuntajes());
        SalaDTO salaActual = new SalaDTO(sala.getJugadores(), sala.getHost(), configuracionJuego);
        return salaActual;
    }

    private static String jugadoresNicknames(List<JugadorDTO> jugadores) {
        java.util.List<String> nicknames = new java.util.ArrayList<>();
        for (JugadorDTO jugador : jugadores) {
            nicknames.add(jugador.getNickname());
        }
        return nicknames.toString();
    }
}
