package managers;

import dtos.aplicacion.NuevoUsuarioDTO;
import eventos.eventos_aplicacion.EventoSalirSalaEspera;
import eventos.eventos_aplicacion.EventoUnirseSala;
import interfaces.IPeer;
import interfaces.IModeloVistaConfiguracion;
import modelo.Jugador;
import modelo.ModeloJuegoFacade;
import modelo.Sala;
import util.ConfigLoader;

/**
 *
 * @author norma
 */
public class SalaManager {

    private IPeer componentePeer;
    private IModeloVistaConfiguracion modeloVistaConfiguracion;

    public void inicializar(IPeer peer, IModeloVistaConfiguracion modeloVistaConfiguracion) {
        if (this.componentePeer != null) {
            return;
        }
        this.componentePeer = peer;
        this.modeloVistaConfiguracion = modeloVistaConfiguracion;
    }

    public void unirseSala(NuevoUsuarioDTO usuario) {
        componentePeer.setUser(usuario.getNickname());
        Jugador jugadorPrincipal = new Jugador(usuario.getNickname(), usuario.getIdAvatarSeleccionado(), 0, null);
        Sala.getInstance().setJugadorPrincipal(jugadorPrincipal);
        
        // Actualizar SalaSubject a través de la fachada para que la UI se entere
        if (modeloVistaConfiguracion != null) {
            System.out.println("[SalaManager] Actualizando jugadorPrincipal: " + usuario.getNickname());
            modeloVistaConfiguracion.actualizarJugadorPrincipal(usuario.getNickname());
        } else {
            System.err.println("[SalaManager] modeloVistaConfiguracion es NULL!");
        }
        
        EventoUnirseSala eventoUnirsePartida = new EventoUnirseSala(usuario.getNickname(), usuario);
        componentePeer.directMessage(eventoUnirsePartida, ConfigLoader.getInstance().getUsuarioMatchmaker());
    }
    
    public void abandonarSala(){
        EventoSalirSalaEspera eventoSalirSalaEspera = new EventoSalirSalaEspera(Sala.getInstance().getJugadorPrincipal().getNickname());
        ModeloJuegoFacade.getInstance().setJugadorPrincipal(null);
        componentePeer.directMessage(eventoSalirSalaEspera, ConfigLoader.getInstance().getUsuarioMatchmaker());
        this.componentePeer.setUser(null);
    }

}

