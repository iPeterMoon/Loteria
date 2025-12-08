/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import dtos.aplicacion.JugadorDTO;
import dtos.aplicacion.NuevoUsuarioDTO;

/**
 *
 * @author Alici
 */
public class JugadorMapperMatchmaker {

    public static JugadorDTO toJugadorDTO(NuevoUsuarioDTO hostDTO) {
        JugadorDTO host = new JugadorDTO();
        host.setNickname(hostDTO.getNickname());
        host.setFotoPerfil(hostDTO.getIdAvatarSeleccionado());
        return host;
    }
}
