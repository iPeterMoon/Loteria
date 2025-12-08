package procesadores;

import enums.TipoEvento;
import eventos.Evento;
import eventos.eventos_aplicacion.EventoSalaActualizada;
import eventos.eventos_aplicacion.EventoSalirSalaEspera;
import eventos.eventos_aplicacion.EventoInfoSala;
import implementaciones.Matchmaker;
import implementaciones.Sala;
import util.ConfigLoader;

public class ManejadorDesconexion extends ManejadorEventos {

    Sala sala = Sala.getInstance();
    Matchmaker matchmaker = Matchmaker.getInstance();

    @Override
    public void procesar(Evento evento) {
        if (evento.getTipoEvento().equals(TipoEvento.SALIR_SALA_ESPERA)) {
            manejarSalirSalaEspera((EventoSalirSalaEspera) evento);
        } else if (next != null) {
            next.procesar(evento);
        }
    }

    private void manejarSalirSalaEspera(EventoSalirSalaEspera evento) {
        String jugadorAEliminar = evento.getUserSender();
        boolean eraHost = jugadorAEliminar.equals(sala.getHost());

        sala.eliminarJugador(jugadorAEliminar);

        if (eraHost || sala.getJugadores().isEmpty()) {

            eliminarSala();

            EventoInfoSala eventoSalaEliminada = new EventoInfoSala(
                    ConfigLoader.getInstance().getUsuarioMatchmaker(),
                    null 
            );
            matchmaker.broadcast(eventoSalaEliminada);

        } else {
            EventoSalaActualizada eventoSalaActualizada = new EventoSalaActualizada(
                    ConfigLoader.getInstance().getUsuarioMatchmaker(),
                    sala.getJugadores() 
            );
            matchmaker.broadcast(eventoSalaActualizada);
        }
    }

    private void eliminarSala() {
        sala.setHost(null);
        sala.setConfiguracion(null);
        sala.limpiarJugadores();
    }

}
