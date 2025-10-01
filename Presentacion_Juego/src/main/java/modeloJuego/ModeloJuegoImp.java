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
 * Clase que implementa los métodos de la interfaz IModeloJuego
 *
 * @author Alici
 */
public class ModeloJuegoImp implements IModeloJuego {

    /**
     * Lista de jugadores secundarios al jugador de la vista principal
     */
    private List<Jugador> jugadoresSecundario;
    /**
     * Jugador principal que tiene la vista principal
     */
    private Jugador jugadorPrincipal;
    /**
     * Jugador host de la ronda
     */
    private Jugador host;
    private IModeloVista vista = ModeloVistaFacade.getInstance();

    /**
     * Constructor vacio
     */
    public ModeloJuegoImp() {
    }

    /**
     * Constructor con los atributos de jugadores secundarios, jugador principal
     * y host de la ronda.
     *
     * @param jugadoresSecundario
     * @param jugadorPrincipal
     * @param host
     */
    public ModeloJuegoImp(List<Jugador> jugadoresSecundario, Jugador jugadorPrincipal, Jugador host) {
        this.jugadoresSecundario = jugadoresSecundario;
        this.jugadorPrincipal = jugadorPrincipal;
        this.host = host;
    }

    /**
     * Método para validar un movimiento de colocación de ficha en el tablero
     *
     * @param posicion Posición del tablero donde se quiere colocar la ficha
     */
    @Override
    public void validaMovimiento(Point posicion) {
        // Se debe de obtener la tarjeta del jugador Principal 
        // obtener la carta en la posicion dada.
        // verificar la carta actual
        //si son iguales es decir la carta en la posicion actual y la carta cantada actual
        // modeloVista.colocarFicha(posicion);
        //debe de mandar un mensaje a los demás jugadores para que también se actualicé su vista
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
