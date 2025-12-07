/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import dtos.aplicacion.NuevoUsuarioDTO;
import modelo.IModeloControlNegocio;

/**
 *
 * @author Alici
 */
public class ControlConfiguracion {

    private final IModeloControlNegocio controlModelo;

    public ControlConfiguracion(IModeloControlNegocio controlModelo) {
        if (controlModelo == null) {
            throw new IllegalArgumentException("La dependencia IModeloControlConfiguracion no puede ser nula.");
        }
        this.controlModelo = controlModelo;
    }

    public void configurarUsuario(NuevoUsuarioDTO nuevoUsuario) {
//        controlModelo.
    }

}
