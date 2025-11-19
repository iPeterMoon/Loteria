/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package procesadores_discovery;

import com.google.gson.JsonObject;

/**
 *
 * @author Alici
 */
public abstract class ManejadorMensajesSalida implements IHandler {

    /**
     * Referencia al siguiente manejador en la cadena.
     */
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
