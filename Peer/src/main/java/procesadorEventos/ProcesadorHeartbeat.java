package procesadorEventos;

import com.google.gson.Gson;

import dtos.PeerInfo;
import eventos.EventoHeartbeat;
import factory.RedFactory;
import interfaces.IEnvio;

public class ProcesadorHeartbeat {

    private static final Gson gson = new Gson();
    private static final IEnvio envio = RedFactory.crearEnvioHandler();

    public static void responderHeartbeat(PeerInfo myInfo) {
        EventoHeartbeat eventoHeartbeat = new EventoHeartbeat(myInfo, myInfo.getUser());
        String json = gson.toJson(eventoHeartbeat);
        String ip = util.ConfigLoader.getInstance().getIpServidor();
        int port = util.ConfigLoader.getInstance().getPuertoDiscovery();
        envio.sendEvent(ip, port, json);
        System.out.println("Heartbeat enviado a discovery");
    }
}
