package mappers;

import dtos.aplicacion.ConfiguracionJuegoDTO;
import dtos.aplicacion.SalaDTO;
import modelo.ConfiguracionJuego;

/**
 *
 * @author norma
 */
public class SalaMapperModelo {

    public static ConfiguracionJuego toConfiguracionJuego(SalaDTO salaDTO) {
        ConfiguracionJuegoDTO configuracionDTO = salaDTO.getConfiguracion();
        ConfiguracionJuego configuracion = new ConfiguracionJuego();
        configuracion.setDificultad(configuracionDTO.getDificultad());
        configuracion.setLimiteJugadores(configuracionDTO.getLimiteJugadores());
        configuracion.setPuintajes(configuracionDTO.getPuntajes());
        configuracion.setPuntajeMax(configuracionDTO.getPuntajeMax());
        return configuracion;
    }
}
