/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import dtos.aplicacion.ConfiguracionJuegoDTO;
import dtos.aplicacion.JugadorDTO;
import dtos.aplicacion.SalaDTO;
import implementaciones.Sala;
import java.util.List;

/**
 *
 * @author Alici
 */
public class SalaMapperMatchmaker {

    public static SalaDTO toSalaDTO(ConfiguracionJuegoDTO configuracion) {
        List<JugadorDTO> jugadores = Sala.getInstance().getJugadores();
        String jugadorHost = Sala.getInstance().getHost();
        SalaDTO sala = new SalaDTO(jugadores, jugadorHost, configuracion);
        return sala;
    }
}
