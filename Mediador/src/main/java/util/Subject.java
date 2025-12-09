/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.util.LinkedList;
import java.util.List;

/**
 * Clase abstracta que representa el sujeto (Subject) en el patrón de diseño
 * Observer.
 *
 * Proporciona la infraestructura básica para manejar la suscripción de
 * observadores y la propagación de notificaciones.
 *
 * @author rocha
 */
public abstract class Subject {

    /**
     * Lista de observadores suscritos.
     */
    private List<IObserver> observers;

    /**
     * Constructor que inicializa la lista de observadores.
     */
    public Subject() {
        this.observers = new LinkedList<>();
    }

    /**
     * Agrega un observador a la lista de observadores.
     *
     * @param observer Objeto que se desea suscribir.
     */
    public void addObserver(IObserver observer) {
        observers.add(observer);
    }

    /**
     * Notifica a todos los observadores registrados sobre un cambio en el
     * sujeto.
     *
     * Llama al método update() de cada observador, pasando como parámetro la
     * instancia actual del sujeto que emite la notificación.
     */
    public void notifyAllObservers() {
        for (IObserver observer : observers) {
            observer.update(this);
        }
    }
}
