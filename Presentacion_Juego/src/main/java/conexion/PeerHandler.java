package conexion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * PeerHandler.java
 * Clase que maneja la comunicación continua (E/S) con un peer remoto a través
 * de un Socket TCP.
 * Cada PeerHandler se ejecuta en un hilo separado para no bloquear la
 * aplicación.
 * 
 * @author pedro
 */
public class PeerHandler implements Runnable {

    private final Socket peerSocket;
    private final String peerID;
    private BufferedReader entrada;
    private PrintWriter salida;
    private volatile boolean isRunning = true;

    /**
     * Constructor. Inicializa el manejador con el socket TCP establecido.
     * 
     * @param peerSocket El socket de la conexión con el peer
     */
    public PeerHandler(Socket peerSocket) {
        this.peerSocket = peerSocket;
        this.peerID = peerSocket.getInetAddress().getHostAddress() + ":" + peerSocket.getPort();
        System.out.println("[HANDLER] Manejador para Peer iniciado: " + this.peerID);
        try{
            //Inicializar flujos de entrada y salida
            this.entrada = new BufferedReader(new InputStreamReader(peerSocket.getInputStream()));
            this.salida = new PrintWriter(peerSocket.getOutputStream(), true);
        } catch (IOException e) {
            System.err.println("[HANDLER] Error al configurar flujos E/S para " + peerID + ": "+ e.getMessage());
            closeConnection();            
        }
    }

    /**
     * Cierra el socket y detiene el manejador
     */
    public void closeConnection(){
        this.isRunning = false;
        try{
            if(peerSocket != null && !peerSocket.isClosed()){
                peerSocket.close();
                System.out.println("[HANLDER] Conexxión cerrada con " + peerID);
            }
        } catch (IOException e){
            System.err.println("[HANDLER] Error al cerrar el socket: "+ e.getMessage());
        }
    }

    /**
     * Bucle principal de recepción de mensajes.
     */
    @Override
    public void run() {
        if (entrada == null){
            return;
        } 
        
        String mensaje;
        try{
            while(isRunning && (mensaje = entrada.readLine()) != null) {
                System.out.println("[HANDLER - " + peerID + "] Mensaje recibido: " + mensaje);
                //Lógica del juego
                //Procesar las acciones del juego
                //1. Deserializar mensaje (JSON, String, etc.)
                //2. Actualizar Modelo de Juego
                //3. Modificar Modelo de la Vista
                //TODO: Implementar Lógica del juego
            }
        } catch (IOException e) {
            System.err.println("[HANDLER - "+ peerID + "] Conexión cerrada inesperadamente: " + e.getMessage());
        } finally {
            closeConnection();
        }
    }

    /**
     * Envía un mensaje al peer remoto
     * @param mensaje Mensaje a enviar
     */
    public void sendMessage(String mensaje) {
        if(salida!= null && !peerSocket.isClosed()) {
            salida.println(mensaje);
        }
    }

}
