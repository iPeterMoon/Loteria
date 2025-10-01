package modelo;

import dtos.FichaDTO;

/**
 * Interfaz que define el contrato para la comunicación entre el modelo de la
 * vista y el modelo del juego.
 *
 * @author rocha
 */
public interface IModeloVista {

    /**
     * Indica al modelo del juego que se debe colocar una ficha de acuerdo con
     * la información contenida en el DTO recibido.
     *
     * @param fichaDTO Objeto FichaDTO que contiene los datos de la ficha
     */
    public void colocarFicha(FichaDTO fichaDTO);
}
