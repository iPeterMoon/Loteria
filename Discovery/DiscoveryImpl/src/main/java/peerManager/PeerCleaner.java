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
            //  Llama al método modificado (que devuelve la lista)
            List<PeerInfo> peersEliminados = ListaPeers.limpiarPeersMuertos(timeoutMs);

            if (peersEliminados.isEmpty()) {
                return;
            }

            //  Obtiene los peers vivos (para notificarles)
            List<PeerInfo> peersVivos = ListaPeers.getAllPeers();

            //  Itera sobre los caídos
            for (PeerInfo peerCaido : peersEliminados) {

                System.out.println("[PeerCleaner] Anunciando desconexión de: " + peerCaido.getIp());

                //  Crea el evento
                String eventoJson = crearMensajeDesconexion(peerCaido);

                // Usa IEnvio para el broadcast
                for (PeerInfo peerVivo : peersVivos) {
                    envio.sendEvent(peerVivo.getIp(), peerVivo.getPort(), eventoJson);
                }
            }
        } catch (Exception e) {
            System.err.println("[PeerCleaner] Error durante la limpieza: " + e.getMessage());
        }
    }

    private String crearMensajeDesconexion(PeerInfo peerCaido) {
        JsonObject json = new JsonObject();
        json.addProperty("tipoEvento", "PEER_DESCONECTADO");
        json.addProperty("id", ListaPeers.obtenerKey(peerCaido));
        return json.toString();
    }
}