package peer;

import dtos.PeerInfo;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 *
 * @author pedro
 */
public class PeersConectados {
    
    private static PeersConectados instance;
    
    private final List<PeerInfo> listaPeers;
    private PeerInfo myInfo;
    
    private PeersConectados(){
        this.listaPeers = new LinkedList<>();
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
            listaPeers.add(info);
        }
    }
    
    public void eliminarPeer(PeerInfo info){
        if(listaPeers.contains(info)){
            listaPeers.remove(info);
        }
    }
    
    /**
     * Metodo para ejecutar un metodo para todos los peers conectados (puede ser un broadcast)
     * @param accion 
     */
    public void ejecutarEnTodos(String mensaje, BiConsumer<PeerInfo, String> accion) {
        for (PeerInfo peer : listaPeers) {
            accion.accept(peer, mensaje);
        }
    }
    
    
}
