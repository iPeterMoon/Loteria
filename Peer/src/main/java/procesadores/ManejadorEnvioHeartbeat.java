package procesadores;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import dtos.PeerInfo;
import enums.TipoEvento;
import network.EnvioPeer;
import util.ConfigLoader;

/**
 * ManejadorEnvioHeartbeat.java
 *
 * Clase para manejar el envio de un heartbeat, se encarga de mandarlo
 * directamente al servidor de descubrimiento.
 */
public class ManejadorEnvioHeartbeat extends ManejadorMensajesSalida {

    private final Gson gson = new Gson();

    @Override
    public void procesar(JsonObject json) {
            String tipo = json.get("tipoEvento").getAsString();

            if (TipoEvento.HEARTBEAT.name().equals(tipo)) {
                procesarHeartbeat(json);
            } else if (next != null) {
                next.procesar(json);
            }
       

    }

    /**
     * Procesa el Evento de heartbeat obteniendo la información del servidor y
     * enviandosela
     *
     * @param json Objeto que será un evento de heartbeat
     */
    private void procesarHeartbeat(JsonObject json) {
        String mensaje = gson.toJson(json);
        PeerInfo discovery = obtenerPeerDiscovery();
        EnvioPeer.getInstance().directMessage(discovery, mensaje);
    }

    /**
     * Metodo para obtener la información del servidor a través de un archivo de
     * configuracion
     *
     * @return DTO con la informacion del servidor
     */
    private PeerInfo obtenerPeerDiscovery() {
        String discoveryIp = ConfigLoader.getInstance().getIpServidor();
        int discoveryPort = ConfigLoader.getInstance().getPuertoDiscovery();
        return new PeerInfo("discovery", discoveryIp, discoveryPort);
    }

}
