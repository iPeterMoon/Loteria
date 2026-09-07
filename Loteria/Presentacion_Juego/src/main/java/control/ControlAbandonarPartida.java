/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import java.awt.Point;
import modelo.IModeloControl;

/**
 *
 * @author petermoon
 */
public class ControlAbandonarPartida {
    
    private final IModeloControl controlModelo;
    
    /**
     * Constructor que recibe la dependencia de IModeloControl.
     * @param controlModelo La instancia de la interfaz de control del modelo.
     */
    public ControlAbandonarPartida(IModeloControl controlModelo){
        if (controlModelo == null) {
            throw new IllegalArgumentException("La dependencia IModeloControl no puede ser nula.");
        }
        this.controlModelo = controlModelo;
    }

    /**
     * Metodo para abandonar la partida en curso. Notifica a los demás
     * jugadores y regresa a la sala de espera; no cierra la aplicación.
     */
    public void abandonarPartida() {
        controlModelo.abandonarPartida();
    }
}
