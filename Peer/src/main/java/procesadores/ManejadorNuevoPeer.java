package procesadores;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import dtos.PeerInfo;

import static enums.TipoEvento.NUEVO_PEER;
import eventos.eventos_peers.EventoNuevoPeer;
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
     * lo convierte en objeto y lo registra.
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
     * Convierte el evento en objeto y registra el nuevo peer
     * 
     * @param json evento JSON de tipo NUEVO_PEER
     */
    private void procesarNuevoPeer(JsonObject json) {
        EventoNuevoPeer evento = gson.fromJson(json, EventoNuevoPeer.class);
        nuevoPeer = evento.getPeer();
        
        // Solo registrar el peer localmente
        PeersConectados peers = PeersConectados.getInstance();
        peers.registrarPeer(nuevoPeer);
    }
}

