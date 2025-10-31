package dtos;

/**
 *
 * @author pedro
 */
public class Mensaje {
    private final String ipReceiver;
    private final int portReceiver;
    private final String mensaje;

    public Mensaje(String ipReceiver, int portReceiver, String mensaje) {
        this.ipReceiver = ipReceiver;
        this.portReceiver = portReceiver;
        this.mensaje = mensaje;
    }

    public String getIpReceiver() {
        return ipReceiver;
    }

    public int getPortReceiver() {
        return portReceiver;
    }

    public String getMensaje() {
        return mensaje;
    }
    
    
}
