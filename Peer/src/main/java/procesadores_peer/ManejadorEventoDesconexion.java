package procesadores_peer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dtos.peer.PeerInfo;
import enums.TipoEvento;
import eventos.eventos_peers.EventoPeerDesconectado;

import java.util.Collection;
import java.util.Comparator;
import peer.Peer;
import peer.PeersConectados;

/**
 * Manejador que procesa el evento de desconexión de un Peer. Ejecuta la lógica
 * de elección de nuevo Host (Host Migration).
 *
 * @author rocha
 */
public class ManejadorEventoDesconexion extends ManejadorMensajesLlegada {

    private final Gson gson = new Gson();

    @Override
    public void procesar(JsonObject json) {
        // Validación de existencia de tipoEvento
        if (!json.has("tipoEvento")) {
            if (next != null) {
                next.procesar(json);
            }
            return;
        }

        String tipo = json.get("tipoEvento").getAsString();

        if (TipoEvento.PEER_DESCONECTADO.name().equals(tipo)) {
            procesarDesconexion(json);
        } else if (next != null) {
            next.procesar(json);
        }
    }

    private void procesarDesconexion(JsonObject json) {
        PeersConectados peersConectados = PeersConectados.getInstance();

        try {
            // Gson mapea el JSON completo al DTO EventoPeerDesconectado
            EventoPeerDesconectado evento = gson.fromJson(json, EventoPeerDesconectado.class);
            PeerInfo peerDesconectado = evento.getPeerDesconectado();

            peersConectados.eliminarPeer(peerDesconectado);
            
            //Pasar al siguiente procesador para que llegue a la lógica de aplicación.
            next.procesar(json);
        } catch (Exception e) {
            // Manejo de errores de JSON o lógica de PeersConectados
            System.err.println("[ManejadorDesconexion] Error al procesar evento: " + e.getMessage());
        }

    }

}
