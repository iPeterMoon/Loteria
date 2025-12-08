package modelo;

import enums.Pantalla;
import dtos.aplicacion.JugadorSalaEsperaDTO;
import dtos.aplicacion.MensajeDTO;
import enums.TipoConfiguracion;
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
    private MensajeSubject mensajeSubject;
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
    
    public AvatarSubject getAvatarSubject() {
        return avatarSubject;
    }

//    //Son iguales??
//    public void actualizarSala(List<JugadorSalaEsperaDTO> jugadores) {
//        this.salaSubject.setJugadores(jugadores);
//    }
    /// -->
    /// @param jugadores>
    @Override
    public void actualizarJugadoresSala(List<JugadorSalaEsperaDTO> jugadores) {
        salaSubject.setJugadores(jugadores);
    }
    
    @Override
    public void actualizarDatosSala(String host, int limiteJugadores, TipoNivel nivel) {
        salaSubject.actualizarDatosSala(host, limiteJugadores, nivel);
    }
    
    private void configurarSubjects() {
        configurarSalaSubject();
        configurarAvatarSubject();
        configurarPantallaActualSubject();
        configurarMensajeSubject();
        configurarConfiguracionSubject();
    }
    
    private void configurarConfiguracionSubject() {
        this.configuracionSubject = new ConfiguracionSubject();
        this.configuracionSubject.addObserver(observer);
    }
    
    private void configurarSalaSubject() {
        this.salaSubject = SalaSubject.getInstance();
        this.salaSubject.addObserver(this.observer);
    }
    
    private void configurarMensajeSubject() {
        this.mensajeSubject = new MensajeSubject();
        this.mensajeSubject.addObserver(observer);
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
    
    @Override
    public void actualizarMensaje(MensajeDTO mensaje) {
        mensajeSubject.actualizarMensaje(mensaje);
    }
    
    @Override
    public void actualizarTipoConfiguracion(TipoConfiguracion tipoConfiguracion) {
        configuracionSubject.setTipoConfiguracion(tipoConfiguracion);
    }
    
}
