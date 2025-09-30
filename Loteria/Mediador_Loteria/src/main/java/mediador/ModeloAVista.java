/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mediador;

import dtos.FichaDTO;
import modelo.IModeloVista;
import modelo.ModeloVistaFacade;

/**
 *
 * @author pedro
 */
public class ModeloAVista {
    
    public void colocarFicha(FichaDTO ficha){
        IModeloVista modeloVista = ModeloVistaFacade.getInstance();
        modeloVista.colocarFicha(ficha);
    }
}
