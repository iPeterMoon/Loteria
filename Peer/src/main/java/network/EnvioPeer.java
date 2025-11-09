package network;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import dtos.PeerInfo;
import factory.RedFactory;
import interfaces.IEnvio;
import procesadorEventos.ManejadorBroadcast;
import procesadorEventos.ManejadorEnvioHeartbeat;
import procesadorEventos.ManejadorMensajes;

/**
 *
 * @author Jp
 */
public class EnvioPeer implements Runnable {

    private static EnvioPeer instance;

    private final ManejadorMensajes manejadorPrincipal;
    private final Gson gson = new Gson();

    private EnvioPeer() {
        this.envio = RedFactory.crearEnvioHandler();

        ManejadorMensajes envioHeartbeat = new ManejadorEnvioHeartbeat();
        ManejadorMensajes broadcast = new ManejadorBroadcast();

        envioHeartbeat.setNext(broadcast);

        this.manejadorPrincipal=envioHeartbeat;
    }

    public static EnvioPeer getInstance() {
        if (instance == null) {
            instance = new EnvioPeer();
        }
        return instance;
    }

    private final IEnvio envio;
    private volatile boolean isRunning = false;

    @Override
    public void run() {
        start();
        while (isRunning) {
            try {
                String evento = OutgoingMessageDispatcher.take();
                procesar(evento);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
    
    private void start() {
        if (isRunning) {
            return;
        }
        envio.startClient();
        isRunning = true;
        new Thread(this, "EnvioHandler-Thread").start();
    }

    private void procesar(String evento){
        try{
            JsonObject json = gson.fromJson(evento, JsonObject.class);
            manejadorPrincipal.procesar(json);
        } catch(JsonSyntaxException e){
            System.err.println("[Processor] JSON inválido: " + e.getMessage());
        }
    }
    
    public void directMessage( PeerInfo peer, String mensaje){
        envio.sendEvent(peer.getIp(), peer.getPort(), mensaje);
    }

    public void stop() {
        isRunning = false;
        envio.stop();
    }
}
