package procesadores_peer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import dtos.PeerInfo;
import network.EnvioPeer;
import mensajes.TipoMensaje;
import peer.PeersConectados;
import util.ConfigLoader;

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
        if(json.has("tipoMensaje")){
            String tipoMensaje = json.get("tipoMensaje").getAsString();
    
            if (TipoMensaje.DIRECTO.name().equals(tipoMensaje)) {
                procesarMensajeDirecto(json);
            } else if (next != null) {
                next.procesar(json);
            }

        } else if (next != null){
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
        PeerInfo peerDestino = extraerPeerDestinatario(json);
        String evento = extraerEvento(json);
        EnvioPeer.getInstance().directMessage(peerDestino, evento);
    }
    
    /**
     * Extrae el evento de un json con formato de la clase Mensaje
     * @param json Json con la estructura de la clase mensaje
     * @return String (json) con el formato del evento
     */
    private String extraerEvento(JsonObject json){
        JsonObject eventoJson = json.getAsJsonObject("evento");
        String evento = gson.toJson(eventoJson);
        return evento;
    }
    
    /**
     * Extrae el Peer destinatario de un json con el formato de MensajeDirecto.java
     * @param json Json con el formato de MensajeDirecto.java
     * @return DTO con la información del PeerDestinatario
     */
    private PeerInfo extraerPeerDestinatario(JsonObject json) {
        String user = json.get("userReceiver").getAsString();
        PeersConectados peers = PeersConectados.getInstance();
        PeerInfo peerDestino = peers.obtenerPeerPorUsuario(user);
        return peerDestino;
    }
}
