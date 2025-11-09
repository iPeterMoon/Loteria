package procesadorEventos;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import network.EnvioPeer;
import peer.PeersConectados;

public class ManejadorBroadcast extends ManejadorMensajes {

    private final Gson gson = new Gson();

    @Override
    public void procesar(JsonObject json) {
        if(next == null){
            String evento = gson.toJson(json);
            broadcast(evento);   
        }
    }

    private void broadcast(String evento) {
        PeersConectados peers = PeersConectados.getInstance();
        EnvioPeer envio = EnvioPeer.getInstance();
        peers.ejecutarEnTodos(evento, envio::directMessage);
    }
    
}
