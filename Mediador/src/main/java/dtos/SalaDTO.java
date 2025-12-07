package dtos;

import java.util.List;

/**
 *
 * @author norma
 */
public class SalaDTO {
    
    private final List<JugadorDTO> jugadores;
    private String jugadorHost;
    private ConfiguracionJuegoDTO configuracion;

    public SalaDTO(List<JugadorDTO> jugadores) {
        this.jugadores = jugadores;
    }

    public SalaDTO(List<JugadorDTO> jugadores, String jugadorHost, ConfiguracionJuegoDTO configuracion) {
        this.jugadores = jugadores;
        this.jugadorHost = jugadorHost;
        this.configuracion = configuracion;
    }

    public List<JugadorDTO> getJugadores() {
        return jugadores;
    }
    
    public String getJugadorHost() {
        return jugadorHost;
    }

    public void setJugadorHost(String jugadorHost) {
        this.jugadorHost = jugadorHost;
    }

    public ConfiguracionJuegoDTO getConfiguracion() {
        return configuracion;
    }

    public void setConfiguracion(ConfiguracionJuegoDTO configuracion) {
        this.configuracion = configuracion;
    }

    @Override
    public String toString() {
        return "SalaDTO{" + "jugadores=" + jugadores + ", jugadorHost=" + jugadorHost + ", configuracion=" + configuracion + '}';
    }
    
}
