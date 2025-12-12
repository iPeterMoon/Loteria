package managers;

import eventos.eventos_aplicacion.EventoFicha;
import interfaces.peer.IPeer;
import java.awt.Point;
import modelo.Cantador;
import modelo.ModeloJuegoFacade;
import modelo.Sala;
import modelo.Tarjeta;

/**
 * Clase Manager responsable de gestionar la lógica de los movimientos de juego,
 * específicamente la validación y colocación de fichas por parte del jugador
 * principal en su tarjeta.
 *
 * @author Alici
 */
public class MovimientoManager {

    /**
     * Componente para la comunicación Peer-to-Peer de la aplicación, utilizado
     * para notificar los movimientos realizados a otros jugadores.
     */
    private IPeer componentePeer;

    /**
     * Inicializa las dependencias principales del manager.
     *
     * @param peer La implementación de la interfaz IPeer.
     */
    public void inicializar(IPeer peer) {
        if (this.componentePeer != null) {
            return;
        }
        this.componentePeer = peer;
    }

    /**
     * Valida si la colocación de una ficha en la posición especificada es
     * correcta según la carta cantada actualmente.
     *
     * @param posicion Posición (coordenadas x, y) en la tarjeta donde el
     * jugador intenta colocar la ficha.
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

        // Verificar si la posición ya tiene una ficha
        if (tarjeta.getFichas() != null && Boolean.TRUE.equals(tarjeta.getFichas().get(posicion))) {
            System.out.println("Ya hay una ficha en " + posicion + ", se ignora.");
            return; // Evita volver a validar o colocar ficha en esa posición
        }

        // Validación
        if (numeroCarta == cartaActual) {
            colocarFicha(posicion, tarjeta, cartaActual);
        } else {
            System.out.println("Movimiento inválido en " + posicion + " (Carta en tarjeta: " + numeroCarta + ", Carta cantada: " + cartaActual + ")");
        }

    }

    /**
     * Registra la colocación de una ficha en la tarjeta del jugador principal,
     * notifica al modelo de juego y transmite el evento a los demás jugadores.
     *
     * @param posicion La posición de la ficha colocada.
     * @param tarjeta La tarjeta del jugador principal.
     * @param cartaActual El número de la carta que fue cantada.
     */
    private void colocarFicha(Point posicion, Tarjeta tarjeta, Integer cartaActual) {
        // Colocar ficha en la tarjeta
        tarjeta.addFicha(posicion);

        // Crear evento para notificar el movimiento
        EventoFicha eventoFicha = new EventoFicha(Sala.getInstance().getJugadorPrincipal().getNickname(), posicion);

        // Notificar al modelo de juego localmente
        ModeloJuegoFacade.getInstance().colocarFicha(eventoFicha);

        // Broadcast del evento a todos los peers
        componentePeer.broadcastEvento(eventoFicha);
        System.out.println("Ficha colocada correctamente en " + posicion + " por " + Sala.getInstance().getJugadorPrincipal().getNickname() + " (Carta: " + cartaActual + ")");
    }
}
