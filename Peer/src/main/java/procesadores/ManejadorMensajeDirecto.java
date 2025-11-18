package procesadorEventos;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import dtos.PeerInfo;
import mensajes.TipoMensaje;
import network.EnvioPeer;
import peer.PeersConectados;

/**
 * Manejador para procesar mensajes directos. Se encarga de enviar mensajes a un
 * peer específico.
 *
 * @author norma
 */
public class ManejadorMensajeDirecto extends ManejadorMensajesSalida {

    private final Gson gson = new Gson();

    @Override
    public void procesar(JsonObject json) {
        if (json.has("tipoMensaje")) {
            String tipoMensaje = json.get("tipoMensaje").getAsString();

            if (TipoMensaje.DIRECTO.name().equals(tipoMensaje)) {
                procesarMensajeDirecto(json);
            } else if (next != null) {
                next.procesar(json);
            }
        } else if (next != null) {
            next.procesar(json);
        }
    }

    /**
     * Procesa un mensaje directo, obteniendo el usuario de destino y enviando
     * el mensaje al peer correspondiente.
     *
     * @param json Objeto JSON que contiene el mensaje directo.
     */
    private void procesarMensajeDirecto(JsonObject json) {
        String user = json.get("user").getAsString();

        PeersConectados peers = PeersConectados.getInstance();
        PeerInfo peerDestino = peers.obtenerPeerPorUsuario(user);

        String mensaje = gson.toJson(json);

        EnvioPeer.getInstance().directMessage(peerDestino, mensaje);
    }
}
