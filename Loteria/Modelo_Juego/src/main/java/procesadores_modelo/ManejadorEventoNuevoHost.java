package procesadores_modelo;

import dtos.aplicacion.JugadorDTO;
import enums.TipoEvento;
import enums.TipoNivel;
import eventos.Evento;
import eventos.eventos_aplicacion.EventoNuevoHost;
import java.util.ArrayList;
import java.util.List;
import mappers.JugadorMapperModelo;
import modelo.Cantador;
import modelo.Jugador;
import modelo.ModeloJuegoFacade;
import modelo.Sala;

/**
 *
 * @author petermoon
 */
public class ManejadorEventoNuevoHost extends ManejadorEventos {

    @Override
    public void procesar(Evento evento) {
        if (evento.getTipoEvento().equals(TipoEvento.NUEVO_HOST)) {
            manejarNuevoHost((EventoNuevoHost) evento);
        } else if (next != null) {
            next.procesar(evento);
        }
    }

    private void manejarNuevoHost(EventoNuevoHost evento) {
        Sala sala = Sala.getInstance();

        String userHost = evento.getNuevoHostUser();

        sala.setHost(userHost);
        if (sala.getConfiguracion() != null) {
            ModeloJuegoFacade.getInstance().actualizarDatosSala(
                    userHost,
                    sala.getConfiguracion().getLimiteJugadores(),
                    sala.getConfiguracion().getDificultad()
            );
            //Dormimos tantito por si se acaba la partida
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            if (userHost.equals(sala.getJugadorPrincipal().getNickname()) && sala.isPartidaEnCurso()) {
                int intervalo = Sala.getInstance().getConfiguracion().getDificultad().getIntervalo();
                Cantador.getInstance().iniciarCanto(intervalo);

            }
        }
    }
}
