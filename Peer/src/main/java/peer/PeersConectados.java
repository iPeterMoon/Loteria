package peer;

import dtos.peer.PeerInfo;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

/**
 * Clase que actúa como un registro central de peers conocidos por el peer
 * local.
 *
 * @author pedro
 */
public class PeersConectados {

    /**
     * Instancia única del registro de peers.
     */
    private static PeersConectados instance;

    /**
     * Mapa de peers conectados, indexados por su clave ip:puerto.
     */
    private final Map<String, PeerInfo> listaPeers;
    
    /**
     * Información del peer local.
     */
    private PeerInfo myInfo;

    /**
     * Constructor privado. Inicializa la estructura de almacenamiento de peers.
     */
    private PeersConectados() {
        this.listaPeers = new ConcurrentHashMap<>();
    }

    /**
     * Obtiene la instancia única de la clase.
     * @return instancia de PeersConectados.
     */
    public static PeersConectados getInstance() {
        if (instance == null) {
            synchronized (PeersConectados.class) {
                if (instance == null) {
                    instance = new PeersConectados();
                }
            }
        }
        return instance;
    }

    /**
     * Registra la información del peer local.
     * @param myInfo Info del peer.
     */
    public void setMyInfo(PeerInfo myInfo) {
        this.myInfo = myInfo;
    }

    /**
     * Obtiene la información del Peer local.
     *
     * @return PeerInfo del Peer que está ejecutando este código.
     */
    public PeerInfo getMyInfo() {
        return this.myInfo;
    }

    /**
     * Metodo para registrar un Peer en la lista de peers conectados.
     *
     * @param info
     */
    public synchronized void registrarPeer(PeerInfo info) {
        if (info != null) {
            if (!esMismoPeer(info, this.myInfo)) {
                listaPeers.put(generarClave(info), info);
                imprimirListaPeers();
            }
        } else {
            System.err.println("Se intentó procesar un Peer nulo");
        }
    }

    /**
     * Metodo para eliminar un peer de la lista de peers.
     *
     * @param info Peer a eliminar
     */
    public synchronized void eliminarPeer(PeerInfo info) {
        String clave = generarClave(info);
        if (listaPeers.containsKey(clave)) {
            listaPeers.remove(clave);
        }
    }

    /**
     * Metodo para generar la clave de un peer
     *
     * @param info DTO con la info del peer
     * @return String con la clave de identificacion.
     */
    private synchronized String generarClave(PeerInfo info) {
        return info.getIp() + ":" + info.getPort();
    }

    /**
     * Metodo para checar si dos peers son iguales
     *
     * @param peer1 Primer peer a comparar
     * @param peer2 Segundo peer a comparar
     * @return true si son el mismo, false si no.
     */
    private boolean esMismoPeer(PeerInfo peer1, PeerInfo peer2) {

        if (peer1 == null || peer2 == null) {
            return false;
        }

        return generarClave(peer1).equals(generarClave(peer2));
    }

    /**
     * Metodo para ejecutar un metodo para todos los peers conectados (puede ser
     * un broadcast)
     *@param mensaje mensaje asociado a la acción
     * @param accion función a ejecutar por cada peer
     */
    public void ejecutarEnTodos(String mensaje, BiConsumer<PeerInfo, String> accion) {
        for (PeerInfo peer : listaPeers.values()) {
            accion.accept(peer, mensaje);
        }
    }

    /**
     * Obtiene la información de un peer por su nombre de usuario
     *
     * @param user Nombre de usuario del peer a buscar
     * @return PeerInfo del peer si existe, null si no se encuentra
     */
    public PeerInfo obtenerPeerPorUsuario(String user) {
        if (user == null || user.isEmpty()) {
            return null;
        }
        for (PeerInfo peer : listaPeers.values()) {
            if (user.equals(peer.getUser())) {
                return peer;
            }
        }
        return null;
    }

    /**
     * Obtiene una colección de todos los peers conectados
     *
     * @return Colección de PeerInfo de todos los peers conectados
     */
    public Collection<PeerInfo> obtenerTodosLosPeers() {
        return listaPeers.values();
    }

    /**
     * Imprime la lista de peers conectados.
     */
    private void imprimirListaPeers() {
        for (PeerInfo peer : listaPeers.values()) {
            System.out.println(peer.getUser() + "@" + peer.getIp() + ":" + peer.getPort());
        }
    }
}
