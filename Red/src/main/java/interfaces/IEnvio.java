package interfaces;

/**
 *
 * @author Peter
 */
public interface IEnvio {
     
    /**
     * Empieza el hilo del cliente para enviar mensajes
     */
    void startClient();
    
    /**
     * Envía un evento a un destinatario específico (por IP).
     * La implementación se encargará de gestionar la conexión (crear, cachear, etc.).
     * @param ip La IP del destinatario.
     * @param puerto El puerto del destinatario.
     * @param mensaje El mensaje a enviar en forma de un Objeto.
     */
    void sendEvent(String ip, int puerto, String mensaje);

    /**
     * Detiene todos los hilos y cierra todas las conexiones.
     */
    void stop();
}
