package conexion;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ServidorTCP.java Clase que se encarga de escuchar y aceptar conexiones TCP
 * entrantes de otros jugadores. Asigna un puerto dinámico (puerto 0) al iniciar
 * y es crucial para el modo P2P.
 *
 * @author pedro
 */
public class ServerTCP implements Runnable {

    private ServerSocket serverSocket;
    //Mapa para almacenar los sockets de los peers que se conectan a Nosotros (somos el servidor para ellos)
    private final ConcurrentHashMap<String, PeerHandler> conexionesEntrantes = new ConcurrentHashMap<>();
    private final int puertoEscucha;
    private volatile boolean isRunning = true;

    /**
     * Constructor que inicia el ServerSocket en un puerto dinámico (0)
     *
     * @throws IOException
     */
    public ServerTCP() throws IOException {
        //Al usar el puerto 0, el sistema operativo asigna un puerto disponible
        this.serverSocket = new ServerSocket(0);
        this.puertoEscucha = serverSocket.getLocalPort();
        System.out.println("[SERVER] Servidor TCP iniciado en puerto: " + this.puertoEscucha);
    }

    /**
     * Retorna el puerto que fue asignado dinámicamente,
     * el cual debe ser anunciado vía Multicast.
     * @return Puerto TCP de escucha.
     */
    public int getPuertoEscucha() {
        return puertoEscucha;
    }
    
    /**
     * Bucle principal del servidor que acepta conexiones entrantes.
     */
    @Override
    public void run(){
        while(isRunning){
            try{
                //Bloquea hasta que llega una nueva conexión
                Socket clienteSocket = serverSocket.accept();
                String idCliente = clienteSocket.getInetAddress().getHostAddress() + ":" + clienteSocket.getPort();

                System.out.println("[SERVER] Conexión TCP ACEPTADA de: " + idCliente);
                
                PeerHandler handler = new PeerHandler(clienteSocket);
                conexionesEntrantes.put(idCliente, handler);
                new Thread(handler).start();
            } catch(IOException e){
                if (!isRunning) {
                    System.out.println("[SERVER] Servidor TCP detenido.");
                } else {
                    System.err.println("[SERVER] Error al aceptar conexión: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Detiene el servidor TCP de manera segura.
     */
    public void stop() {
        this.isRunning = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Cierra todas las conexiones también
        conexionesEntrantes.values().forEach(PeerHandler::closeConnection);
        System.out.println("[SERVER] Servidor TCP apagado.");
    }
    
}
