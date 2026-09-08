package procesadores_peer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import dtos.peer.PeerInfo;
import network.EnvioPeer;
import mensajes.TipoMensaje;
import peer.PeersConectados;

/**
 * Manejador para procesar mensajes directos. Se encarga de enviar mensajes a un
 * peer específico.
 *
 * @author norma
 */
public class ManejadorMensajeDirecto extends ManejadorMensajesSalida {

    /**
     * Serializador JSON.
     */
    private final Gson gson = new Gson();

    /**
     * Procesa el evento recibido en formato JSON.
     * Si el manejador actual no reconoce el tipo de evento, debe delegar la solicitud al siguiente manejador.
     * 
     * @param json objeto que contiene los datos del evento a procesar.
     */
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

        if (peerDestino == null) {
            reportarDestinatarioDesconocido(json);
            return;
        }

        EnvioPeer.getInstance().directMessage(peerDestino, evento);
    }

    /**
     * Informa que no se encontró al destinatario e imprime los peers que sí
     * se conocen, para poder diagnosticar por qué no llegó el registro.
     *
     * @param json Json con el formato de MensajeDirecto.java
     */
    private void reportarDestinatarioDesconocido(JsonObject json) {
        String user = json.get("user").getAsString();
        StringBuilder conocidos = new StringBuilder();

        for (PeerInfo peer : PeersConectados.getInstance().obtenerTodosLosPeers()) {
            conocidos.append("\n\t- ")
                    .append(peer.getUser())
                    .append("@")
                    .append(peer.getIp())
                    .append(":")
                    .append(peer.getPort());
        }

        if (conocidos.length() == 0) {
            conocidos.append("\n\t(ninguno: este peer nunca recibió respuesta del discovery)");
        }

        System.err.println("[ManejadorMensajeDirecto] No se encontró al peer '" + user
                + "'. Peers conocidos:" + conocidos);
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
        String user = json.get("user").getAsString();
        PeersConectados peers = PeersConectados.getInstance();
        PeerInfo peerDestino = peers.obtenerPeerPorUsuario(user);
        return peerDestino;
    }
}
