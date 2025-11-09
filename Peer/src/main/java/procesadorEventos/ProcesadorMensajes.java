package procesadorEventos;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import enums.TipoEvento;
import network.IncomingMessageDispatcher;


/**
 *
 * @author Jp
 */
public class ProcesadorMensajes implements Runnable{

    private final Gson gson = new Gson();
    private volatile boolean isRunning = true;

    /**
     * Primer manejador de la cadena de responsabilidad
     */
    private final ManejadorMensajes manejadorPrincipal;

    public ProcesadorMensajes() {

        // Inicializar manejadores
        ManejadorMensajes nuevoPeer = new ManejadorNuevoPeer();
        ManejadorMensajes eventoJuego = new ManejadorEventoJuego();

        nuevoPeer.setNext(eventoJuego);

        this.manejadorPrincipal = nuevoPeer;
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                String mensaje = IncomingMessageDispatcher.take();
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
            
        } catch (JsonSyntaxException e) {
            System.err.println("[Processor] JSON inválido: " + e.getMessage());
        }
    }

    public void stop() {
        isRunning = false;
    }
}
