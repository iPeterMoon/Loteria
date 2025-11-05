/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

/**
 * clase interface del IObserver para definir un contrato que las clases deben
 * cumplir, especificando qué métodos deben tener pero no cómo se implementan.
 *
 * @author rocha
 */
public interface IObserver {

    /**
     * metodo para actualizar el juego.
     *
     * @param object
     */
    public void update(Object object);
}
