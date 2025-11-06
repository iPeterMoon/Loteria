/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package network;

import com.google.gson.Gson;
import dtos.PeerInfo;
import eventos.Evento;
import factory.RedFactory;
import interfaces.IEnvio;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 *
 * @author Jp
 */
public class EnvioHandler implements Runnable {

    private final Gson gson = new Gson();
    private final BlockingQueue<Evento> outgoingQueue;
    private final List<PeerInfo> peers;
    private final IEnvio envio;
    private volatile boolean running = false;

    public EnvioHandler(BlockingQueue<Evento> queue, List<PeerInfo> peers) {
        this.outgoingQueue = queue;
        this.peers = peers;
        this.envio = RedFactory.crearEnvioHandler();
    }

    public void start() {
        if (running) {
            return;
        }
        envio.startClient();
        running = true;
        new Thread(this, "EnvioHandler-Thread").start();
    }

    @Override
    public void run() {
        while (running || !outgoingQueue.isEmpty()) {
            try {
                Evento evento = outgoingQueue.take();
                broadcast(evento);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void broadcast(Evento evento) {
        if (peers.isEmpty()) {
            return;
        }
        String json = gson.toJson(evento);
        for (PeerInfo p : peers) {
            envio.sendEvent(p.getIp(), p.getPort(), json);
        }
    }

    public void stop() {
        running = false;
        envio.stop();
    }
}
