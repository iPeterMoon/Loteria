/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.LinkedList;
import java.util.List;
import vista.IObserver;

/**
 *
 * @author rocha
 */
public abstract class Subject {
    protected List<IObserver> observers;

    public Subject() {
        this.observers = new LinkedList<>();
    }
    
    public void addObserver(IObserver observer) {
        observers.add(observer);
    }
    
    public void notifyAllObservers() {
        for (IObserver observer : observers) {
            observer.update(this);
        }
    }
}
