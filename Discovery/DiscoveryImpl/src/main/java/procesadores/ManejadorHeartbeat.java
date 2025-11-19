package procesadores;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import peerManager.ListaPeers;
import dtos.PeerInfo;
import enums.TipoEvento;
import eventos.eventos_peers.EventoHeartbeat;

public class ManejadorHeartbeat extends ManejadorMensajesLlegada{

    private final Gson gson = new Gson();
    

    @Override
    public void procesar(JsonObject json) {
       TipoEvento tipo = TipoEvento.valueOf(json.get("tipoEvento").getAsString());
        
        if(tipo.equals(TipoEvento.HEARTBEAT)){
            EventoHeartbeat heartbeat = gson.fromJson(json, EventoHeartbeat.class);
            PeerInfo peer = heartbeat.getInfo();
            
            // Llamamos al método interno
            actualizarUltimaVezVisto(peer);
            
        } else if (next != null) {
            next.procesar(json);
        }
    }

    /**
     * Metodo para actualizar la ultima vez que se vio a un peer
     * @param peer Peer a actualizar
     */
    private void actualizarUltimaVezVisto(PeerInfo peer){
        //  Obtenemos la llave (ID) del peer usando el auxiliar de la lista
        String key = ListaPeers.obtenerKey(peer);
        
        // Le avisamos al PeerCleaner que reinicie el cronómetro
        PeerCleaner.actualizarUltimaVezVisto(key);
    }
}