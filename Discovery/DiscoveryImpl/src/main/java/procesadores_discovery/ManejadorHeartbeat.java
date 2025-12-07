package procesadores_discovery;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import peerManager.PeerCleaner;
import dtos.peer.PeerInfo;
import enums.TipoEvento;
import eventos.eventos_peers.EventoHeartbeat;

public class ManejadorHeartbeat extends ManejadorMensajesLlegada{

    private final Gson gson = new Gson();
    

    @Override
    public void procesar(JsonObject json) {
       TipoEvento tipo = TipoEvento.valueOf(json.get("tipoEvento").getAsString());
        
        if(tipo.equals(TipoEvento.HEARTBEAT)){
            procesarHeartbeat(json);    
        } else if (next != null) {
            next.procesar(json);
        }
    }

    /**
     * Metodo para procesar un heartbeat en caso de que sea la responsabilidad de este eslabon.
     * Actualiza la ultima vez que se vio el Peer en el PeerCleaner.
     * @param json JsonObject con los datos del evento.
     */
    private void procesarHeartbeat(JsonObject json){
        EventoHeartbeat heartbeat = gson.fromJson(json, EventoHeartbeat.class);
        PeerInfo peer = heartbeat.getInfo();
        
        PeerCleaner.actualizarUltimaVezVisto(peer);
            
    }
}