/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package procesadores_modelo;

import eventos.Evento;

/**
 *
 * @author Alici
 */
public abstract class ManejadorEventos implements IHandler {

    protected IHandler next;

    @Override
    public void setNext(IHandler next) {
        this.next = next;
    }

    @Override
    public abstract void procesar(Evento evento);

}
