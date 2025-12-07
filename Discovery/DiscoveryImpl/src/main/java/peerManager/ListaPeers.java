package peerManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

import dtos.peer.PeerInfo;

public class ListaPeers {
// Mapa privado de peers conectados (sin tiempos)
    private static final Map<String, PeerInfo> peersVivos = new ConcurrentHashMap<>();

    public static String obtenerKey(PeerInfo peer) {
        return peer.getIp() + ":" + peer.getPort();
    }

    /**
     * Metodo para agregar un Peer a la lista de Peers
     */
    public static void registrarPeer(PeerInfo peer) {
        peersVivos.put(obtenerKey(peer), peer);
        System.out.println("[ListaPeers] Peer agregado: " + peer.getUser()+"@"+obtenerKey(peer));
    }

    /**
     * Metodo para eliminar un peer de la lista de peers
     */
    public static PeerInfo eliminarPeer(String key) {
        return peersVivos.remove(key);
    }

    /**
     * Metodo para enviar mensajes a todos sin exponer el mapa
     */
    public static void ejecutarEnPeersVivos(BiConsumer<String, PeerInfo> accion) {
        peersVivos.forEach(accion);
    }
}