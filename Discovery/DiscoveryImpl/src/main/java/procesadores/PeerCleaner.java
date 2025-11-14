package procesadores;

import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import discovery.ListaPeers;
import dtos.PeerInfo;
import interfaces.IEnvio;

/**
 * Tarea (Runnable) que se ejecuta en un hilo separado por el Scheduler
 * Su única responsabilidad es:
 * 1. Pedir a ListaPeers que limpie los peers expirados
 * 2. Recibir la lista de peers eliminados
 * 3. Notificar (broadcast) a los peers restantes sobre la desconexión
 */
public class PeerCleaner implements Runnable {

    private final IEnvio envio; // Dependencia para enviar mensajes
    private final long timeoutMs;
    private final Gson gson = new Gson(); // Para crear el evento JSON

    /**
     * Constructor que inyecta las dependencias necesarias
     * @param envio El manejador de envíos (IEnvio) para notificar
     * @param timeoutMs El tiempo de expiración
     */
    public PeerCleaner(IEnvio envio, long timeoutMs) {
        this.envio = envio;
        this.timeoutMs = timeoutMs;
    }

    @Override
    public void run() {
        try {
            // 1. Llama al método modificado Esta es una llamada SÍNCRONA
            List<PeerInfo> peersEliminados = ListaPeers.limpiarYObtenerPeersMuertos(timeoutMs);

            // Si no se eliminó a nadie, no hay nada que notificar
            if (peersEliminados.isEmpty()) {
                return;
            }

            // 2. Obtiene la lista de peers que SÍ sobrevivieron (para notificarles)
            List<PeerInfo> peersVivos = ListaPeers.getAllPeers();

            // 3. Itera sobre cada peer eliminado para anunciar su "muerte"
            for (PeerInfo peerCaido : peersEliminados) {
                
                // 4. Guardar/Loguear 
                System.out.println("[PeerCleaner] Anunciando desconexión de: " + peerCaido.getIp() + ":" + peerCaido.getPort());
                
                // 5. Generar el evento de desconexión
                String eventoJson = crearMensajeDesconexion(peerCaido);

                // 6. Notificar (broadcast) a TODOS los peers restantes (ASÍNCRONO)
                for (PeerInfo peerVivo : peersVivos) {
                    envio.sendEvent(peerVivo.getIp(), peerVivo.getPort(), eventoJson);
                }
            }
            
        } catch (Exception e) {
            // Es importante capturar errores para que el scheduler no muera
            System.err.println("[PeerCleaner] Error durante la limpieza: " + e.getMessage());
        }
    }

    /**
     * Genera el mensaje JSON para el evento de desconexión
     */
    private String crearMensajeDesconexion(PeerInfo peerCaido) {
        JsonObject json = new JsonObject();
        // Asegúrate de que tus Peers entiendan este nuevo tipo de evento
        json.addProperty("tipoEvento", "PEER_DESCONECTADO"); 
        json.addProperty("id", ListaPeers.obtenerKey(peerCaido));
        return json.toString();
    }
}
