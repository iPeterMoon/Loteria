package peer;

import com.google.gson.Gson;

import dtos.PeerInfo;
import eventos.EventoHeartbeat;
import factory.RedFactory;
import interfaces.IEnvio;

public class ProcesadorHeartbeat {

    private static final IEnvio envio = RedFactory.crearEnvioHandler();
    private static final Gson gson = new Gson();

    public static void procesarHeartbeat(PeerInfo discovery, PeerInfo myInfo){
        EventoHeartbeat heartbeat = new EventoHeartbeat(myInfo, myInfo.getUser());
        String mensaje = gson.toJson(heartbeat);
        envio.sendEvent(discovery.getIp(), discovery.getPort(), mensaje);
        System.out.println("Respondiendo el Heartbeat al discovery");
    }
}