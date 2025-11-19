package procesadores_peer;

import com.google.gson.JsonObject;

/**
 * Interfaz que define el contrato base para los manejadores (handlers)
 * del patrón Chain of Responsibility.
 * 
 * Cada manejador concreto debe implementar esta interfaz para procesar
 * un tipo específico de evento o delegar la solicitud al siguiente
 * manejador de la cadena si no puede procesarla.
 * 
 * @author rocha
 */
public interface IHandler {
    
     /**
     * Establece el siguiente manejador en la cadena de responsabilidad.
     * 
     * @param next el siguiente objeto que implementa IHandler y que procesará la solicitud si el manejador actual no puede hacerlo.
     */
    void setNext(IHandler next);
    
    /**
     * Procesa el evento recibido en formato JSON.
     * Si el manejador actual no reconoce el tipo de evento, debe delegar la solicitud al siguiente manejador.
     * 
     * @param json objeto que contiene los datos del evento a procesar.
     */
    void procesar(JsonObject json);
}
