/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package procesadores_modelo;

import eventos.Evento;

/**
 *
 * @author Alici
 */
public interface IHandler {

    public void setNext(IHandler next);

    public void procesar(Evento evento);

}
