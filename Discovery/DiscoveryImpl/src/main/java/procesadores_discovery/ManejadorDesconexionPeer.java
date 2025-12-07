/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package procesadores_discovery;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import peerManager.ListaPeers;
import dtos.peer.PeerInfo;
import enums.TipoEvento;
import eventos.eventos_peers.EventoDesconexion; 
import network.Envio;

/**
 *
 * @author Alici
 */
public class ManejadorDesconexionPeer extends ManejadorMensajesSalida {
private final Gson gson = new Gson();
    private PeerInfo peerCaido;

    @Override
    public void procesar(JsonObject json) {
        TipoEvento tipo = TipoEvento.valueOf(json.get("tipoEvento").getAsString());
        
        if (tipo.equals(TipoEvento.PEER_DESCONECTADO)) {
            // Deserializamos para obtener la info del caído
            EventoDesconexion evento = gson.fromJson(json, EventoDesconexion.class);
            peerCaido = evento.getPeerCaido(); // o getPeer() según tu clase evento
            
            // Solo hacemos broadcast (avisar a los demás)
            enviarDesconexionAPeers();
                        
        } else if (next != null) {
            next.procesar(json);
        }
    }

    /**
     * Metodo para enviar el aviso de desconexión a los peers existentes
     */
    private void enviarDesconexionAPeers() {
        // Generamos el JSON del evento una sola vez
        String mensaje = generarEvento(peerCaido);
        
        ListaPeers.ejecutarEnPeersVivos((key, peer) -> {
            // Enviamos a todos 
            enviarEvento(peer, mensaje);
        });
    }

    /**
     * Metodo para generar un EventoDesconexion a partir de un peer
     *
     * @param peer Peer Caído
     * @return String representando el mensaje del evento en JSON
     */
    private String generarEvento(PeerInfo peer) {
        // Creamos el evento para serializarlo correctamente
        EventoDesconexion evento = new EventoDesconexion(peer);
        return gson.toJson(evento);
    }

    /**
     * Metodo para enviar un evento a un peer usando el Singleton
     *
     * @param peer Peer a enviar el mensaje
     * @param mensaje Mensaje a enviar
     */
    private void enviarEvento(PeerInfo peer, String mensaje) {
        Envio envio = Envio.getInstance();
        envio.directMessage(peer, mensaje);
    }
}