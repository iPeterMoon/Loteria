package managers;

import dtos.aplicacion.NuevoUsuarioDTO;
import eventos.eventos_aplicacion.EventoUnirseSala;
import interfaces.IPeer;
import util.ConfigLoader;

/**
 *
 * @author norma
 */
public class UnirsePartidaManager {

    private IPeer componentePeer;

    public void inicializar(IPeer peer) {
        if (this.componentePeer != null) {
            return;
        }
        this.componentePeer = peer;
    }

    public void unirseSala(NuevoUsuarioDTO usuario) {
        componentePeer.setUser(usuario.getNickname());
        EventoUnirseSala eventoUnirsePartida = new EventoUnirseSala(usuario.getNickname(), usuario);
        componentePeer.directMessage(eventoUnirsePartida, ConfigLoader.getInstance().getUsuarioMatchmaker());
    }

    public void obtenerInfoSala() {
        //como le mandara el evento al match si no tiene usuario?
//        EventoPeticionInfoSala eventoPeticionInfoSala = new EventoPeticionInfoSala(?????????????);
//        componentePeer.directMessage(eventoPeticionInfoSala, "MATCHMAKER");
    }
}
