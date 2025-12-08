/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eventos.eventos_aplicacion;

import dtos.aplicacion.NuevoUsuarioDTO;
import enums.TipoEvento;
import eventos.Evento;

/**
 *
 * @author norma
 */
public class EventoUnirseSala extends Evento{
    
    private NuevoUsuarioDTO usuario;
    
    public EventoUnirseSala(String userSender, NuevoUsuarioDTO usuario) {
        super(TipoEvento.UNIRSE_SALA, userSender);
        this.usuario = usuario;
    }

    public NuevoUsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(NuevoUsuarioDTO usuario) {
        this.usuario = usuario;
    }   
    
}
