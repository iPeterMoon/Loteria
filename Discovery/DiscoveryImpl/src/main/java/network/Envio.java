/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package network;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import dtos.PeerInfo;
import factory.RedFactory;
import interfaces.IEnvio;
import java.util.concurrent.ExecutorService;
import procesadores.ManejadorDesconexionPeer;
import procesadores.ProcesadorMensajesSalida;
import util_discovery.PoolHilos;

/**
 *
 * @author Alici
 */
public class Envio {

    private final IEnvio envio;
    private static Envio instance;
    private final ExecutorService threadPool;
    private final ProcesadorMensajesSalida procesador;
    private final Gson gson = new Gson();

    private Envio() {
        this.envio = RedFactory.crearEnvioHandler();
        this.procesador = new ProcesadorMensajesSalida();
        this.threadPool = PoolHilos.getInstance().getThreadPool();
    }

    public static Envio getInstance() {
        if (instance == null) {
            instance = new Envio();
        }
        return instance;
    }

    public void empezarEnvio() {    
        envio.startClient();
        threadPool.execute(procesador);
    }

    public void directMessage(PeerInfo peer, String mensaje) {
        envio.sendEvent(peer.getIp(), peer.getPort(), mensaje);
    }

    public void stop() {
        envio.stop();
        procesador.stop();
    }
}
