package conexion;

import java.io.IOException;
import java.net.*;

/**
 * ConexionReceiver.java
 * 
 * Clase que se encarga de recibir conexiones de otras instancias de este programa.
 * Se une a un grupo multicast que recibe mensajes con direcciones ips para registrarlas
 * Este mensaje es enviado cada que se inicia el programa.
 * @author pedro
 */
public class ConexionReceiver implements Runnable {

    private final int MULTICAST_PORT = 4446;
    private final String MULTICAST_ADDRESS = "230.0.0.1";
    private volatile boolean isRunning = true;
    private int conexionesRecibidas;
    
    /**
     * Método principal de la clase, prepara un socket MultiCast para recibir mensajes de conexión
     */
    @Override
    public void run() {
        prepararSocket();
    }
    
    /**
     * Prepara el socket Multicast, acoplado a una dirección Multicast y un puerto específico
     * Además selecciona una interfaz de red y se une a un grupo multicast para recibir los mensajes
     */
    private void prepararSocket(){
        conexionesRecibidas = 0;
        MulticastSocket socket = null;
        try {
            socket = new MulticastSocket(MULTICAST_PORT);
            InetAddress groupAddress = InetAddress.getByName(MULTICAST_ADDRESS);
            SocketAddress group = new InetSocketAddress(groupAddress, MULTICAST_PORT);

            NetworkInterface netIf = SeleccionadorInterfaz.seleccionarInterfazRed();
            if (netIf != null) {
                socket.joinGroup(group, netIf);
                System.out.println("Unido al grupo multicast: " + groupAddress.getHostAddress());
                recibirMensajes(socket);
            } else {
                System.err.println("No se pudo unir al grupo multicast. No se encontró una interfaz de red adecuada.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                try {
                    // Cierra el socket de forma segura
                    socket.leaveGroup(new InetSocketAddress(MULTICAST_ADDRESS, MULTICAST_PORT), SeleccionadorInterfaz.seleccionarInterfazRed());
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Se encarga de recibir los mensajes con el protocolo UDP.
     * recibe una string con ip y puerto de donde se envío el mensaje
     * @param socket 
     */
    private void recibirMensajes(MulticastSocket socket) {
        while (isRunning) {
            try {
                byte[] buf = new byte[256];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                String received = new String(packet.getData(), 0, packet.getLength());
                String remoteIp = packet.getAddress().getHostAddress();
                if(conexionesRecibidas != 0){
                    System.out.println("Anuncio recibido de " + remoteIp + ": " + received);
                }
                conexionesRecibidas++;

            } catch (SocketException e) {
                if (!isRunning) {
                    System.out.println("El socket de recepción ha sido cerrado.");
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Para la ejecución del hilo principal para dejar de recibir mensajes
     */
    public void stop() {
        this.isRunning = false;
    }
}