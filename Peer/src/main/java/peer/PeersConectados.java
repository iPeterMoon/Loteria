package peer;

import dtos.PeerInfo;

import java.util.HashMap;
import java.util.Map;
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
        this.listaPeers = new HashMap<>();
    }
    
    public static PeersConectados getInstance(){
        if(instance == null){
            instance = new PeersConectados();
        }
        return instance;
    }
    
    public void setMyInfo(PeerInfo myInfo){
        this.myInfo = myInfo;
    }
    
    public void registrarPeer(PeerInfo info){
        if(!info.equals(myInfo)){
            listaPeers.put(generarClave(info), info);
            System.out.println("Se Unió un nuevo Peer! ["+info.getIp()+":"+info.getPort()+"]");
        }
    }
    
    public void eliminarPeer(PeerInfo info){
        if(listaPeers.containsKey(generarClave(info))){
            listaPeers.remove(generarClave(info));
        }
    }
    
    private String generarClave(PeerInfo info){
        return info.getIp() + ":" + info.getPort();
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
