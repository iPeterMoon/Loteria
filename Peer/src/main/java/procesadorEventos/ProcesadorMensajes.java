package procesadorEventos;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import enums.TipoEvento;
import java.util.concurrent.BlockingQueue;

/**
 *
 * @author Jp
 */
public class ProcesadorMensajes implements Runnable{

    private final BlockingQueue<String> incomingQueue;
    private final Gson gson = new Gson();
    private volatile boolean isRunning = true;

    /**
     * Primer manejador de la cadena de responsabilidad
     */
    private final ManejadorMensajes manejadorPrincipal;

    public ProcesadorMensajes(BlockingQueue<String> incomingQueue) {
        this.incomingQueue = incomingQueue;

        // Inicializar manejadores
        ManejadorMensajes nuevoPeer = new ManejadorNuevoPeer();
        ManejadorMensajes eventoJuego = new ManejadorEventoJuego();

        nuevoPeer.setNext(eventoJuego);

        this.manejadorPrincipal = nuevoPeer;
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
            manejadorPrincipal.procesar(json);
            
            
            String tipo = json.get("tipoEvento").getAsString();
            switch (TipoEvento.valueOf(tipo)) {
                case HEARTBEAT ->
                    ProcesadorHeartbeat.responderHeartbeat();
                default ->
                    System.out.println("[Processor] Evento desconocido: " + tipo);
            }
        } catch (JsonSyntaxException e) {
            System.err.println("[Processor] JSON inválido: " + e.getMessage());
        }
    }

    public void stop() {
        isRunning = false;
    }
}
