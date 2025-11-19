package peerManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.List;
import java.util.ArrayList;

import dtos.PeerInfo;

public class ListaPeers {

    private static final Map<String, PeerInfo> peersVivos = new ConcurrentHashMap<>();
    private static final Map<String, Long> ultimaVezVistos = new ConcurrentHashMap<>();

    /**
     * Obtiene una lista de todos los peers vivos.
     * 
     * @return Una lista de PeerInfo.
     */
    public static List<PeerInfo> getAllPeers() {
        // Devuelve una nueva lista para evitar problemas de concurrencia al iterar
        return new ArrayList<>(peersVivos.values());
    }

    /**
     * Limpia peers muertos Y DEVUELVE la lista de los que eliminó.
     * Reemplaza al antiguo limpiarPeersMuertos
     *
     * @param HEARTBEAT_TIMEOUT_MS El tiempo en ms para considerar a un peer muerto.
     * @return Una lista de PeerInfo que fueron eliminados.
     */
    public static List<PeerInfo> limpiarPeersMuertos(long HEARTBEAT_TIMEOUT_MS) {
        long ahora = System.currentTimeMillis();
        List<PeerInfo> peersEliminados = new ArrayList<>();

        ultimaVezVistos.entrySet().removeIf(e -> {
            if (ahora - e.getValue() > HEARTBEAT_TIMEOUT_MS) {
                String peerKey = e.getKey();

                // Primero removemos de peersVivos
                PeerInfo muerto = peersVivos.remove(peerKey);

                if (muerto != null) {
                    System.out.println("[DiscoveryServer] Peer eliminado (timeout): " + peerKey);
                    peersEliminados.add(muerto);
                }
                // Retornar true elimina de ultimaVezVistos
                return true;
            }
            return false;
        });
        return peersEliminados; // <-- La clave del cambio
    }

    /**
     * Metodo publico para registrar un peer en el servidor
     * 
     * @param peer Peer a registrar
     */
    public static void registrarPeer(PeerInfo peer) {
        registrarPeer(obtenerKey(peer), peer);
    }

    /**
     * Metodo para obtener la llave de un peer
     * 
     * @param peer Peer del que se obtendrá la llave
     * @return String con la llave del peer
     */
    public static String obtenerKey(PeerInfo peer) {
        return peer.getIp() + ":" + peer.getPort();
    }


    /**
     * Metodo privado para registrar un peer en el servidor
     * 
     * @param key  Llave del peer
     * @param peer Peer a registrar
     */
    private static void registrarPeer(String key, PeerInfo peer) {
        peersVivos.put(key, peer);
        ultimaVezVistos.put(key, System.currentTimeMillis());
        System.out.println("[ListaPeers] Peer registrado: " + key);
    }
    /**
     * Metodo para actualizar la ultima vez que se vio a un Peer
     * 
     * @param peer Peer visto
     */
    public static void setUltimaVezVisto(PeerInfo peer) {
        String key = obtenerKey(peer);
        if (peersVivos.containsKey(key)) {
            ultimaVezVistos.put(key, System.currentTimeMillis());
        }
    }

    /**
     * Devuelve una COPIA del mapa de tiempos.
     * PeerCleaner usará esto para iterar y calcular quién expiró.
     */
    public static Map<String, Long> getMapaUltimaVezVistos() {
        return new ConcurrentHashMap<>(ultimaVezVistos);
    }

    /**
     * Devuelve la lista de vivos para poder hacer el broadcast.
     */
    public static List<PeerInfo> getPeersVivos() {
        return new ArrayList<>(peersVivos.values());
    }

    /**
     * Elimina un peer específico.
     * PeerCleaner llamará a esto cuando SU lógica decida que el peer expiró.
     */
    public static PeerInfo eliminarPeer(String key) {
        ultimaVezVistos.remove(key); // Borrar del mapa de tiempos
        PeerInfo eliminado = peersVivos.remove(key); // Borrar del mapa de vivos
        
        if (eliminado != null) {
            System.out.println("[ListaPeers] Eliminando peer de la lista: " + key);
        }
        return eliminado;
    }


   
}