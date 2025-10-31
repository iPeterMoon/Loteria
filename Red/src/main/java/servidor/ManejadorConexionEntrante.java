package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.BlockingQueue;

/**
 *
 * @author pedro
 */
public class ManejadorConexionEntrante implements Runnable {

    private final Socket socket;
    private final BlockingQueue<String> incomingQueue;

    public ManejadorConexionEntrante(Socket socket, BlockingQueue<String> incomingQueue) {
        this.socket = socket;
        this.incomingQueue = incomingQueue;
    }

    @Override
    public void run() {
        try (BufferedReader input = new BufferedReader(
                new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8))) {
            String linea;

            while ((linea = input.readLine()) != null) {
                // Pone el mensaje en la cola central ubicada en RedImpl
                incomingQueue.put(linea);
            }
        } catch (Exception e) {
            // Causa más común: Fin de stream (EOFException) o SocketException
            // indica que el cliente se desconectó.
            System.out.println("[Manejador] Conexión cerrada con " + socket.getInetAddress().getHostAddress()
                    + ". Razón: " + e.getMessage());
        } finally {
            cerrarSocket();
        }
    }

    private void cerrarSocket() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {

        }
    }

}
