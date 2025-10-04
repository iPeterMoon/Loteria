/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package vista;

import modelo.Subject;

/**
 * clase interface del IObserver para definir 
 * un contrato que las clases deben cumplir, 
 * especificando qué métodos deben tener pero 
 * no cómo se implementan
 * @author rocha
 */
public interface IObserver {
    /**
     * metodo para actualizar el juego
     * @param subject representacion del sujeto
     */
    public void update(Subject subject);
}
