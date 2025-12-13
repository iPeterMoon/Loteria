package dtos;

/**
 * Clase que representa un mensaje de red dentro del sistema. Es utilizado como
 * un DTO para el intercambio de información entre componentes.
 *
 * @author pedro
 */
public class Mensaje {

    /**
     * Dirección IP del receptor del mensaje.
     */
    private final String ipReceiver;

    /**
     * Puerto de destino del receptor.
     */
    private final int portReceiver;

    /**
     * Contenido del mensaje a transmitir.
     */
    private final String mensaje;

    /**
     * Crea una nueva instancia de Mensaje con la información del receptor y el
     * contenido a enviar.
     *
     * @param ipReceiver Dirección IP del receptor.
     * @param portReceiver Puerto de destino.
     * @param mensaje Contenido del mensaje.
     */
    public Mensaje(String ipReceiver, int portReceiver, String mensaje) {
        this.ipReceiver = ipReceiver;
        this.portReceiver = portReceiver;
        this.mensaje = mensaje;
    }

    /**
     * Obtiene la dirección IP del receptor.
     *
     * @return Dirección IP del receptor.
     */
    public String getIpReceiver() {
        return ipReceiver;
    }

    /**
     * Obtiene el puerto del receptor.
     *
     * @return Puerto de destino.
     */
    public int getPortReceiver() {
        return portReceiver;
    }

    /**
     * Obtiene el contenido del mensaje.
     *
     * @return Contenido del mensaje.
     */
    public String getMensaje() {
        return mensaje;
    }
}
