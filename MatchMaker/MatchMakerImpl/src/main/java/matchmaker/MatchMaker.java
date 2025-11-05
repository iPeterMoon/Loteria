package matchmaker;

import java.util.LinkedList;
import java.util.List;
import interfaces.IPeer;
import util.ConfigLoader;


/**
 *
 * @author Jp
 */
public class MatchMaker {

    private int PUERTO = ConfigLoader.getInstance().getPuertoDiscovery();
    private final List<Sala> salas;
    private final IPeer peer;

    public MatchMaker(IPeer peer){ 
        this.salas = new LinkedList<>();
        this.peer = peer;
    }

}
