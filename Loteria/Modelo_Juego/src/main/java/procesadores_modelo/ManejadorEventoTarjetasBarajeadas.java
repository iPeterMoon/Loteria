package procesadores_modelo;

import enums.TipoEvento;
import eventos.Evento;
import eventos.eventos_aplicacion.EventoTarjetasBarajeadas;
import mappers.TarjetaMapper;
import modelo.Jugador;
import modelo.Sala;
import modelo.Tarjeta;
import dtos.TarjetaDTO;
import java.util.HashMap;
import java.util.Map;

/**
 * Manejador para evento de tarjetas barajeadas, se encarga
 * de asignar la tarjeta adecuada a los jugadores recibida desde el
 */
public class ManejadorEventoTarjetasBarajeadas extends ManejadorEventos {
    
    @Override
    public void procesar(Evento evento) {
        if (evento.getTipoEvento().equals(TipoEvento.TARJETAS_BARAJEADAS)) {
            EventoTarjetasBarajeadas eventoTarjetas = (EventoTarjetasBarajeadas) evento;
            procesarEvento(eventoTarjetas);
        } else if (next != null) {
            next.procesar(evento);
        }
    }

    /**
     * Procesa el evento consiguiendo el mapa con los nicknames 
     * de los jugadores y sus tarjetas y repartiendolas como van
     * 
     * @param evento Evento conteniendo las tarjetas barajeadas.
     */
    private void procesarEvento(EventoTarjetasBarajeadas evento){
        // Obtener el mapa de tarjetas DTO del evento
        Map<String, TarjetaDTO> jugadoresTarjetasDTO = evento.getJugadoresTarjetas();
        
        // Crear un nuevo mapa para almacenar las tarjetas convertidas
        Map<String, Tarjeta> jugadoresTarjetas = new HashMap<>();
        
        // Convertir cada TarjetaDTO a Tarjeta usando TarjetaMapper
        jugadoresTarjetasDTO.forEach((nickname, tarjetaDTO) -> {
            Tarjeta tarjeta = TarjetaMapper.toTarjeta(tarjetaDTO);
            jugadoresTarjetas.put(nickname, tarjeta);
        });
        
        repartirTarjetas(jugadoresTarjetas);
    }

    /**
     * Metodo auxiliar para repartir las tarjetas a los jugadores
     * @param jugadoresTarjetas Mapa con los nicknames de los jugadores y sus tarjetas
     */
    private void repartirTarjetas(Map<String, Tarjeta> jugadoresTarjetas){
        Sala sala = Sala.getInstance();
        Jugador jugadorPrincipal = sala.getJugadorPrincipal();
        jugadorPrincipal.setTarjeta(jugadoresTarjetas.get(jugadorPrincipal.getNickname()));
        for(Jugador jugador : sala.getJugadoresSecundario()){
            jugador.setTarjeta(jugadoresTarjetas.get(jugador.getNickname()));
        }
    }
}
