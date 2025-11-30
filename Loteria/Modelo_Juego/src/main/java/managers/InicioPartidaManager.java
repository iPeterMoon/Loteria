package managers;

import interfaces.IPeer;

public class InicioPartidaManager {

    private IPeer componentePeer;

    public void inicializar(IPeer peer) {
        if (this.componentePeer != null) {
            return;
        }
        this.componentePeer = peer;
    }


}
