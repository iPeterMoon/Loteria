package mensajes;

import eventos.Evento;

/**
 * Representa un mensaje de broadcast que debe ser enviado a todos los peers.
 *
 * @author norma
 */
public class MensajeBroadcast extends Mensaje {

    /**
     * Constructor para un mensaje de broadcast. Establece automáticamente el
     * TipoMensaje como BROADCAST.
     *
     * @param evento El contenido del mensaje.
     */
    public MensajeBroadcast(Evento evento) {
        super(evento, TipoMensaje.BROADCAST);
    }

}
