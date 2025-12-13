package network;

import com.google.gson.Gson;
import dtos.peer.PeerInfo;
import eventos.eventos_peers.EventoNuevoPeer;
import factory.RedFactory;
import util.ConfigLoader;

/**
 *
 * @author Jp
 */
public class DiscoveryRegistrar {

    /**
     * Dirección IP del servidor de discovery.
     */
    private final static String DISCOVERY_IP = ConfigLoader.getInstance().getIpServidor();
    
    /**
     * Puerto del servidor de discovery.
     */
    private final static int DISCOVERY_PORT = ConfigLoader.getInstance().getPuertoDiscovery(); 

    /**
     * Registra un usuario en el servidor de discovery
     * @param myInfo Info del usuario.
     */
    public static void registrar(PeerInfo myInfo) {
        EventoNuevoPeer evento = new EventoNuevoPeer(myInfo, myInfo.getUser());
        RedFactory.crearEnvioHandler().sendEvent(DISCOVERY_IP, DISCOVERY_PORT, new Gson().toJson(evento));
    }
}
