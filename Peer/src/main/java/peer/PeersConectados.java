package peer;

import dtos.PeerInfo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

/**
 *
 * @author pedro
 */
public class PeersConectados {
    
    private static PeersConectados instance;
    
    private final Map<String, PeerInfo> listaPeers;
    private PeerInfo myInfo;
    
    private PeersConectados(){
        this.listaPeers = new ConcurrentHashMap<>();
    }
    
    public static PeersConectados getInstance(){
        if(instance == null){
            synchronized (PeersConectados.class) {
                if (instance == null) {
                    instance = new PeersConectados();
                }
            }
        }
        return instance;
    }
    
    public void setMyInfo(PeerInfo myInfo){
        this.myInfo = myInfo;
    }
    
    /**
     * Metodo para registrar un Peer en la lista de peers conectados.
     * @param info
     */
    public synchronized void registrarPeer(PeerInfo info){
        if(info != null) {
            if(!esMismoPeer(info, this.myInfo)){
                listaPeers.put(generarClave(info), info);
                System.out.println("Se Unió un nuevo Peer! ["+info.getUser()+"@"+info.getIp()+":"+info.getPort()+"]");
            }
        } else {
            System.err.println("Se intentó procesar un Peer nulo");
        }
    }
    
    /**
     * Metodo para eliminar un peer de la lista de peers.
     * @param info Peer a eliminar
     */
    public synchronized void eliminarPeer(PeerInfo info){
        String clave = generarClave(info);
        if(listaPeers.containsKey(clave)){
            listaPeers.remove(clave);
        }
    }
    
    /**
     * Metodo para generar la clave de un peer 
     * @param info DTO con la info del peer
     * @return String con la clave de identificacion.
     */
    private synchronized String generarClave(PeerInfo info){
        return info.getIp() + ":" + info.getPort();
    }

    /**
     * Metodo para checar si dos peers son iguales
     * @param peer1 Primer peer a comparar
     * @param peer2 Segundo peer a comparar
     * @return true si son el mismo, false si no.
     */
    private boolean esMismoPeer(PeerInfo peer1, PeerInfo peer2){
        
        if (peer1 == null || peer2 ==null){
            return false;
        }

        return generarClave(peer1).equals(generarClave(peer2));
    }

    /**
     * Metodo para ejecutar un metodo para todos los peers conectados (puede ser un broadcast)
     * @param accion 
     */
    public void ejecutarEnTodos(String mensaje, BiConsumer<PeerInfo, String> accion) {
        for (PeerInfo peer : listaPeers.values()) {
            accion.accept(peer, mensaje);
        }
    }
    
    
}
