/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modelo;

/**
 *
 * @author Alici
 */
public class ModeloControlAplicacion implements IModeloControlAplicacion {

    ModeloVistaConfiguracionFacade modeloFacade = ModeloVistaConfiguracionFacade.getInstance();

    @Override
    public void siguienteAvatar(int accion) {
        modeloFacade.actualizarAvatar(accion);
    }

}
