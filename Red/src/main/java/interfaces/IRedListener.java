package interfaces;

/**
 * Interfaz que define el contrato para los componentes que desean ser
 * notificados cuando se recibe un mensaje desde la red.
 *
 * @author pedro
 */
public interface IRedListener {

    /**
     * Llamado por el componente de Red cuando se recibe un nuevo evento.
     *
     * @param mensaje El Mensaje deserializado recibido.
     */
    void onMensajeRecibido(String mensaje);
}
