package modelo;

import dtos.JugadorSalaEsperaDTO;
import interfaces.IModeloVistaConfiguracion;
import interfaces.IObserver;
import java.util.List;
import vista.ModelObserver;
import vista.ModelObserverConfig;

/**
 *
 * @author norma
 */
public class ModeloVistaConfiguracionFacade implements IModeloVistaConfiguracion {

    private static ModeloVistaConfiguracionFacade instancia;
    private SalaSubject salaSubject;
    private ConfiguracionSubject configuracionSubject;
    private AvatarSubject avatarSubject;
    private List<JugadorSalaEsperaDTO> jugadores;
    private IObserver observer = new ModelObserverConfig();

    public static ModeloVistaConfiguracionFacade getInstance() {
        if (instancia == null) {
            instancia = new ModeloVistaConfiguracionFacade();
        }
        return instancia;
    }

    private ModeloVistaConfiguracionFacade() {
        configurarAvatarSubject();
        configurarSalaSubject();
    }

    @Override
    public void actualizarSala(List<JugadorSalaEsperaDTO> jugadores) {
        this.salaSubject.setJugadores(jugadores);
    }

    @Override
    public void obtenerDatosDeLaSala(List<JugadorSalaEsperaDTO> jugadores, String nivel, int limiteJugadores) {
        this.salaSubject.setJugadores(jugadores);
        this.salaSubject.setNivel(nivel);
        this.salaSubject.setLimiteJugadores(limiteJugadores);
    }

    private void configurarAvatarSubject() {
        avatarSubject = new AvatarSubject();
        avatarSubject.addObserver(observer);
    }
    
    private void configurarSalaSubject() {
        this.salaSubject = new SalaSubject();
        this.salaSubject.addObserver(observer);
    }

    public void actualizarAvatar(int direccion) {
        if (direccion == -1) {
            avatarSubject.moverIzquierda();
        } else {
            avatarSubject.moverDerecha();
        }

    }

}