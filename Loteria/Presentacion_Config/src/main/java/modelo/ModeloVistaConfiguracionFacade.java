package modelo;

import dtos.aplicacion.JugadorSalaEsperaDTO;
import enums.Pantalla;
import dtos.JugadorSalaEsperaDTO;
import enums.TipoNivel;
import interfaces.IModeloVistaConfiguracion;
import interfaces.IObserver;
import java.util.List;
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
    private PantallaActualSubject pantallaSubject;
    private List<JugadorSalaEsperaDTO> jugadores;
    private IObserver observer = new ModelObserverConfig();

    public static ModeloVistaConfiguracionFacade getInstance() {
        if (instancia == null) {
            instancia = new ModeloVistaConfiguracionFacade();
        }
        return instancia;
    }

    private ModeloVistaConfiguracionFacade() {
        configurarSubjects();
        configurarAvatarSubject();
        configurarSalaSubject();
    }

    //Son iguales??
    @Override
    public void actualizarSala(List<JugadorSalaEsperaDTO> jugadores) {
        this.salaSubject.setJugadores(jugadores);
    }
    /// -->>
    @Override
    public void actualizarJugadoresSala(List<JugadorSalaEsperaDTO> jugadores) {
        salaSubject.setJugadores(jugadores);
    }

    @Override
    public void actualizarDatosSala(int limiteJugadores, TipoNivel nivel) {
        salaSubject.actualizarDatosSala(limiteJugadores, nivel);
    }

    private void configurarSubjects() {
        configurarSalaSubject();
        configurarAvatarSubject();
        configurarPantallaActualSubject();
    }

    private void configurarSalaSubject() {
        this.salaSubject = new SalaSubject();
        this.salaSubject.addObserver(this.observer);
    }

    private void configurarAvatarSubject() {
        avatarSubject = new AvatarSubject();
        avatarSubject.addObserver(observer);
    }

    private void configurarPantallaActualSubject() {
        pantallaSubject = new PantallaActualSubject();
        pantallaSubject.addObserver(observer);
    }

    public void actualizarAvatar(int direccion) {
        if (direccion == -1) {
            avatarSubject.moverIzquierda();
        } else {
            avatarSubject.moverDerecha();
        }
    }

    public void actualizarPantalla(Pantalla pantalla) {
        pantallaSubject.setPantallaActual(pantalla);
    }

}
