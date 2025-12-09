package procesadores_modelo;

import dtos.aplicacion.JugadorDTO;
import dtos.aplicacion.MensajeDTO;
import enums.TipoEvento;
import enums.TipoMensajePantalla;
import eventos.Evento;
import eventos.eventos_aplicacion.EventoFicha;
import eventos.eventos_aplicacion.EventoPeerDesconectado;
import java.util.ArrayList;
import java.util.List;
import mappers.JugadorMapperModelo;
import modelo.Jugador;
import modelo.ModeloJuegoFacade;
import modelo.Sala;

/**
 *
 * @author petermoon
 */
public class ManejadorEventoDesconexion extends ManejadorEventos {

    private String userDesconectado;

    @Override
    public void procesar(Evento evento) {
        if (evento.getTipoEvento().equals(TipoEvento.PEER_DESCONECTADO)) {
            EventoPeerDesconectado eventoDesconexion = (EventoPeerDesconectado) evento;
            procesarDesconexion(eventoDesconexion);
        } else if (next != null) {
            next.procesar(evento);
        }
    }

    //  LÓGICA DE ABANDONAR PARTIDA 
    private void procesarDesconexion(EventoPeerDesconectado evento) {
        this.userDesconectado = evento.getUserSender();

        // 1. Acceder a la Sala
        Sala sala = Sala.getInstance();

        // 2. Eliminar al jugador desconectado de la lista general
        List<Jugador> listaJugadores = sala.getJugadoresSecundario();

        if (listaJugadores != null) {
            // Borramos al que se fue buscando por Nickname o ID
            listaJugadores.removeIf(j -> j.getNickname().equals(userDesconectado));
        }

        actualizarJugadoresEnVista();
        
        if(sala.isJuegoEnCurso() && listaJugadores.isEmpty()){
            ModeloJuegoFacade.getInstance().cerrarJuegoDefinitivo(sala.getJugadorPrincipal().getNickname());
        }
    }

    private void actualizarJugadoresEnVista() {
        ModeloJuegoFacade.getInstance().eliminarJugadorDePartida(userDesconectado);
    }
}
