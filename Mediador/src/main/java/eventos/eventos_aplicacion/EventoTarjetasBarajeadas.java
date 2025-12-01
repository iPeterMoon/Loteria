package eventos.eventos_aplicacion;

import java.util.Map;

import dtos.TarjetaDTO;
import enums.TipoEvento;
import eventos.Evento;

/**
 * Evento para enviar las tarjetas barajeadas a todos los jugadores
 * este evento lo envía el host cuando inicia una nueva partida.
 */
public class EventoTarjetasBarajeadas extends Evento{

    private Map<String, TarjetaDTO> jugadoresTarjetas;

    /**
     * Constructor del evento
     * @param userSender Usuario que lo envía (host)
     * @param jugadoresTarjetas Mapa con los nicknames de los jugadores y sus tarjetas
     */
    public EventoTarjetasBarajeadas(String userSender, Map<String, TarjetaDTO> jugadoresTarjetas) {
        super(TipoEvento.TARJETAS_BARAJEADAS, userSender);
        this.jugadoresTarjetas = jugadoresTarjetas;
    }

    /**
     * @return Mapa con el nickname del jugador y su tarjeta
     */
    public Map<String, TarjetaDTO> getJugadoresTarjetas(){
        return jugadoresTarjetas;
    }

}
