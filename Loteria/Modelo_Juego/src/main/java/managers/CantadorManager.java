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
    private IPeer componentePeer;
    private Cantador cantador;
    private final long RETRASO_MS = 500; 

    public void inicializar(IPeer peer) {
        if (this.componentePeer != null) {
            return;
        }
        
        this.componentePeer = peer;
        this.cantador = Cantador.getInstance();
        cantador.addObserver(this);
    }
    
    public void iniciarCanto() {
        if (!obtenerNicknameHost().equals(obtenerNicknameJugadorPrincipal())) {
            return;
        }
        
        cantador.iniciarCanto(5000); // Falta cambiar intervalo según la dificultad
    }
    
    private void actualizarCarta() {
        int cartaActual = Cantador.getInstance().getCartaActual();
        System.out.println("Carta actual: " + cartaActual);
        ModeloJuegoFacade.getInstance().actualizarCarta(cartaActual);
    }
    
    private void enviarCarta() {
        int cartaActual = cantador.getCartaActual();
        String nicknameHost = obtenerNicknameHost();
        
        EventoCartaCantada evento = new EventoCartaCantada(nicknameHost, cartaActual);
        componentePeer.broadcastEvento(evento);
        
        System.out.println("Enviando carta cantada " + " (Carta: " + cartaActual + ") desde host [" + nicknameHost + "]");
    }

    private String obtenerNicknameHost() {
        Jugador host = Sala.getInstance().getHost();
        return host.getNickname();
    }
    
    private String obtenerNicknameJugadorPrincipal() {
        Jugador principal = Sala.getInstance().getJugadorPrincipal();
        return principal.getNickname();
    }
    
    @Override
    public void update(Object object) {
        if (object instanceof Cantador) {
            new Thread(() -> {
                try {
                    Thread.sleep(RETRASO_MS);
                    enviarCarta();
                    actualizarCarta();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }
    }
}
