package conexion;

import java.util.concurrent.ConcurrentHashMap;

import dtos.FichaDTO;
import dtos.JugadorDTO;

/**
 * PeerService.java
 * Clase Singleton que actúa como la interfaz central para enviar y recibir
 * mensajes
 * del juego. Mantiene un registro de todos los PeerHandlers activos.
 * 
 * @author pedro
 */
public class PeerService {
    private static PeerService instancia;

    // Almacena todas las conexiones TCP salientes (somos cliente)
    private final ConcurrentHashMap<String, PeerHandler> handlersSalientes;
    
    // Almacena todas las conexiones TCP entrantes (somos servidor)
    private final ConcurrentHashMap<String, PeerHandler> handlersEntrantes;
    
    private PeerService() {
        this.handlersSalientes = new ConcurrentHashMap<>();
        this.handlersEntrantes = new ConcurrentHashMap<>();
    }

    public static synchronized PeerService getInstancia() {
        if (instancia == null) {
            instancia = new PeerService();
        }
        return instancia;
    }
    /**
     * Añade un PeerHandler de una conexión que nosotros iniciamos (saliente, Receiver).
     */
    public void agregarHandlerSaliente(String peerID, PeerHandler handler) {
        handlersSalientes.put(peerID, handler);
    }
    
    /**
     * Añade un PeerHandler de una conexión que otro peer inició (entrante, ServidorTCP).
     */
    public void agregarHandlerEntrante(String peerID, PeerHandler handler) {
        handlersEntrantes.put(peerID, handler);
    }
    
    /**
     * Elimina un PeerHandler de la lista (ej. al desconectarse).
     */
    public void removerHandler(String peerID) {
        handlersSalientes.remove(peerID);
        handlersEntrantes.remove(peerID);
    }

    public void broadcastActualizarTarjeta(FichaDTO ficha){
        // Enviar a conexiones que iniciamos (Salientes)
        handlersSalientes.values().forEach(handler -> handler.sendObject(ficha));
        
        // Enviar a conexiones que aceptamos (Entrantes)
        handlersEntrantes.values().forEach(handler -> handler.sendObject(ficha));
        
        System.out.println("[SERVICE] Broadcast enviado: " + ficha.toString());
    }
    
    public void broadcastMandarJugador(JugadorDTO jugador) {
        // Enviar a conexiones que iniciamos (Salientes)
        handlersSalientes.values().forEach(handler -> handler.sendObject(jugador));
        
        // Enviar a conexiones que aceptamos (Entrantes)
        handlersEntrantes.values().forEach(handler -> handler.sendObject(jugador));
        
        System.out.println("[SERVICE] Broadcast enviado: " + jugador.toString());
    }

}
