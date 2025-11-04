package interfaces;

import java.io.IOException;

/**
 *
 * @author Peter
 */
public interface IRecepcion {
    
    /**
     * Inicia el servidor de escucha de la red en un puerto aleatorio.
     * @return El puerto en el que el servidor está escuchando.
     * @throws IOException Si el puerto no se puede enlazar.
     */
    int empezarEscucha() throws IOException;

    /**
     * Registra un "observer" que será notificado de nuevos eventos.
     * @param listener El listener (ej. el Peer) a notificar.
     */
    void setEventListener(IRedListener listener);

    /**
     * Registra el puerto del server por si se quiere configurar
     * con un puerto específico
     * @param port puerto donde escuchará el server.
     */
    void setServerPort(int port);

    /**
     * Detiene los hilos y el socket del servidor.
     */
    void stop();
}
