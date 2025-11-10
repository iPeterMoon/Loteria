package procesadores;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import discovery.Discovery;
import discovery.ListaPeers;
import dtos.PeerInfo;
import enums.TipoEvento;
import eventos.EventoNuevoPeer;

public class ManejadorNuevoPeer extends ManejadorMensajes{

    private final Gson gson = new Gson();
    private PeerInfo nuevoPeer;

    @Override
    public void procesar(JsonObject json) {
        TipoEvento tipo = TipoEvento.valueOf(json.get("tipoEvento").getAsString());
        if(tipo.equals(TipoEvento.NUEVO_PEER)){
            EventoNuevoPeer evento = gson.fromJson(json, EventoNuevoPeer.class);
            nuevoPeer = evento.getPeer();
            enviarPeersANuevo();
            enviarNuevoAPeers();
            ListaPeers.registrarPeer(nuevoPeer);
        } else if (next != null) {
            next.procesar(json);
        }
    }

    /**
     * Metodo para enviar los peers ya registrados al nuevo peer
     */
    private void enviarPeersANuevo(){
        ListaPeers.ejecutarEnPeersVivos((key, peer) -> {
            String mensaje = generarEvento(peer);
            enviarEvento(nuevoPeer, mensaje);
        });
    }
    
    /**
     * Metodo para generar un eventoNuevoPeer a partir de un peer
     * @param peer Peer Nuevo
     * @return String representanndo el mensaje del evento
     */
    private String generarEvento(PeerInfo peer){
        EventoNuevoPeer nuevoPeer = new EventoNuevoPeer(peer, peer.getUser());
        return gson.toJson(nuevoPeer);
    }

    /**
     * Metodo para enviar un evento a un peer
     * @param peer Peer a enviar el mensaje
     * @param mensaje Mensaje a enviar
     */
    private void enviarEvento(PeerInfo peer, String mensaje){
        Discovery discovery = Discovery.getInstance();
        discovery.directMessage(peer, mensaje);
    }

    /**
     * Metodo para enviar el nuevo Peer a los peers existentes
     */
    private void enviarNuevoAPeers(){
        String mensaje = generarEvento(nuevoPeer);            
        ListaPeers.ejecutarEnPeersVivos((key,peer) -> {
            enviarEvento(peer, mensaje);
        });
    }

}
