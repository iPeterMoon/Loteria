package servidor;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

/**
 *
 * @author pedro
 */
public class ServidorRed implements Runnable {

    private final ServerSocket serverSocket;
    private final BlockingQueue<String> incomingQueue;
    private final ExecutorService threadPool;
    private volatile boolean isRunning = true;

    /**
     * Constructor.
     *
     * @param incomingQueue La cola donde los manejadores pondrán los eventos
     * recibidos.
     * @param threadPool El pool de hilos para ejecutar los manejadores.
     * @throws IOException Si no se puede enlazar el puerto.
     */
    public ServidorRed(BlockingQueue<String> incomingQueue, ExecutorService threadPool) throws IOException {
        this.serverSocket = new ServerSocket(0); // Puerto aleatorio asignado por el SO
        this.incomingQueue = incomingQueue;
        this.threadPool = threadPool;
    }

    /**
     * Constructor.
     *
     * @param incomingQueue La cola donde los manejadores pondrán los eventos
     * recibidos.
     * @param threadPool El pool de hilos para ejecutar los manejadores.
     * @param port Puerto especifico en el que escuchará el servidor.
     * @throws IOException Si no se puede enlazar el puerto.
     */
    public ServidorRed(BlockingQueue<String> incomingQueue, ExecutorService threadPool, int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.incomingQueue = incomingQueue;
        this.threadPool = threadPool;
    }

    /**
     * @return El puerto en el que el servidor está escuchando
     */
    public int getPort() {
        return serverSocket.getLocalPort();
    }

    @Override
    public void run() {
        System.out.println("[ServidorRed] Escuchando en el puerto: " + getPort());
        while (isRunning) {
            try {
                // Bloquea hasta que llega una nueva conexión
                Socket clientSocket = serverSocket.accept();
                System.out.println("[ServidorRed] Conexión entrante de: " + clientSocket.getInetAddress().getHostAddress());

                // Crea un nuevo manejador para este cliente y lo pone en el pool de hilos
                threadPool.submit(new ManejadorConexionEntrante(clientSocket, incomingQueue));

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
     * Detiene el servidor cerrando el ServerSocket
     */
    public void stop() {
        isRunning = false;
        try {
            if(serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
             System.err.println("[ServidorRed] Error al cerrar server socket: " + e.getMessage());
        }
    }
}
