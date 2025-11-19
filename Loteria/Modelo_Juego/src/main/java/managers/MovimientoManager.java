package managers;

import eventos.eventos_aplicacion.EventoFicha;
import interfaces.IPeer;
import java.awt.Point;
import modelo.Cantador;
import modelo.ModeloJuegoFacade;
import modelo.Sala;
import modelo.Tarjeta;

/**
 *
 * @author Alici
 */
public class MovimientoManager {

    private IPeer componentePeer;

    public void inicializar(IPeer peer) {
        if (this.componentePeer != null) {
            return;
        }
        this.componentePeer = peer;
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
    public void validaMovimiento(Point posicion) {
        Sala sala = Sala.getInstance();
        if (sala.getJugadorPrincipal() == null || sala.getJugadorPrincipal().getTarjeta() == null) {
            return; // No hay jugador principal o tarjeta asignada
        }

        Tarjeta tarjeta = sala.getJugadorPrincipal().getTarjeta();
        Integer numeroCarta = tarjeta.getCartas().get(posicion);

        if (numeroCarta == null) {
            return; // La posición no corresponde a ninguna carta en la tarjeta
        }

        // Carta actual del cantador (singleton)
        Cantador cantador = Cantador.getInstance();
        int cartaActual = cantador.getCartaActual();

        //Verificar si la posición ya tiene una ficha
        if (tarjeta.getFichas() != null && Boolean.TRUE.equals(tarjeta.getFichas().get(posicion))) {
            System.out.println("Ya hay una ficha en " + posicion + ", se ignora.");
            return; // Evita volver a validar o colocar ficha en esa posición
        }

        // Validación
        if (numeroCarta == cartaActual) {
            colocarFicha(posicion, tarjeta, cartaActual);
        } else {
            // Print temporal para movimientos inválidos
            System.out.println("Movimiento inválido en " + posicion + " (Carta en tarjeta: " + numeroCarta + ", Carta cantada: " + cartaActual + ")");
        }

    }

    private void colocarFicha(Point posicion, Tarjeta tarjeta, Integer cartaActual) {
        // Colocar ficha en la tarjeta
        tarjeta.addFicha(posicion);

        EventoFicha eventoFicha = new EventoFicha(Sala.getInstance().getJugadorPrincipal().getNickname(), posicion);

        ModeloJuegoFacade.getInstance().colocarFicha(eventoFicha);

        componentePeer.broadcastEvento(eventoFicha);
        // Print temporal 
        System.out.println("Ficha colocada correctamente en " + posicion + " por " + Sala.getInstance().getJugadorPrincipal().getNickname() + " (Carta: " + cartaActual + ")");
    }
}
