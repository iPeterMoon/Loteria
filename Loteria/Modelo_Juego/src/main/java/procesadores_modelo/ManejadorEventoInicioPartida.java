package procesadores_modelo;

import enums.TipoEvento;
import eventos.Evento;
import modelo.ModeloJuegoFacade;
import modelo.Sala;

public class ManejadorEventoInicioPartida extends ManejadorEventos{

    @Override
    public void procesar(Evento evento) {
        if (evento.getTipoEvento().equals(TipoEvento.INICIAR_PARTIDA)) {
            ModeloJuegoFacade modeloJuego = ModeloJuegoFacade.getInstance();
            String user = modeloJuego.getJugadorPrincipal().getNickname();
            
            if(Sala.getInstance().isInSala(user)){
                modeloJuego.cerrarSalaEspera();
                modeloJuego.mostrarFramePartida();
            }
        } else if (next != null) {
            next.procesar(evento);
        }    
    }

}
