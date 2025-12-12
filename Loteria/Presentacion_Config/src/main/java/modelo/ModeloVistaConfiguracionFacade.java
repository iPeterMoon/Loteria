package modelo;

import enums.Pantalla;
import dtos.aplicacion.JugadorSalaEsperaDTO;
import dtos.aplicacion.MensajeDTO;
import enums.TipoConfiguracion;
import enums.TipoNivel;
import interfaces.aplicacion.IModeloVistaConfiguracion;
import util.IObserver;
import java.util.List;
import ventanas.FrameConfiguracion;
import vista.ModelObserverConfig;

/**
 * Clase que implementa los patrones Facade y Singleton.
 *
 * Actúa como la fachada principal del modelo de la aplicación, centralizando la
 * gestión de todos los Subjects (objetos que mantienen el estado observable
 * para la vista) relacionados con la configuración, la sala y la navegación de
 * la UI. Implementa IModeloVistaConfiguracion para proporcionar una interfaz
 * limpia y única al resto del Modelo (Managers, Controles de Negocio) para
 * actualizar el estado de la Vista.
 *
 * @author norma
 */
public class ModeloVistaConfiguracionFacade implements IModeloVistaConfiguracion {

    /**
     * Instancia única de la fachada (Singleton).
     */
    private static ModeloVistaConfiguracionFacade instancia;

    /**
     * Sujeto que gestiona el estado observable de la sala de juego.
     */
    private SalaSubject salaSubject;

    /**
     * Sujeto que gestiona el modo de configuración actual (crear/unirse).
     */
    private ConfiguracionSubject configuracionSubject;

    /**
     * Sujeto que gestiona el número de avatar seleccionado.
     */
    private AvatarSubject avatarSubject;

    /**
     * Sujeto que gestiona los mensajes y notificaciones de la aplicación.
     */
    private MensajeSubject mensajeSubject;

    /**
     * Sujeto que gestiona la pantalla actual de la UI para la navegación.
     */
    private PantallaActualSubject pantallaSubject;

    /**
     * Lista de jugadores en la sala (no utilizado como Subject, sino como
     * variable de apoyo).
     */
    private List<JugadorSalaEsperaDTO> jugadores;

    /**
     * El observador principal que escucha los cambios de todos los Subjects
     * para actualizar la vista.
     */
    private IObserver observer = new ModelObserverConfig();

    /**
     * Devuelve la instancia única de la fachada (Singleton). Si la instancia no
     * existe, la crea antes de devolverla.
     *
     * @return La instancia única de ModeloVistaConfiguracionFacade.
     */
    public static ModeloVistaConfiguracionFacade getInstance() {
        if (instancia == null) {
            instancia = new ModeloVistaConfiguracionFacade();
        }
        return instancia;
    }

    /**
     * Constructor privado que inicializa todos los Subjects y configura sus
     * observadores.
     */
    private ModeloVistaConfiguracionFacade() {
        configurarSubjects();
    }

    /**
     * Obtiene el Sujeto responsable de gestionar la selección del avatar.
     *
     * @return El AvatarSubject.
     */
    public AvatarSubject getAvatarSubject() {
        return avatarSubject;
    }

    /**
     * Actualiza la lista de jugadores que se encuentran actualmente en la sala
     * y notifica al SalaSubject.
     *
     * @param jugadores La lista actualizada de jugadores en la sala.
     */
    @Override
    public void actualizarJugadoresSala(List<JugadorSalaEsperaDTO> jugadores) {
        salaSubject.setJugadores(jugadores);
    }

    /**
     * Actualiza los datos de la sala como el host, el límite de jugadores y el
     * nivel de dificultad, y notifica al SalaSubject.
     *
     * @param host El nombre de usuario del anfitrión.
     * @param limiteJugadores El número máximo de jugadores permitidos.
     * @param nivel El tipo de dificultad de la partida.
     */
    @Override
    public void actualizarDatosSala(String host, int limiteJugadores, TipoNivel nivel) {
        salaSubject.actualizarDatosSala(host, limiteJugadores, nivel);
    }

