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
            actualizarUltimaVezVisto(peer);
        } else if (next!= null) {
            next.procesar(json);
        }
    }

    /**
     * Metodo para actualizar la ultima vez que se vio a un peer
     * @param peer Peer a actualizar
     */
    private void actualizarUltimaVezVisto(PeerInfo peer){
        ListaPeers.setUltimaVezVisto(peer);
    }

}
