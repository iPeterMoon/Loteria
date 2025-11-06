package procesadorEventos;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import enums.TipoEvento;
import eventos.EventoFicha;
import eventos.EventoNuevoPeer;
import java.util.concurrent.BlockingQueue;

/**
 *
 * @author Jp
 */
public class ProcesadorMensajes implements Runnable{

    private final BlockingQueue<String> incomingQueue;
    private final Gson gson = new Gson();
    private volatile boolean isRunning = true;

    public ProcesadorMensajes(BlockingQueue<String> incomingQueue) {
        this.incomingQueue = incomingQueue;
    }

    @Override
    public void run() {
        while (isRunning || !incomingQueue.isEmpty()) {
            try {
                String mensaje = incomingQueue.take();
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
                    ProcesadorHeartbeat.responderHeartbeat();
                default ->
                    System.out.println("[Processor] Evento desconocido: " + tipo);
            }
        } catch (JsonSyntaxException e) {
            System.err.println("[Processor] JSON inválido: " + e.getMessage());
        }
    }

    private void procesarNuevoPeer(JsonObject json) {
        EventoNuevoPeer evento = gson.fromJson(json, EventoNuevoPeer.class);
        ProcesadorConexiones.registrarPeer(evento);
    }

    private void procesarFicha(JsonObject json) {
        EventoFicha evento = gson.fromJson(json, EventoFicha.class);
    }

    public void stop() {
        isRunning = false;
    }
}