    /**
     * Inicializa y enlaza todos los Subjects con el observador principal.
     */
    private void configurarSubjects() {
        configurarSalaSubject();
        configurarAvatarSubject();
        configurarPantallaActualSubject();
        configurarMensajeSubject();
        configurarConfiguracionSubject();
    }

    /**
     * Inicializa el ConfiguracionSubject y le añade el observador.
     */
    private void configurarConfiguracionSubject() {
        this.configuracionSubject = new ConfiguracionSubject();
        this.configuracionSubject.addObserver(observer);
    }

    /**
     * Inicializa el SalaSubject (Singleton) y le añade el observador.
     */
    private void configurarSalaSubject() {
        this.salaSubject = SalaSubject.getInstance();
        this.salaSubject.addObserver(this.observer);
    }

    /**
     * Inicializa el MensajeSubject y le añade el observador.
     */
    private void configurarMensajeSubject() {
        this.mensajeSubject = new MensajeSubject();
        this.mensajeSubject.addObserver(observer);
    }

    /**
     * Inicializa el AvatarSubject y le añade el observador.
     */
    private void configurarAvatarSubject() {
        avatarSubject = new AvatarSubject();
        avatarSubject.addObserver(observer);
    }

    /**
     * Inicializa el PantallaActualSubject y le añade el observador.
     */
    private void configurarPantallaActualSubject() {
        pantallaSubject = new PantallaActualSubject();
        pantallaSubject.addObserver(observer);
    }

    /**
     * Mueve la selección del avatar a la izquierda o a la derecha delegando la
     * acción al AvatarSubject.
     *
     * @param direccion -1 para izquierda, cualquier otro valor para derecha.
     */
    public void actualizarAvatar(int direccion) {
        if (direccion == -1) {
            avatarSubject.moverIzquierda();
        } else {
            avatarSubject.moverDerecha();
        }
    }

    /**
     * Actualiza la pantalla actual de la UI delegando la acción al
     * PantallaActualSubject.
     *
     * @param pantalla El enumerador Pantalla que representa la nueva vista.
     */
    public void actualizarPantalla(Pantalla pantalla) {
        pantallaSubject.setPantallaActual(pantalla);
    }

    /**
     * Actualiza el mensaje de notificación delegando la acción al
     * MensajeSubject.
     *
     * @param mensaje El DTO con los datos del mensaje a mostrar.
     */
    @Override
    public void actualizarMensaje(MensajeDTO mensaje) {
        mensajeSubject.actualizarMensaje(mensaje);
    }

    /**
     * Actualiza el tipo de configuración (crear sala, unirse a sala) delegando
     * la acción al ConfiguracionSubject.
     *
     * @param tipoConfiguracion El TipoConfiguracion actual.
     */
    @Override
    public void actualizarTipoConfiguracion(TipoConfiguracion tipoConfiguracion) {
        configuracionSubject.setTipoConfiguracion(tipoConfiguracion);
    }

    /**
     * Actualiza el nombre de usuario del jugador principal (host) en el
     * SalaSubject.
     *
     * @param user El nombre de usuario del anfitrión.
     */
    @Override
    public void actualizarJugadorPrincipal(String user) {
        salaSubject.setJugadorPrincipalUser(user);
    }

    /**
     * Envía la señal para cerrar la ventana principal de la aplicación.
     */
    @Override
    public void cerrarVentana() {
        pantallaSubject.setPantallaActual(Pantalla.CERRAR);
    }

    /**
     * Hace visible la ventana principal y navega a la pantalla de Sala de
     * Espera.
     */
    @Override
    public void mostrarSalaEspera() {
        FrameConfiguracion.getInstancia().setVisible(true);
        pantallaSubject.setPantallaActual(Pantalla.SALA_ESPERA);
    }

    /**
     * Hace visible la ventana principal y navega a la pantalla del Menú
     * Principal.
     */
    @Override
    public void mostrarMenuPrincipal() {
        FrameConfiguracion.getInstancia().setVisible(true);
        pantallaSubject.setPantallaActual(Pantalla.MENU);
    }
}
