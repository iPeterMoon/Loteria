package procesadorEventos;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import network.OutgoingMessageDispatcher;


/**
 *
 * @author Jp
 */
public class ProcesadorMensajesSalida implements Runnable{

    private final Gson gson = new Gson();
    private volatile boolean isRunning = true;

    /**
     * Primer manejador de la cadena de responsabilidad
     */
    private final IHandler manejadorPrincipal;

    public ProcesadorMensajesSalida() {

        // Inicializar manejadores
        ManejadorMensajesSalida envioHeartbeat = new ManejadorEnvioHeartbeat();
        ManejadorMensajesSalida mensajeDirecto = new ManejadorMensajeDirecto();
        ManejadorMensajesSalida broadcast = new ManejadorBroadcast();
        
        envioHeartbeat.setNext(mensajeDirecto);
        mensajeDirecto.setNext(broadcast);

        this.manejadorPrincipal = envioHeartbeat;
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                String mensaje = OutgoingMessageDispatcher.take();
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
