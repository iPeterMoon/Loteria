/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package procesadores;

import eventos.Evento;

/**
 *
 * @author Alici
 */
public interface IHandler {

    void setNext(IHandler next);

    void procesar(Evento evento);

}
