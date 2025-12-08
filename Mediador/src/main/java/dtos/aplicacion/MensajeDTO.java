/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos.aplicacion;

import enums.TipoMensajePantalla;

/**
 *
 * @author Alici
 */
public class MensajeDTO {

    private String titulo;
    private String mensaje;
    private boolean fueExitoso;
    private TipoMensajePantalla tipoMensaje;

    public MensajeDTO() {
    }

    public MensajeDTO(String titulo, String mensaje, boolean fueExitoso, TipoMensajePantalla tipoMensaje) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.fueExitoso = fueExitoso;
        this.tipoMensaje = tipoMensaje;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public boolean isFueExitoso() {
        return fueExitoso;
    }

    public void setFueExitoso(boolean fueExitoso) {
        this.fueExitoso = fueExitoso;
    }

    public TipoMensajePantalla getTipoMensaje() {
        return tipoMensaje;
    }

    public void setTipoMensaje(TipoMensajePantalla tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }

}
