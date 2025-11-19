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
// Mapa privado de peers conectados (sin tiempos)
    private static final Map<String, PeerInfo> peersVivos = new ConcurrentHashMap<>();

    public static String obtenerKey(PeerInfo peer) {
        return peer.getIp() + ":" + peer.getPort();
    }

    /**
     * METODO AUXILIAR: Registrar
     * (Solo mete el dato al mapa, no piensa)
     */
    public static void registrarPeer(String key, PeerInfo peer) {
        peersVivos.put(key, peer);
        System.out.println("[ListaPeers] Peer agregado: " + key);
    }

    /**
     * METODO AUXILIAR: Eliminar
     * (Solo saca el dato del mapa, no piensa)
     * PeerCleaner llama a esto para ejecutar su decisión.
     */
    public static PeerInfo eliminarPeer(String key) {
        return peersVivos.remove(key);
    }

    /**
     * METODO AUXILIAR: Iterar
     * (Para enviar mensajes a todos sin exponer el mapa)
     */
    public static void ejecutarEnPeersVivos(BiConsumer<String, PeerInfo> accion) {
        peersVivos.forEach(accion);
    }
}