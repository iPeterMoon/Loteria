/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package procesadorEventos;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import dtos.PeerInfo;
import enums.TipoEvento;
import eventos.Evento;
import eventos.EventoFicha;
import eventos.EventoNuevoPeer;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

/**
 *
 * @author Jp
 */
public class ProcesadorMensajes implements Runnable {

    private final BlockingQueue<String> queue;
    private final Consumer<Evento> eventHandler;
    private final List<PeerInfo> peers;
    private final PeerInfo myInfo;
    private final Gson gson = new Gson();
    private volatile boolean running = true;

    public ProcesadorMensajes(BlockingQueue<String> queue, Consumer<Evento> handler, List<PeerInfo> peers, PeerInfo myInfo) {
        this.queue = queue;
        this.eventHandler = handler;
        this.peers = peers;
        this.myInfo = myInfo;
    }

    @Override
    public void run() {
        while (running || !queue.isEmpty()) {
            try {
                String mensaje = queue.take();
                procesar(mensaje);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void procesar(String mensaje) {
        try {
            JsonObject json = gson.fromJson(mensaje, JsonObject.class);
            String tipo = json.get("tipoEvento").getAsString();

            switch (TipoEvento.valueOf(tipo)) {
                case NUEVO_PEER ->
                    procesarNuevoPeer(json);
                case FICHA ->
                    procesarFicha(json);
                case HEARTBEAT ->
                    ProcesadorHeartbeat.responderHeartbeat(myInfo);
                default ->
                    System.out.println("[Processor] Evento desconocido: " + tipo);
            }
        } catch (JsonSyntaxException e) {
            System.err.println("[Processor] JSON inválido: " + e.getMessage());
        }
    }

    private void procesarNuevoPeer(JsonObject json) {
        EventoNuevoPeer evento = gson.fromJson(json, EventoNuevoPeer.class);
        peers.add(evento.getPeer());
    }

    private void procesarFicha(JsonObject json) {
        EventoFicha evento = gson.fromJson(json, EventoFicha.class);
        eventHandler.accept(evento);
    }

    public void stop() {
        running = false;
    }
}
