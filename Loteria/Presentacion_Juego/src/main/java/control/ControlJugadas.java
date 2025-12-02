/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import modelo.IModeloControl;

/**
 *
 * @author Jp
 */
public class ControlJugadas {
    private final IModeloControl controlModelo;
    
    public ControlJugadas(IModeloControl controlModelo){
        if (controlModelo == null) {
            throw new IllegalArgumentException("La dependencia IModeloControl no puede ser nula.");
        }
        this.controlModelo = controlModelo;
    }

    public void cantarLlena() {
        controlModelo.verificarJugadaLlena();
    }
    
}
