package procesadorEventos;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import network.IncomingMessageDispatcher;


/**
 *
 * @author Pedro
 */ 
public abstract class ProcesadorMensajes implements Runnable{

    private final Gson gson = new Gson();
    private volatile boolean isRunning = true;

    /**
     * Primer manejador de la cadena de responsabilidad
     */
    private final IHandler manejadorPrincipal;

    @Override
    public abstract void run();

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
