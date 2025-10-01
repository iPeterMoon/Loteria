package conexion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import dtos.FichaDTO;
import modelo.ModeloVistaFacade;

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
    private ObjectInputStream entrada;
    private ObjectOutputStream salida;
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
            this.salida = new ObjectOutputStream(peerSocket.getOutputStream());
            this.entrada = new ObjectInputStream(peerSocket.getInputStream());
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
                //Notificar al PeerService que el handler se cerró
                PeerService.getInstancia().removerHandler(peerID);

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
        
        try{
            while(isRunning) {
                //Leer un objeto del socket.
                Object objetoRecibido = entrada.readObject();

                if(objetoRecibido instanceof FichaDTO) {
                    FichaDTO fichaDTO = (FichaDTO) objetoRecibido;
                    actualizarTarjeta(fichaDTO);
                }
            }
        } catch (IOException e) {
            System.err.println("[HANDLER - "+ peerID + "] Conexión cerrada inesperadamente: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("[HANDLER - "+ peerID + "] Objeto recibido no reconocido: " + e.getMessage());
        } finally {
            closeConnection();
        }
    }

    /**
     * Metodo que llama al modelo vista fachada y actualiza el subject de la tarjeta
     * @param ficha
     */
    private void actualizarTarjeta(FichaDTO ficha){
        ModeloVistaFacade modeloVista = ModeloVistaFacade.getInstance();
        modeloVista.colocarFicha(ficha);
    }    

    /**
     * Envía un objeto serializable al peer remoto.
     * @param objeto Objeto a enviar
     */
    public void sendObject(Object objeto){
        try {
            if(salida != null && !peerSocket.isClosed()){
                salida.writeObject(objeto);
                salida.flush(); // Asegura que los datos se envíen de inmediato
            }
        } catch (IOException e) {
            System.err.println("[HANDLER] Error al enviar objeto a " + peerID + ": "+ e.getMessage());
            // Si falla el envío, asumimos que el peer se desconectó
            closeConnection();
        }
    }


}
