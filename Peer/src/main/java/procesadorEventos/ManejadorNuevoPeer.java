/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package procesadorEventos;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import dtos.PeerInfo;

import static enums.TipoEvento.NUEVO_PEER;
import eventos.eventos_peers.EventoNuevoPeer;
import peer.Peer;
import peer.PeersConectados;

/**
 * Manejador que procesa el evento de conexión de nuevo peer.
 * Si el evento no corresponde, lo pasa al siguiente manejador.
 * 
 * @author rocha
 */
public class ManejadorNuevoPeer extends ManejadorMensajesLlegada {

    private final Gson gson = new Gson();
    private PeerInfo nuevoPeer;
    
    /**
     * Procesa el evento recibido. Si es de tipo NUEVO_PEER,
     * lo convierte en objeto y lo registra mediante ProcesadorConexiones.
     * En caso contrario, se pasa al siguiente manejador.
     * 
     * @param json evento en formato JSON
     */
    @Override
    public void procesar(JsonObject json) {
        String tipo = json.get("tipoEvento").getAsString();

        if (NUEVO_PEER.name().equals(tipo)) {
            procesarNuevoPeer(json);
        } else if (next != null) {
            next.procesar(json);
        }
    }
    
    /**
     * Convierte el evento en objeto y registra el nuevo peer.
     * 
     * @param json evento JSON de tipo NUEVO_PEER
     */
    private void procesarNuevoPeer(JsonObject json) {
        EventoNuevoPeer evento = gson.fromJson(json, EventoNuevoPeer.class);
        nuevoPeer = evento.getPeer();
        enviarPeersANuevo();
        enviarNuevoAPeers();
        PeersConectados peers = PeersConectados.getInstance();
        peers.registrarPeer(nuevoPeer);
    }
    
    /**
     * Metodo para enviar los peers ya registrados al nuevo peer
     */
    private void enviarPeersANuevo(){
        if (nuevoPeer == null || nuevoPeer.getUser() == null) {
            return;
        }
        
        PeersConectados peers = PeersConectados.getInstance();
        Peer peer = Peer.getInstance();
        String userSender = peer.getMyInfo().getUser();
        
        for (PeerInfo peerExistente : peers.obtenerTodosLosPeers()) {
            EventoNuevoPeer evento = new EventoNuevoPeer(peerExistente, userSender);
            peer.directMessage(evento, nuevoPeer.getUser());
        }
    }
    
    /**
     * Metodo para enviar el nuevo Peer a los peers existentes
     */
    private void enviarNuevoAPeers(){
        if (nuevoPeer == null || nuevoPeer.getUser() == null) {
            return;
        }
        
        PeersConectados peers = PeersConectados.getInstance();
        Peer peer = Peer.getInstance();
        String userSender = peer.getMyInfo().getUser();
        
        EventoNuevoPeer evento = new EventoNuevoPeer(nuevoPeer, userSender);
        for (PeerInfo peerExistente : peers.obtenerTodosLosPeers()) {
            if (peerExistente.getUser() != null) {
                peer.directMessage(evento, peerExistente.getUser());
            }
        }
    }
}
