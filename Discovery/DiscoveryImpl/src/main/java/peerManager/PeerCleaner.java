package peerManager;

import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import peerManager.ListaPeers;
import dtos.PeerInfo;
import interfaces.IEnvio;

public class PeerCleaner implements Runnable {
    private final IEnvio envio; // El 'envio' de Discovery
    private final long timeoutMs;
    private final Gson gson = new Gson();
    private static final long HEARTBEAT_INTERVALO_MS = 500;
    private static final long HEARTBEAT_TIMEOUT_MS = 3 * HEARTBEAT_INTERVALO_MS; // 3x sin heartbeat → muerto
    private static final int LIMPIEZA_INTERVALO_MS = 2000;

    public PeerCleaner(IEnvio envio, long timeoutMs) {
        this.envio = envio;
        this.timeoutMs = timeoutMs;
    }

 @Override
    public void run() {
        try {
            long ahora = System.currentTimeMillis();
            
            // 1. OBTENER DATOS: Pedimos los tiempos a ListaPeers
            Map<String, Long> tiempos = ListaPeers.getMapaUltimaVezVistos();
            
            // 2. LÓGICA DE LIMPIEZA (EL "IF" ESTÁ AQUÍ)
            for (Map.Entry<String, Long> entrada : tiempos.entrySet()) {
                String key = entrada.getKey();
                long ultimaVez = entrada.getValue();
                
                // Verificamos si ya pasó el tiempo límite
                if (ahora - ultimaVez > timeoutMs) {
                    
                    // 3. ELIMINAR: Ordenamos a ListaPeers que borre a este key
                    PeerInfo peerCaido = ListaPeers.eliminarPeer(key);
                    
                    if (peerCaido != null) {
                        System.out.println("[PeerCleaner] Detectado peer muerto por timeout: " + key);
                        
                        // 4. NOTIFICAR: Creamos el evento y lo mandamos
                        String eventoJson = crearMensajeDesconexion(peerCaido);
                        broadcastDesconexion(eventoJson);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("[PeerCleaner] Error: " + e.getMessage());
        }
    }

    /**
     * Envía el mensaje a todos los peers vivos restantes
     */
    private void broadcastDesconexion(String mensaje) {
        List<PeerInfo> vivos = ListaPeers.getPeersVivos();
        for (PeerInfo vivo : vivos) {
            // Enviamos el mensaje usando la interfaz IEnvio del Discovery
            envio.sendEvent(vivo.getIp(), vivo.getPort(), mensaje);
        }
    }

    /**
     * Genera el JSON del evento PEER_DESCONECTADO
     */
    private String crearMensajeDesconexion(PeerInfo peerCaido) {
        JsonObject json = new JsonObject();
        json.addProperty("tipoEvento", "PEER_DESCONECTADO");
        // Usamos el método helper de ListaPeers para consistencia en la ID
        json.addProperty("id", ListaPeers.obtenerKey(peerCaido));
        return json.toString();
    }
}