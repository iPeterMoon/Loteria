package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Clase encargada de gestionar conexiones entrantes desde la red. Implementa
 * Runnable para ejecutarse en un hilo independiente y permanecer a la espera de
 * conexiones TCP entrantes.
 *
 * @author pedro
 */
public class ServidorRed implements Runnable {

    /**
     * Socket del servidor.
     */
    private final ServerSocket serverSocket;
    /**
     * Indica si el servidor continúa en ejecución.
     */
    private volatile boolean isRunning = true;

    /**
     * Constructor.
     *
     * @throws IOException Si no se puede enlazar el puerto.
     */
    public ServidorRed() throws IOException {
        this.serverSocket = new ServerSocket(0); // Puerto aleatorio asignado por el SO
    }

    /**
     * Constructor.
     *
     * @param port Puerto especifico en el que escuchará el servidor.
     * @throws IOException Si no se puede enlazar el puerto.
     */
    public ServidorRed(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    /**
     * @return El puerto en el que el servidor está escuchando
     */
    public int getPort() {
        return serverSocket.getLocalPort();
    }

    /**
     * Ciclo principal del servidor. Permanece escuchando conexiones entrantes
     * y, por cada conexión aceptada, crea un hilo.
     */
    @Override
    public void run() {
        System.out.println("[ServidorRed] Escuchando en el puerto: " + getPort());
        while (isRunning) {
            try {
                // Bloquea hasta que llega una nueva conexión
                Socket clientSocket = serverSocket.accept();
                System.out.println("[ServidorRed] Conexión entrante de: " + clientSocket.getInetAddress().getHostAddress());

                // Crea un nuevo hilo para poner el mensaje en una cola y lo pone en el pool de hilos
                new Thread(() -> dispatch(clientSocket)).start();

            } catch (IOException e) {
                if (isRunning) {
                    System.err.println("[ServidorRed] Error al aceptar conexión: " + e.getMessage());
                } else {
                    System.out.println("[ServidorRed] Servidor detenido.");
                }
            }
        }
    }

    /**
     * Metodo para extraer el mensaje de un socket y ponerlo en la cola
     * entrante.
     *
     * @param socket Socket entrante
     */
    private void dispatch(Socket socket) {
        try (BufferedReader input = new BufferedReader(
                new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8))) {
            String linea;

            while ((linea = input.readLine()) != null) {
                // Pone el mensaje en la cola central ubicada en RedImpl
                IncomingMessageDispatcher.dispatch(linea);
            }
        } catch (Exception e) {
            // Causa más común: Fin de stream (EOFException) o SocketException
            // indica que el cliente se desconectó.
            System.out.println("[Manejador] Conexión cerrada con " + socket.getInetAddress().getHostAddress()
                    + ". Razón: " + e.getMessage());
        } finally {
            cerrarSocket(socket);
        }
    }

    /**
     * Método auxiliar para cerrar un socket al final de que es usado
     */
    private void cerrarSocket(Socket socket) {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {

        }
    }

    /**
     * Detiene el servidor cerrando el ServerSocket.
     */
    public void stop() {
        isRunning = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println("[ServidorRed] Error al cerrar server socket: " + e.getMessage());
        }
    }
}
