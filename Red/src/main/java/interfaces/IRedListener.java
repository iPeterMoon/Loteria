package interfaces;

/**
 *
 * @author pedro
 */
public interface IRedListener {
    /**
     * Llamado por el componente de Red cuando se recibe un nuevo evento.
     * @param mensaje El Mensaje deserializado recibido.
     */
    void onMensajeRecibido(String mensaje);
}
