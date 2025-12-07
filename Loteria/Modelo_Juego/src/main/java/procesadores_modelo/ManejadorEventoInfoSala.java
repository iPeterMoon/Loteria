package procesadores_modelo;

import dtos.aplicacion.JugadorDTO;
import enums.TipoEvento;
import eventos.Evento;
import eventos.eventos_aplicacion.EventoInfoSala;
import java.util.ArrayList;
import java.util.List;
import mappers.JugadorMapperModelo;
import modelo.Jugador;
import modelo.ModeloJuegoFacade;
import modelo.Sala;

/**
 *
 * @author norma
 */
public class ManejadorEventoInfoSala extends ManejadorEventos{

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
        List<JugadorDTO> jugadoresDTO = evento.getSala().getJugadores();

        List<Jugador> jugadores = new ArrayList<>();
        for (JugadorDTO jugadorDTO : jugadoresDTO) {
            jugadores.add(JugadorMapperModelo.toJugador(jugadorDTO));
        }

        //falta guardar la info de la sala (mapper falta hacerlo). (pero falta modificar los atributos de la sala y crear la clase configuración.
        
        sala.setJugadoresSecundario(jugadores);

        ModeloJuegoFacade.getInstance().actualizarDatosSala(evento.getSala().getConfiguracion().getLimiteJugadores(), 
                evento.getSala().getConfiguracion().getDificultad());
        ModeloJuegoFacade.getInstance().actualizarJugadoresSala(jugadoresDTO);
    }
}
