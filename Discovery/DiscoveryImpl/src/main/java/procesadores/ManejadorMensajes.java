package procesadores;

import com.google.gson.JsonObject;

/**
 * Clase abstracta base para los manejadores de mensajes.
 * Define la estructura para enlazar manejadores y procesar eventos.
 * 
 * @author rocha
 */
public abstract class ManejadorMensajes implements IHandler {

    /** Referencia al siguiente manejador en la cadena. */
    protected IHandler next;

    /**
     * Asigna el siguiente manejador en la cadena de responsabilidad.
     * 
     * @param next siguiente manejador
     */
    @Override
    public void setNext(IHandler next) {
        this.next = next;
    }
    
    /**
     * Procesa el mensaje recibido o lo delega al siguiente manejador.
     * 
     * @param json mensaje en formato JSON
     */
    @Override
    public abstract void procesar(JsonObject json);
}
