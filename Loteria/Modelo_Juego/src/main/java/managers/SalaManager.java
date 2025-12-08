package managers;

import dtos.aplicacion.JugadorDTO;
import dtos.aplicacion.NuevoUsuarioDTO;
import eventos.eventos_aplicacion.EventoSalirSalaEspera;
import eventos.eventos_aplicacion.EventoUnirseSala;
import interfaces.IPeer;
import modelo.Sala;
import util.ConfigLoader;

/**
 *
 * @author norma
 */
public class SalaManager {

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
    
        public void abandonarSala(){
        EventoSalirSalaEspera eventoSalirSalaEspera = new EventoSalirSalaEspera(Sala.getInstance().getJugadorPrincipal().getNickname());
        componentePeer.directMessage(eventoSalirSalaEspera, ConfigLoader.getInstance().getUsuarioMatchmaker());
    }

}
