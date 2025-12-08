/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import dtos.aplicacion.MensajeDTO;
import enums.TipoMensajePantalla;
import util.Subject;

/**
 *
 * @author Alici
 */
public class MensajeSubject extends Subject {

    private String titulo;
    private String mensaje;
    private boolean fueExitoso;
    private TipoMensajePantalla tipoMensaje;

    public String getMensaje() {
        return mensaje;
    }

    public boolean isExitoso() {
        return fueExitoso;
    }

    public String getTitulo() {
        return titulo;
    }

    public TipoMensajePantalla getTipoMensaje() {
        return tipoMensaje;
    }

    public void actualizarMensaje(MensajeDTO mensajeDTO) {
        titulo = mensajeDTO.getTitulo();
        mensaje = mensajeDTO.getMensaje();
        fueExitoso = mensajeDTO.isFueExitoso();
        tipoMensaje = mensajeDTO.getTipoMensaje();
        notifyAllObservers();
    }

}
