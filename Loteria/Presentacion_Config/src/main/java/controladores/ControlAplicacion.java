package controladores;

import enums.Pantalla;
import ventanas.FrameConfiguracion;
import java.awt.CardLayout;
import javax.swing.JPanel;
import modelo.IModeloControlAplicacion;

/**
 * Clase controladora principal que gestiona la lógica de la aplicación,
 * especialmente la navegación entre diferentes paneles o "pantallas" de la
 * interfaz de usuario, y actúa como intermediaria entre la vista y el modelo
 * IModeloControlAplicacion.
 *
 * @author Alici
 */
public class ControlAplicacion {

    /**
     * Referencia a la interfaz del modelo, utilizada para invocar la lógica de
     * negocio y actualizar el estado de la aplicación.
     */
    private final IModeloControlAplicacion controlModelo;

    /**
     * Referencia a la ventana principal de la aplicación. Se utiliza para
     * gestionar acciones que afectan a toda la ventana, como cerrarla.
     */
    private FrameConfiguracion ventanaPrincipal;

    /**
     * Panel contenedor que utiliza un CardLayout para alternar entre las
     * diferentes vistas (paneles).
     */
    private JPanel panelContenedor;

    /**
     * Layout manager que permite cambiar entre los paneles contenidos en.
     */
    private CardLayout cardLayout;

    // Constantes utilizadas para identificar las diferentes vistas (tarjetas)
    // dentro del CardLayout.
    /**
     * Constante de identificación para el Panel de Menú Principal.
     */
    private final String MENU = "MENU";
    /**
     * Constante de identificación para el Panel de Configuración de Usuario.
     */
    private final String CONFIGURACION_USUARIO = "CONFIGURAR_USUARIO";
    /**
     * Constante de identificación para el Panel de Sala de Espera ya unido.
     */
    private final String SALA_ESPERA = "SALA_ESPERA";
    /**
     * Constante de identificación para el Panel de Sala de Espera antes de
     * unirse.
     */
    private final String SALA_ESPERA_NO_UNIDO = "SALA_ESPERA_NO_UNIDO";

    /**
     * Constructor de la clase ControlAplicacion.
     *
     * @param controlModelo La implementación de la interfaz
     * IModeloControlAplicacion que contiene la lógica de la aplicación. No
     * puede ser nula.
     * @throws IllegalArgumentException si el parámetro controlModelo es nulo.
     */
    public ControlAplicacion(IModeloControlAplicacion controlModelo) {
        if (controlModelo == null) {
            throw new IllegalArgumentException("La dependencia IModeloControlAplicacion no puede ser nula.");
        }
        this.controlModelo = controlModelo;
    }

    /**
     * Solicita al modelo cambiar el avatar del usuario.
     *
     * @param accion Indica la dirección o el tipo de cambio del avatar (ej. 1
     * para siguiente, -1 para anterior).
     */
    public void cambiarAvatar(int accion) {
        controlModelo.siguienteAvatar(accion);
    }

    /**
     * Solicita al modelo cambiar la pantalla actual a la vista del Menú
     * Principal.
     */
    public void mostrarPanelMenu() {
        controlModelo.siguientePantalla(Pantalla.MENU);
    }

    /**
     * Solicita al modelo cambiar la pantalla actual a la vista de Configuración
     * de Usuario.
     */
    public void mostrarPanelConfigurarUsuario() {
        controlModelo.siguientePantalla(Pantalla.CONFIGURAR_USUARIO);
    }

    /**
     * Solicita al modelo cambiar la pantalla actual a la vista de Configuración
     * de Sala/Partida.
     */
    public void mostrarPanelConfigurarSala() {
        controlModelo.siguientePantalla(Pantalla.CONFIGURAR_PARTIDA);
    }

    /**
     * Método para redirigir al panel de la sala creada, donde existe una sala
     * pero el jugador aún no se une. Utiliza la constante
     * Pantalla.SALA_ESPERA_NO_UNIDO.
     */
    public void mostrarPanelSala() {
        controlModelo.siguientePantalla(Pantalla.SALA_ESPERA_NO_UNIDO);
    }

    /**
     * Método para redirigir al panel de la sala después de configurar su
     * usuario para unirse. Utiliza la constante Pantalla.SALA_ESPERA.
     */
    public void mostrarPanelSalaEsperaJuego() {
        controlModelo.siguientePantalla(Pantalla.SALA_ESPERA);
    }

    /**
     * Inicia el proceso para unirse a una sala, redirigiendo primero al panel
     * de configuración de usuario.
     */
    public void unirseASala() {
        mostrarPanelConfigurarUsuario();
    }

    /**
     * Abandona la sala y cierra la ventana principal de la aplicación.
     */
    public void abandonarSala() {
        ventanaPrincipal.dispose();
    }

    /**
     * Cierra la aplicación principal.
     */
    public void salir() {
        ventanaPrincipal.dispose();
    }

}
