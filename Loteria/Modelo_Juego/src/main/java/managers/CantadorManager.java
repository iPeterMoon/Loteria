
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package managers;

import eventos.eventos_aplicacion.EventoCartaCantada;
import eventos.eventos_aplicacion.EventoFinRonda;
import util.IObserver;
import interfaces.peer.IPeer;
import javax.swing.SwingUtilities;
import modelo.Cantador;
import modelo.ModeloJuegoFacade;
import modelo.Sala;

/**
 * Manejador para el cantador del modelo.
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
        Sala sala = Sala.getInstance();
        if(sala.getHost() == null || sala.getNicknameJugadorPrincipal() == null){
            return;
        }
        
        if (!sala.getHost().equals(sala.getNicknameJugadorPrincipal())) {
            return;
        }

        // Obtiene el intervalo de la configuración
        int intervalo = sala.getConfiguracion().getDificultad().getIntervalo();
        cantador.iniciarCanto(intervalo);
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
        int cartaActual = cantador.getCartaActual();
        ModeloJuegoFacade.getInstance().actualizarCarta(cartaActual);
    }

    /**
     * Envía el evento de carta cantada a todos los demás peers.
     */
    private void enviarCarta() {
        int cartaActual = cantador.getCartaActual();
        String nicknameHost = Sala.getInstance().getHost();

        // Enviar evento a otros peers
        EventoCartaCantada evento = new EventoCartaCantada(nicknameHost, cartaActual);
        componentePeer.broadcastEvento(evento);
        System.out.println("Enviando carta cantada " + "(Carta: " + cartaActual + ") desde host [" + nicknameHost + "]");
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
                if(isHost()){
                    EventoFinRonda evento = new EventoFinRonda(Sala.getInstance().getHost(), "SE ACABARON LAS CARTAS");
                    componentePeer.broadcastEvento(evento);
                    ModeloJuegoFacade.getInstance().finalizarRonda("Se acabaron las cartas");
                    return;
                }
            }
            
            new Thread(() -> {
                try {
                    enviarCarta();
                    Thread.sleep(RETRASO_MS);
                    
                    SwingUtilities.invokeLater(() -> {
                        actualizarCarta();
                    });
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }
    }
    
    private boolean isHost(){
        Sala sala = Sala.getInstance();
        String jugador = sala.getJugadorPrincipal() != null ? sala.getJugadorPrincipal().getNickname() : "";
        String host = sala.getHost();
        
        if(host == null) return false;
        return jugador.equals(host);
    }
}
