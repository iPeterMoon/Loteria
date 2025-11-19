package procesadores;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import mensajes.TipoMensaje;
import network.EnvioPeer;
import peer.PeersConectados;

/**
 * Manejador para procesar mensajes de broadcast. Se encarga de enviar mensajes
 * a todos los peers conectados.
 */
public class ManejadorBroadcast extends ManejadorMensajesSalida {

    private final Gson gson = new Gson();

    @Override
    public void procesar(JsonObject json) {
        if(json.has("tipoMensaje")){
            String tipoMensaje = json.get("tipoMensaje").getAsString();

            if (TipoMensaje.BROADCAST.name().equals(tipoMensaje)) {
                String evento = extraerEvento(json);
                broadcast(evento);
            } else if (next != null) {
                next.procesar(json);
            }

        } else if (next != null) {
            next.procesar(json);
        }
    }

    /**
     * Envía un evento a todos los peers (broadcast).
     * @param evento Evento a mandar.
     */
    private void broadcast(String evento) {
        PeersConectados peers = PeersConectados.getInstance();
        EnvioPeer envio = EnvioPeer.getInstance();
        peers.ejecutarEnTodos(evento, envio::directMessage);
    }
    
    /**
     * Procesa un mensaje directo, obteniendo el usuario de destino y enviando
     * el mensaje al peer correspondiente.
     *
     * @param json Objeto JSON que contiene el mensaje directo.
     */
    private String extraerEvento(JsonObject json){
        JsonObject eventoJson = json.getAsJsonObject("evento");
        String evento = gson.toJson(eventoJson);
        return evento;
    }

}
