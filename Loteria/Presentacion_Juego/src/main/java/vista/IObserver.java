package vista;

import modelo.Subject;

/**
 * clase interface del IObserver para definir 
 * un contrato que las clases deben cumplir, 
 * especificando qué métodos deben tener pero 
 * no cómo se implementan.
 * @author rocha
 */
public interface IObserver {
    /**
     * metodo para actualizar el juego.
     * @param subject representacion del sujeto.
     */
    public void update(Subject subject);
}
