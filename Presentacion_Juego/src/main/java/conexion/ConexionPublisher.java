package conexion;

import java.io.IOException;
import java.net.*;

/**
 * ConexionPublisher.java Clase que se encarga de anunciar una conexión, anuncia
 * que se está ejecutando el programa y manda a todos los demás programas su ip
 * y el puerto TCP de escucha. Se llama solo cuando la aplicación se inicia o
 * cuando se recibe un anuncio de otra instancia.
 *
 * @author pedro
 */
public class ConexionPublisher {

    private static final String MULTICAST_ADDRESS = "230.0.0.1";
    private static final int MULTICAST_PORT = 4446;

    /**
     * Anuncia la conexión de esta instancia a través de Multicast. Es un método
     * estático para ser llamado fácilmente por otras clases.
     *
     * @param puertoTCP El puerto dinámico o fijo de escucha del ServidorTCP.
     */
    public static void anunciarConexion(int puertoTCP) {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
            String ipLocalString = InetAddress.getLocalHost().getHostAddress();
            
            //El mensaje incluye la IP y el puerto TCP de escucha
            String mensaje = ipLocalString + ":" + puertoTCP;
            System.out.println("[PUBLISHER] Anunciando conexión en: " + mensaje);
            
            enviarAnuncioConexion(socket, mensaje);
            
        } catch (SocketException | UnknownHostException e) {
            System.err.println("[PUBLISHER] Error al inicializar/obtener IP: " + e.getMessage());
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }
    /**
     * Metodo para enviar el anuncio de la conexión al Multicast
     * @param socket Socket UDP para enviar la info
     * @param mensaje Mensaje a enviar
     */
    private static void enviarAnuncioConexion(DatagramSocket socket, String mensaje) {
        byte[] buffer = mensaje.getBytes();
        try {
            InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, MULTICAST_PORT);
            socket.send(packet);
        } catch (IOException e) {
            System.err.println("[PUBLISHER] Error al enviar anuncio: " + e.getMessage());
        }
    }
}
