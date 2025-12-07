/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eventos.eventos_aplicacion;

import dtos.aplicacion.ConfiguracionJuegoDTO;
import dtos.aplicacion.NuevoUsuarioDTO;
import enums.TipoEvento;
import eventos.Evento;

/**
 *
 * @author Alici
 */
public class EventoCrearSala extends Evento {

    private ConfiguracionJuegoDTO configuracionSala;
    private NuevoUsuarioDTO usuarioHost;

    public EventoCrearSala(String userSender) {
        super(TipoEvento.SALA_CREADA, userSender);
    }

    public EventoCrearSala(ConfiguracionJuegoDTO configuracionSala, NuevoUsuarioDTO usuarioHost, String userSender) {
        super(TipoEvento.SALA_CREADA, userSender);
        this.configuracionSala = configuracionSala;
        this.usuarioHost = usuarioHost;
    }

    public ConfiguracionJuegoDTO getConfiguracionSala() {
        return configuracionSala;
    }

    public void setConfiguracionSala(ConfiguracionJuegoDTO configuracionSala) {
        this.configuracionSala = configuracionSala;
    }

    public NuevoUsuarioDTO getUsuarioHost() {
        return usuarioHost;
    }

    public void setUsuarioHost(NuevoUsuarioDTO usuarioHost) {
        this.usuarioHost = usuarioHost;
    }

}
