package procesadores_peer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

/**
 * Clase abstracta base para los procesadores de mensajes. Implementa Runnable
 * para ejecutarse en un hilo independiente.
 *
 * @author Pedro
 */
public abstract class ProcesadorMensajes implements Runnable {

    /**
     * Serializador JSON.
     */
    protected final Gson gson = new Gson();

    /**
     * Indica si el procesador continúa en ejecución.
     */
    protected volatile boolean isRunning = true;

    /**
     * Primer manejador de la cadena de responsabilidad
     */
    protected IHandler manejadorPrincipal;

    /**
     * Método principal del hilo.
     *
     * Cada implementación concreta define cómo y desde dónde se obtienen los
     * mensajes a procesar.
     */
    @Override
    public abstract void run();

    /**
     * Procesa un mensaje en formato JSON.
     *
     * Convierte el mensaje a un objeto JSON y lo envía al primer manejador de
     * la cadena de responsabilidad.
     *
     * @param mensaje Mensaje en formato JSON.
     */
    protected void procesar(String mensaje) {
        try {
            JsonObject json = gson.fromJson(mensaje, JsonObject.class);
            manejadorPrincipal.procesar(json);

        } catch (JsonSyntaxException e) {
            System.err.println("[Processor] JSON inválido: " + e.getMessage());
        }
    }

    /**
     * Detiene el procesamiento de mensajes.
     */
    public void stop() {
        isRunning = false;
    }
}
