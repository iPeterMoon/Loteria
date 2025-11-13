package discovery;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

import dtos.PeerInfo;

public class ListaPeers {
    
    private static final Map<String, PeerInfo> peersVivos = new ConcurrentHashMap<>();
    private static final Map<String, Long> ultimaVezVistos = new ConcurrentHashMap<>();

    /**
     * Metodo publico para registrar un peer en el servidor
     * @param peer Peer a registrar
     */
    public static void registrarPeer(PeerInfo peer){
        registrarPeer(obtenerKey(peer), peer);
    }

    /**
     * Metodo para obtener la llave de un peer
     * @param peer Peer del que se obtendrá la llave
     * @return String con la llave del peer
     */
    public static String obtenerKey(PeerInfo peer) {
        return peer.getIp() + ":" +  peer.getPort();
    }

    /**
     * Metodo para actualizar la ultima vez que se vio a un Peer
     * @param peer Peer visto
     */
    public static void setUltimaVezVisto(PeerInfo peer){
        String key = obtenerKey(peer);
        if (peersVivos.containsKey(key)) {
            ultimaVezVistos.put(key, System.currentTimeMillis());
        }
    }

    /**
     * Metodo privado que registra el peer en la lista del discovery
     * @param key Llave identificadora del peer
     * @param peer Peer a registrar 
     */
    private static void registrarPeer(String key, PeerInfo peer) {
        peersVivos.put(key, peer);
        ultimaVezVistos.put(key, System.currentTimeMillis());
        System.out.println("[DiscoveryServer] Peer registrado: " + key + " @ " + peer.getIp() + ":" + peer.getPort());
    }

    /**
     * Metodo para ejecutar un metodo para todos los peers conectados (puede ser un broadcast)
     * @param mensaje Mensaje a enviar
     * @param accion Acción o metodo a ejecutar
     */
    public static void ejecutarEnPeersVivos(BiConsumer<String, PeerInfo> accion) {
        peersVivos.forEach(accion);
    }

    /**
     * Limpia los peers que han superado el tiempo de timeout.
     * Esta lógica elimina peers de AMBOS mapas (ultimaVezVistos y peersVivos).
     * @param HEARTBEAT_TIMEOUT_MS El tiempo en ms para considerar a un peer muerto.
     */
    public static void limpiarPeersMuertos(long HEARTBEAT_TIMEOUT_MS) {
        long ahora = System.currentTimeMillis();
        
        ultimaVezVistos.entrySet().removeIf(e -> {
            if (ahora - e.getValue() > HEARTBEAT_TIMEOUT_MS) {
                String peerKey = e.getKey();
                PeerInfo muerto = peersVivos.remove(peerKey); 
                if (muerto != null) {
                    System.out.println("[DiscoveryServer] Peer eliminado (timeout " + HEARTBEAT_TIMEOUT_MS + " ms): " + peerKey);
                }
                return true;
            }
            return false;
        });
    }

}
