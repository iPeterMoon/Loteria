package procesadores;

import dtos.aplicacion.JugadorDTO;
import enums.TipoEvento;
import eventos.Evento;
import eventos.eventos_aplicacion.EventoInfoSala;
import eventos.eventos_aplicacion.EventoNuevoHost;
import eventos.eventos_aplicacion.EventoPeerDesconectado;
import eventos.eventos_aplicacion.EventoSalaActualizada;
import implementaciones.Matchmaker;
import implementaciones.Sala;
import util.ConfigLoader;

/**
 *
 * @author petermoon
 */
public class ManejadorDesconexion extends ManejadorEventos{

    Sala sala = Sala.getInstance();
    Matchmaker matchmaker = Matchmaker.getInstance();

    
    @Override
    public void procesar(Evento evento) {
    if (evento.getTipoEvento().equals(TipoEvento.PEER_DESCONECTADO)) {
            manejarDesconexion((EventoPeerDesconectado) evento);
        } else if (next != null) {
            next.procesar(evento);
        }
    }

    private void manejarDesconexion(EventoPeerDesconectado evento) {
        String jugadorAEliminar = evento.getPeerDesconectado().getUser();
        boolean eraHost = jugadorAEliminar.equals(sala.getHost());

        System.out.println("[DIAG][ManejadorDesconexion] jugadorAEliminar=" + jugadorAEliminar
                + " host=" + sala.getHost() + " eraHost=" + eraHost
                + " jugadoresAntes=" + jugadoresNicknames(sala.getJugadores()));

        sala.eliminarJugador(jugadorAEliminar);

        System.out.println("[DIAG][ManejadorDesconexion] jugadoresDespues=" + jugadoresNicknames(sala.getJugadores()));

        if (sala.getJugadores().isEmpty()) {

            eliminarSala();

            EventoInfoSala eventoSalaEliminada = new EventoInfoSala(
                    ConfigLoader.getInstance().getUsuarioMatchmaker(),
                    null 
            );
            matchmaker.broadcast(eventoSalaEliminada);

        } else if (eraHost){
            String nuevoHost = obtenerNuevoHost(jugadorAEliminar);
            if(nuevoHost != null){
                // Actualizar también el host aquí, no solo avisarle a los
                // clientes por broadcast -- si no, la próxima desconexión
                // comparará contra este mismo host ya desactualizado y
                // "eraHost" dará falso aunque sí lo sea, saltándose la
                // reasignación por completo.
                sala.setHost(nuevoHost);
                EventoNuevoHost eventoNuevoHost = new EventoNuevoHost(
                        nuevoHost,
                        ConfigLoader.getInstance().getUsuarioMatchmaker()
                );
                matchmaker.broadcast(eventoNuevoHost);
            }
            actualizarSala();
        }else {
            actualizarSala();
        }
    }

    private void eliminarSala() {
        sala.setHost(null);
        sala.setConfiguracion(null);
        sala.limpiarJugadores();
    }
 
    private void actualizarSala(){
        EventoSalaActualizada eventoSalaActualizada = new EventoSalaActualizada(
                    ConfigLoader.getInstance().getUsuarioMatchmaker(),
                    sala.getJugadores() 
            );
            matchmaker.broadcast(eventoSalaActualizada);
    }
    
    private String obtenerNuevoHost(String viejoHost){
        for(JugadorDTO jugador : sala.getJugadores()){
            if (!jugador.getNickname().equals(viejoHost)){
                return jugador.getNickname();
            }
        }
        return null;
    }

    private static String jugadoresNicknames(java.util.List<JugadorDTO> jugadores) {
        java.util.List<String> nicknames = new java.util.ArrayList<>();
        for (JugadorDTO jugador : jugadores) {
            nicknames.add(jugador.getNickname());
        }
        return nicknames.toString();
    }

}
