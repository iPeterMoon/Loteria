package procesadorEventos;

import com.google.gson.JsonObject;

/**
 * Clase abstracta base para los manejadores de mensajes de salida. Implementa
 * la lógica común de la interfaz IHandler.
 *
 * @author norma
 */
public abstract class ManejadorMensajesSalida implements IHandler {

    /**
     * Referencia al siguiente manejador en la cadena.
     */
    protected IHandler next;

    /**
     * Asigna el siguiente manejador en la cadena de responsabilidad.
     */
    @Override
    public void setNext(IHandler next) {
        this.next = next;
    }

    /**
     * Método abstracto que obliga a las subclases a definir su lógica de
     * procesamiento.
     */
    @Override
    public abstract void procesar(JsonObject json);
}
