package procesadores_peer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dtos.PeerInfo;
import enums.TipoEvento;
import eventos.eventos_peers.EventoPeerDesconectado;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import peer.Peer;
import peer.PeersConectados;

/**
 * Manejador que procesa el evento de desconexión de un Peer. Ejecuta la lógica
 * de elección de nuevo Host (Host Migration).
 *
 * @author rocha
 */
public class ManejadorEventoDesconexion extends ManejadorMensajesLlegada {

    private final Gson gson = new Gson();

    @Override
    public void procesar(JsonObject json) {
        // Validación de existencia de tipoEvento
        if (!json.has("tipoEvento")) {
            if (next != null) {
                next.procesar(json);
            }
            return;
        }

        String tipo = json.get("tipoEvento").getAsString();

        if (TipoEvento.PEER_DESCONECTADO.name().equals(tipo)) {
            procesarDesconexion(json);
        } else if (next != null) {
            next.procesar(json);
        }
    }

    private void procesarDesconexion(JsonObject json) {
        PeersConectados peersConectados = PeersConectados.getInstance();

        try {
            // Gson mapea el JSON completo al DTO EventoPeerDesconectado
            EventoPeerDesconectado evento = gson.fromJson(json, EventoPeerDesconectado.class);
            PeerInfo peerDesconectado = evento.getPeerDesconectado();

            System.out.println(">>> [RED] Aviso de desconexión recibido: " + peerDesconectado.getUser());

            //  Eliminar el Peer de la lista local
            peersConectados.eliminarPeer(peerDesconectado);

            //  Ejecutar la lógica de Host Migration
            PeerInfo nuevoHost = elegirNuevoHost(peersConectados.obtenerTodosLosPeers());
            PeerInfo miInfo = peersConectados.getMyInfo();
            //  Determinar si el Peer local es el nuevo Host
            if (miInfo.equals(nuevoHost)) {
                // Acción Real: Llama al método del Peer Singleton para el cambio de rol.
                Peer.getInstance().promoverseAHost(miInfo);
            } else if (nuevoHost != null) {
                System.out.println(">>> [ELECCIÓN] Nuevo Host electo: " + nuevoHost.getUser());
            } else {
                System.out.println(">>> [FIN] Soy el último Peer. Partida terminada.");
            }
        } catch (Exception e) {
            // Manejo de errores de JSON o lógica de PeersConectados
            System.err.println("[ManejadorDesconexion] Error al procesar evento: " + e.getMessage());
        }

    }

    /**
     * Implementa el algoritmo de elección de nuevo Host basado en la IP y el
     * Puerto
     *
     * @param peers La colección de Peers activos restantes
     * @return PeerInfo del nuevo Host electo
     */
    private PeerInfo elegirNuevoHost(Collection<PeerInfo> peers) {
        if (peers == null || peers.isEmpty()) {
            return null;
        }

        // Usamos el stream para encontrar el mínimo Peer basado en IP y Puerto
        PeerInfo nuevoHost = peers.stream()
                .min(Comparator
                        // 1. Criterio principal: IP (como String, compara lexicográficamente)
                        .<PeerInfo, String>comparing(PeerInfo::getIp)
                        // 2. Criterio de desempate: Puerto (como int)
                        .thenComparingInt(PeerInfo::getPort))
                .orElse(null);

        return nuevoHost;
    }

}
