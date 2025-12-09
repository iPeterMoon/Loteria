
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package managers;

import eventos.eventos_aplicacion.EventoCartaCantada;
import interfaces.IObserver;
import interfaces.IPeer;
import modelo.Cantador;
import modelo.Jugador;
import modelo.ModeloJuegoFacade;
import modelo.Sala;

/**
 *
 * @author rocha
 */
public class CantadorManager implements IObserver {

    /**
     * Componente peer para el envío de eventos.
     */
    private IPeer componentePeer;

    /**
     * Cantador del modelo.
     */
    private Cantador cantador;

    /**
     * Retraso de tiempo en milisegundos para manejar la ventaja del host.
     */
    private final long RETRASO_MS = 100;

    public void inicializar(IPeer peer) {
        if (this.componentePeer != null) {
            return;
        }

        this.componentePeer = peer;
        this.cantador = Cantador.getInstance();

        cantador.addObserver(this);
    }

    /**
     * Inicia el canto del cantador en el modelo del jugador que es host.
     */
    public void iniciarCanto() {
        if (!obtenerNicknameHost().equals(obtenerNicknameJugadorPrincipal())) {
            return;
        }

        cantador.iniciarCanto(5000); // Intervalo mock, falta cambiarlo según la dificultad
    }
    
    /**
     * Detiene el canto del cantador en el modelo del jugador que es host.
     */
    public void detenerCantador() {
        if (cantador != null) {
            cantador.detenerCanto();
        }
    }

    /**
     * Actualiza la carta de la vista mediante la fachada.
     */
    private void actualizarCarta() {
        int cartaActual = Cantador.getInstance().getCartaActual();
        ModeloJuegoFacade.getInstance().actualizarCarta(cartaActual);
    }

    /**
     * Envía el evento de carta cantada a todos los demás peers.
     */
    private void enviarCarta() {
        int cartaActual = cantador.getCartaActual();
        String nicknameHost = obtenerNicknameHost();

        // Enviar evento a otros peers
        EventoCartaCantada evento = new EventoCartaCantada(nicknameHost, cartaActual);
        componentePeer.broadcastEvento(evento);
        System.out.println("Enviando carta cantada " + "(Carta: " + cartaActual + ") desde host [" + nicknameHost + "]");
    }

    private String obtenerNicknameHost() { // Talvez cambiar a sala
        String host = Sala.getInstance().getHost();
        return host;
    }

    private String obtenerNicknameJugadorPrincipal() { // Talvez cambiar a sala
        Jugador principal = Sala.getInstance().getJugadorPrincipal();
        return principal.getNickname();
    }

    /**
     * Escucha al cantador cada que canta una carta.
     *
     * @param object objeto Cantador que notificó.
     */
    @Override
    public void update(Object object) {
        if (object instanceof Cantador) {
            if (!cantador.isEnEjecucion() && cantador.getMazo().isEmpty()) {
                System.out.println("SE ACABARON LAS CARTAS");
                ModeloJuegoFacade.getInstance().finalizarRonda("Se acabaron las cartas");
                return;
            }
            
            new Thread(() -> {
                try {
                    enviarCarta();
                    Thread.sleep(RETRASO_MS);
                    actualizarCarta();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }
    }
}
