package mensajes;

import eventos.Evento;

/**
 * Representa un mensaje dirigido a un peer específico.
 *
 * @author norma
 */
public class MensajeDirecto extends Mensaje {

    /**
     * Nombre de usuario del peer destinatario.
     */
    private final String user;

    /**
     * Constructor para un mensaje directo. 
     * Establece automáticamente el TipoMensaje como DIRECTO.
     *
     * @param evento El contenido del mensaje.
     * @param user El nombre de usuario del peer destinatario.
     */
    public MensajeDirecto(Evento evento, String user) {
        super(evento, TipoMensaje.DIRECTO);
        this.user = user;
    }

    /**
     * Obtiene el nombre de usuario del peer destinatario.
     *
     * @return Nombre de usuario del destinatario.
     */
    public String getUser() {
        return user;
    }
}
