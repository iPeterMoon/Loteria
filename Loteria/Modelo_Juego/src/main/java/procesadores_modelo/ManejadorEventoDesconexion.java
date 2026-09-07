package procesadores_modelo;

import enums.TipoEvento;
import eventos.Evento;
import eventos.eventos_aplicacion.EventoPeerDesconectado;
import java.util.List;
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

              
        String nicknamePrincipal = sala.getNicknameJugadorPrincipal();

        // Un jugador puede abandonar la partida y luego volver a unirse a la
        // sala (queda como espectador, sin tarjeta repartida en esta ronda).
        // Ese "reingresado" sigue apareciendo en jugadoresSecundario aunque
        // no esté jugando de verdad, así que "me quedé solo" debe contar
        // solo a los que sí tienen tarjeta (participantes activos de la
        // ronda), no a cualquiera que simplemente esté en la sala.
        boolean quedanOponentesActivos = listaJugadores != null
                && listaJugadores.stream().anyMatch(j -> j.getTarjeta() != null);

        if(sala.isJuegoEnCurso() && !quedanOponentesActivos && nicknamePrincipal != null){
            // Aunque ya no quede nadie jugando activamente esta ronda, puede
            // seguir habiendo alguien más en la sala (p. ej. un jugador que
            // abandonó y se reincorporó como espectador). En ese caso no hay
            // que cerrar/eliminar la sala del todo — solo declarar la ronda
            // ganada por abandono y regresar a la sala de espera para que
            // quien quede la pueda seguir usando.
            boolean haySalaConAlguienMas = listaJugadores != null && !listaJugadores.isEmpty();
            if (haySalaConAlguienMas) {
                ModeloJuegoFacade.getInstance().ganarPorAbandono(nicknamePrincipal);
            } else {
                ModeloJuegoFacade.getInstance().cerrarJuegoDefinitivo(nicknamePrincipal);
            }
        } else {
            actualizarJugadoresEnVista();
        }
    }

    private void actualizarJugadoresEnVista() {
        ModeloJuegoFacade.getInstance().eliminarJugadorDePartida(userDesconectado);
    }
}
