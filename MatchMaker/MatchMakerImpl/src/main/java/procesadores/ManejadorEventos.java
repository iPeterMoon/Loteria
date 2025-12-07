package procesadores;

import eventos.Evento;
import procesadores.IHandler;

/**
 * Clase abstracta base para los manejadores de mensajes. Define la estructura
 * para enlazar manejadores y procesar eventos.
 *
 * @author rocha
 */
public abstract class ManejadorEventos implements IHandler {

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
     * @param evento Evento a procesar
     */
    @Override
    public abstract void procesar(Evento evento);
}
