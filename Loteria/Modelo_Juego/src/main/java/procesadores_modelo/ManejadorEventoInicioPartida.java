package procesadores_modelo;

import enums.TipoEvento;
import eventos.Evento;
import eventos.eventos_aplicacion.EventoIniciarPartida;
import modelo.ModeloJuegoFacade;
import modelo.Sala;

public class ManejadorEventoInicioPartida extends ManejadorEventos{

    @Override
    public void procesar(Evento evento) {
        if (evento.getTipoEvento().equals(TipoEvento.INICIAR_PARTIDA)) {
            EventoIniciarPartida eventoInicio = (EventoIniciarPartida) evento;
            procesarInicioPartida(eventoInicio);
        } else if (next != null) {
            next.procesar(evento);
        }    
    }

    private void procesarInicioPartida(EventoIniciarPartida evento) {
        ModeloJuegoFacade modeloJuego = ModeloJuegoFacade.getInstance();
        Sala sala = Sala.getInstance();

        if (!sala.salaCreada()) {
            return;
        }

        String sender = evento.getUserSender();
        String hostActual = sala.getHost();
        
        if (hostActual == null || !hostActual.equals(sender)) {
            System.out.println("[Seguridad] Ignorando inicio de partida: Sender (" + sender + ") no es el Host (" + hostActual + ")");
            return;
        }

        if (modeloJuego.getJugadorPrincipal() == null) {
            return;
        }
        
        String user = modeloJuego.getJugadorPrincipal().getNickname();
        
        if(sala.isInSala(user)){
            modeloJuego.cerrarSalaEspera();
            modeloJuego.mostrarFramePartida();
            if(!sala.isJuegoEnCurso()){
                sala.setJuegoEnCurso(true);
            }
            sala.setPartidaEnCurso(true);
        }
    }
    
}
