/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import dtos.aplicacion.ConfiguracionJuegoDTO;
import implementaciones.ConfiguracionJuego;

/**
 *
 * @author Alici
 */
public class ConfiguracionMapperMatchmaker {

    public static ConfiguracionJuego toConfiguracionJuego(ConfiguracionJuegoDTO configuracionDTO) {
        ConfiguracionJuego configuracion = new ConfiguracionJuego();
        configuracion.setDificultad(configuracionDTO.getDificultad());
        configuracion.setLimiteJugadores(configuracionDTO.getLimiteJugadores());
        configuracion.setPuntajeMax(configuracionDTO.getPuntajeMax());
        configuracion.setPuntajes(configuracionDTO.getPuntajes());

        return configuracion;
    }
}
