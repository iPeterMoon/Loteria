package mensajes;

import eventos.Evento;

/**
 * Clase abstracta que representa la estructura base de un mensaje.
 *
 * @author norma
 */
public abstract class Mensaje {

    private final Evento evento;
    private final TipoMensaje tipoMensaje;

    /**
     * Constructor que inicializa los atributos de la clase.
     *
     * @param evento El contenido del mensaje.
     * @param tipoMensaje El tipo de mensaje (DIRECTO o BROADCAST).
     */
    public Mensaje(Evento evento, TipoMensaje tipoMensaje) {
        this.evento = evento;
        this.tipoMensaje = tipoMensaje;
    }

    /**
     * Obtiene el evento.
     *
     * @return Evento.
     */
    public Evento getEvento() {
        return evento;
    }

    /**
     * Obtiene el tipo de mensaje.
     *
     * @return Tipo de mensaje (Directo o Broadcast).
     */
    public TipoMensaje getTipoMensaje() {
        return tipoMensaje;
    }

}
