package cliente;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import dtos.Mensaje;

/**
 *
 * @author pedro
 */
public class ClienteRed implements Runnable {

    private final Map<String, PrintWriter> connections = new ConcurrentHashMap<>();
    private final Map<String, Socket> sockets = new ConcurrentHashMap<>();
    private volatile boolean isRunning = true;

    public ClienteRed() {
    }

    @Override
    public void run() {
        System.out.println("[ClienteRed] Procesador de cola de salida iniciado.");
        while (isRunning) {
            try {
                //Bloquea el hilo hasta que haya un mensaje que sacar de la cola
                Mensaje msg = OutgoingMessageDispatcher.take();

                //Procesa el envío
                enviarMensaje(msg);
            } catch (InterruptedException e) {
                if (isRunning) {
                    System.err.println("[ClienteRed] Hilo de cola de salida interrumpido.");
                }
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    /**
     * Envia un mensaje a una ip en especifico (la ip viene especificada en el mensaje
     * @param msg 
     */
    private void enviarMensaje(Mensaje msg) {
        String targetIp = msg.getIpReceiver();
        int targetPort = msg.getPortReceiver();
        String cacheKey = targetIp + ":" + targetPort;
        try {
            PrintWriter output = obtenerOutput(targetIp, targetPort, cacheKey);

            // Enviar el evento con flush forzado
            output.println(msg.getMensaje());
            output.flush();

        } catch (IOException e) {
            System.err.println("Error al enviar a " + cacheKey + ": " + e.getMessage());
            // Si falla, eliminamos tanto el PrintWriter como el Socket del caché
            PrintWriter failedWriter = connections.remove(cacheKey);
            Socket failedSocket = sockets.remove(cacheKey);
            
            if (failedWriter != null) {
                failedWriter.close();
            }
            if (failedSocket != null) {
                try {
                    failedSocket.close();
                } catch (IOException ex) {
                    System.err.println("Error al cerrar socket en error: " + ex.getMessage());
                }
            }
        }
    }

    /**
     * Obtiene o crea el output para enviar a una ip en especifico
     * @param targetIp Ip a donde se va a enviar
     * @param targetPort Puerto donde escucha
     * @param cacheKey Llave que representa la ip y el puerto a donde se va a enviar
     * @return PrintWriter Objeto donde se echan los mensajes a enviar
     * @throws IOException por algun fallo con el PrintWriter
     */
    private synchronized PrintWriter obtenerOutput(String targetIp, int targetPort, String cacheKey) throws IOException {
        PrintWriter output = connections.get(cacheKey);
        if (output == null) {
            System.out.println("[ClienteRed] Creando nueva conexión para: " + cacheKey);
            Socket socket = new Socket(targetIp, targetPort);
            output = new PrintWriter(socket.getOutputStream(), true);
            
            // Guardar tanto el socket como el output
            sockets.put(cacheKey, socket);
            connections.put(cacheKey, output);
        }
        return output;
    }

    /**
     * Detiene el cliente e interrumpe el hilo.
     */
    public void stop() {
        isRunning = false;

        // Cierra todos los sockets cacheados
        for (Socket socket : sockets.values()) {
            try {
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) {
                System.err.println("[ClienteRed] Error al cerrar socket: " + e.getMessage());
            }
        }
        
        for (PrintWriter out : connections.values()) {
            out.close();
        }
        
        sockets.clear();
        connections.clear();
    }
}
