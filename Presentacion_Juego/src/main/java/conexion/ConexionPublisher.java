package conexion;

import java.io.IOException;
import java.net.*;

/**
 * ConexionPublisher.java
 *
 * Clase que se encarga de anunciar una conexión, anuncia que se está ejecutando
 * el programa y manda a todos los demás programas su ip y puerto de donde se
 * comunica.
 *
 * @author pedro
 */
public class ConexionPublisher {

    /**
     * puerto TCP donde se expone el servicio local
     */
    private static final int PORT = 8080;

    /**
     * Direccion IP del grupo multicast
     */
    private static final String MULTICAST_ADDRESS = "230.0.0.1";

    /**
     * puerto del grupo multicast
     */
    private static final int MULTICAST_PORT = 4446;

    /**
     * anuncia la conexion del nodo en la red
     *
     * este metodo obtiene la direccion IP local, concatena el puerto definido y
     * envia dicho mensaje en forma de datagrama a traves del grupo multicast
     * configurado al finalizar cierra el socket utilizado
     *
     * @throws RuntimeException si ocurre un error de red al obtener la IP local
     * o al crear el socket
     */
    public void anunciarConexion() {
        DatagramSocket socket = null;
        try {
            //se crea un socket UDP sin puerto fijo (asignado automaticamente)
            socket = new DatagramSocket();
            //obtiene la direccion IP local de la maquina
            String ipLocalString = InetAddress.getLocalHost().getHostAddress();
            //construye el mensaje a enviar en el formato "ip:puerto"
            String mensaje = ipLocalString + ":" + PORT;
            System.out.println("Anunciando conexión en: " + mensaje);
            //envia el mensaje al grupo multicast
            enviarAnuncioConexion(socket, mensaje);
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        } finally {
            //cierra el socket si aun esta abierto
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }

    /**
     * Envia un mensaje de anuncio de conexion al grupo multicast definido
     * @param socket el socket UDP usado para enviar el paquete
     * @param mensaje el contenido del anuncio
     */
    private void enviarAnuncioConexion(DatagramSocket socket, String mensaje) {
        byte[] buffer = mensaje.getBytes();
        try {
            //obtiene la direccion del grupo multicast
            InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
            //Crea el datagrama con el mensaje, la direccion del grupo y el puerto
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, MULTICAST_PORT);
            //envia el paquete al grupo multicast
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
