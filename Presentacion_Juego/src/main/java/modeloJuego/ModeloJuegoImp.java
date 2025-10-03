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
     * instancia unica de {@link ModekoJuegoImp}
     */
    private static ModeloJuegoImp modeloJuegoImp;

    /**
     * obtiene la instacnia unica de {@link ModeloJuegoImp}
     * si la instancia aun no ha sido creada, se inicializa en el momento
     * si ya existe, simplemente se devuelve la misma referencia
     * @return la unica instancia de {@code ModeloJuegoImp}
     */
    public static ModeloJuegoImp getInstance() {
        if (modeloJuegoImp == null) {
            modeloJuegoImp = new ModeloJuegoImp();
        }
        return modeloJuegoImp;
    }

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
    private ModeloJuegoImp() {
    }

    public Jugador getJugadorPrincipal() {
        return jugadorPrincipal;
    }

    public void setJugadorPrincipal(Jugador jugadorPrincipal) {
        this.jugadorPrincipal = jugadorPrincipal;
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
     * Valida un movimiento de colocación de ficha en la tarjeta del jugador
     * principal.
     *
     * 1. Obtiene la carta en la posición seleccionada. 2. Compara con la carta
     * actual cantada por el {@link Cantador}. 3. Si coinciden: - Coloca una
     * ficha en la tarjeta del jugador. - Crea un {@link FichaDTO} con la
     * posición y el nickname del jugador. - Notifica a la vista para que
     * actualice la interfaz.
     *
     * @param posicion Posición en la tarjeta donde el jugador intenta colocar
     * la ficha.
     */
    @Override
    public void validaMovimiento(Point posicion) {
        if (jugadorPrincipal == null || jugadorPrincipal.getTarjeta() == null) {
            return; // No hay jugador principal o tarjeta asignada
        }

        Tarjeta tarjeta = jugadorPrincipal.getTarjeta();
        Integer numeroCarta = tarjeta.getCartas().get(posicion);

        if (numeroCarta == null) {
            return; // La posición no corresponde a ninguna carta en la tarjeta
        }

        // Carta actual del cantador (singleton)
        Cantador cantador = Cantador.getInstance();
        int cartaActual = cantador.getCartaActual();

        // Validación
        if (numeroCarta == cartaActual) {
            // Colocar ficha en la tarjeta
            tarjeta.addFicha(posicion);

            // Crear DTO y notificar a la vista
            FichaDTO ficha = new FichaDTO(jugadorPrincipal.getNickname(), posicion);
            vista.colocarFicha(ficha);

            // Print temporal 
            System.out.println("Ficha colocada correctamente en " + posicion + " por " + jugadorPrincipal.getNickname() + " (Carta: " + cartaActual + ")");
        } else {
            // Print temporal para movimientos inválidos
            System.out.println("Movimiento inválido en " + posicion + " (Carta en tarjeta: " + numeroCarta + ", Carta cantada: " + cartaActual + ")");
        }

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
