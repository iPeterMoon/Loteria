package conexion;

import java.io.IOException;
import java.net.*;

/**
 * ConexionPublisher.java
 * 
 * Clase que se encarga de anunciar una conexión, anuncia que se está 
 * ejecutando el programa y manda a todos los demás programas su ip y puerto de donde se comunica.
 * @author pedro
 */
public class ConexionPublisher {
    private static final int PORT = 8080;
    private static final String MULTICAST_ADDRESS = "230.0.0.1";
    private static final int MULTICAST_PORT = 4446;

    public void anunciarConexion() {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
            String ipLocalString = InetAddress.getLocalHost().getHostAddress();
            String mensaje = ipLocalString + ":" + PORT;
            System.out.println("Anunciando conexión en: " + mensaje);
            enviarAnuncioConexion(socket, mensaje);
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }

    private void enviarAnuncioConexion(DatagramSocket socket, String mensaje) {
        byte[] buffer = mensaje.getBytes();
        try {
            InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, MULTICAST_PORT);
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}