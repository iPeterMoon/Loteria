/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import util.Subject;

/**
 *
 * @author Alici
 */
public abstract class MensajeSubject extends Subject {

    private String titulo;
    private String mensaje;
    private boolean fueExitoso;

    public String getMensaje() {
        return mensaje;
    }

    public boolean isExitoso() {
        return fueExitoso;
    }

    public String getTitulo() {
        return titulo;
    }

}
