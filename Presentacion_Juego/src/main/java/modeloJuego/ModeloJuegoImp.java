/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modeloJuego;

import java.awt.Point;

import dtos.FichaDTO;
import java.util.List;
import modelo.IModeloVista;
import modelo.ModeloVistaFacade;

/**
 *
 * @author Alici
 */
public class ModeloJuegoImp implements IModeloJuego {

    private List<Jugador> jugadoresSecundario;
    private Jugador jugadorPrincipal;
    private Jugador host;
    private IModeloVista vista = ModeloVistaFacade.getInstance();

    public ModeloJuegoImp() {
    }

    public ModeloJuegoImp(List<Jugador> jugadoresSecundario, Jugador jugadorPrincipal, Jugador host) {
        this.jugadoresSecundario = jugadoresSecundario;
        this.jugadorPrincipal = jugadorPrincipal;
        this.host = host;
    }

    @Override
    public void validaMovimiento(Point posicion) {
        // Se debe de obtener la tarjeta del jugador
        // obtener la carta en la posicion dada.
        // verificar la carta actual
        //si son iguales es decir la carta en la posicion actual y la carta cantada actual
        // modeloVista.colocarFicha(posicion);
        // en caso contrario no pasaria nada
        validarMovimientoMock(posicion);
    }

    /**
     * METODO POR MIENTRAS PARA PROBAR EL FLUJO EN LA VISTA
     *
     * @param posicion
     */
    public void validarMovimientoMock(Point posicion) {
        FichaDTO ficha = new FichaDTO("Jerson", posicion);
        vista.colocarFicha(ficha);
    }
 
}
