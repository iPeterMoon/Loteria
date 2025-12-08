package procesadores_modelo;

import dtos.aplicacion.JugadorDTO;
import enums.TipoEvento;
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
public class ManejadorEventoDesconexion extends ManejadorEventos{

    private String userDesconectado;
    
    @Override
    public void procesar(Evento evento) {
        if (evento.getTipoEvento().equals(TipoEvento.PEER_DESCONECTADO)) {
            EventoPeerDesconectado eventoDesconexion = (EventoPeerDesconectado) evento;
            procesarDesconexion(eventoDesconexion);
        } else if (next != null) {
            next.procesar(evento);
        }}
    
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
    }
    
   private void actualizarJugadoresEnVista(){
       actualizarJugadoresEnConfiguracion();
   }
   
   private void actualizarJugadoresEnConfiguracion(){
       Sala sala = Sala.getInstance();
       
       List<JugadorDTO> jugadoresDTO = new ArrayList<>();
       jugadoresDTO.add(JugadorMapperModelo.toDTO(sala.getJugadorPrincipal(), true));
       for(Jugador jugador : sala.getJugadoresSecundario()){
           jugadoresDTO.add(JugadorMapperModelo.toDTO(jugador, false));
       }
       
       ModeloJuegoFacade.getInstance().actualizarJugadoresSala(jugadoresDTO);
       ModeloJuegoFacade.getInstance().eliminarJugadorDePartida(userDesconectado);
   }
}
