package peerManager;

import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import peerManager.ListaPeers;
import dtos.PeerInfo;
import interfaces.IEnvio;

public class PeerCleaner implements Runnable {
    private final IEnvio envio; // El envio de Discovery
    private final long timeoutMs;
    private final Gson gson = new Gson();
    private static final long HEARTBEAT_INTERVALO_MS = 500;
    private static final long HEARTBEAT_TIMEOUT_MS = 3 * HEARTBEAT_INTERVALO_MS; // 3x sin heartbeat → muerto
    private static final int LIMPIEZA_INTERVALO_MS = 2000;

    private static final Map<String, Long> ultimaVezVistos = new ConcurrentHashMap<>();

    public PeerCleaner(IEnvio envio, long timeoutMs) {
        this.envio = envio;
        this.timeoutMs = timeoutMs;
    }

    public static void actualizarUltimaVezVisto(String peerKey) {
        ultimaVezVistos.put(peerKey, System.currentTimeMillis());
    }

    @Override
    public void run() {
        try {
            long ahora = System.currentTimeMillis();

            // Iteramos sobre nuestro mapa
            for (Map.Entry<String, Long> entrada : ultimaVezVistos.entrySet()) {
                String key = entrada.getKey();
                long ultimaVez = entrada.getValue();

                
                if (ahora - ultimaVez > timeoutMs) {
                    
                    // eliminar de mi propio mapa (PeerCleaner)
                    ultimaVezVistos.remove(key);

                    // eliminarR de la lista oficial
                    // Llamamos al método auxiliar porque el mapa peersVivos es privado en ListaPeers
                    PeerInfo peerCaido = ListaPeers.eliminarPeer(key);

                    if (peerCaido != null) {
                        System.out.println("[PeerCleaner] Eliminando peer expirado: " + key);
                        
                        // Generar evento y Broadcast
                        String eventoJson = crearMensajeDesconexion(peerCaido);
                        broadcastDesconexion(eventoJson);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("[PeerCleaner] Error: " + e.getMessage());
        }
    }

    private void broadcastDesconexion(String mensaje) {
        // Usamos el método auxiliar de iteración de ListaPeers
        ListaPeers.ejecutarEnPeersVivos((key, peerInfo) -> {
            envio.sendEvent(peerInfo.getIp(), peerInfo.getPort(), mensaje);
        });
    }

    private String crearMensajeDesconexion(PeerInfo peerCaido) {
        JsonObject json = new JsonObject();
        json.addProperty("tipoEvento", "PEER_DESCONECTADO");
        json.addProperty("id", ListaPeers.obtenerKey(peerCaido));
        return json.toString();
    }
}