package procesadorEventos;

import com.google.gson.Gson;

import dtos.PeerInfo;
import eventos.EventoHeartbeat;
import factory.RedFactory;
import interfaces.IEnvio;
import peer.Peer;

public class ProcesadorHeartbeat {

    private static final Gson gson = new Gson();
    private static final IEnvio envio = RedFactory.crearEnvioHandler();
    
    private static final String DISCOVERY_IP = util.ConfigLoader.getInstance().getIpServidor();
    private static final int DISCOVERY_PORT = util.ConfigLoader.getInstance().getPuertoDiscovery();

    public static void responderHeartbeat() {
        PeerInfo myInfo = getMyInfo();
        
        EventoHeartbeat eventoHeartbeat = new EventoHeartbeat(myInfo, myInfo.getUser());
        String json = gson.toJson(eventoHeartbeat);
        envio.sendEvent(DISCOVERY_IP, DISCOVERY_PORT, json);
    }
    
    private static PeerInfo getMyInfo(){
        return Peer.getInstance().getMyInfo();
    }
}
