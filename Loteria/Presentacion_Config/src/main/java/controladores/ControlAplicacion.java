package controladores;

import enums.Pantalla;
import ventanas.FrameConfiguracion;
import vista.PanelMenu;
import vista.PanelSalaEspera;
import java.awt.CardLayout;
import javax.swing.JPanel;
import modelo.IModeloControlAplicacion;

/**
 *
 * @author Alici
 */
public class ControlAplicacion {

    private final IModeloControlAplicacion controlModelo;
    private FrameConfiguracion ventanaPrincipal;
    private JPanel panelContenedor;
    private CardLayout cardLayout;
    private final String MENU = "MENU";
    private final String CONFIGURACION_USUARIO = "CONFIGURAR_USUARIO";
    private final String SALA_ESPERA = "SALA_ESPERA";
    private final String SALA_ESPERA_NO_UNIDO = "SALA_ESPERA_NO_UNIDO";

    public ControlAplicacion(IModeloControlAplicacion controlModelo) {
        if (controlModelo == null) {
            throw new IllegalArgumentException("La dependencia IModeloControlAplicacion no puede ser nula.");
        }
        this.controlModelo = controlModelo;
    }

    public void cambiarAvatar(int accion) {
        controlModelo.siguienteAvatar(accion);
    }

    public void mostrarPanelMenu() {
        controlModelo.siguientePantalla(Pantalla.MENU);
    }

    public void mostrarPanelConfigurarUsuario() {
        controlModelo.siguientePantalla(Pantalla.CONFIGURAR_USUARIO);
    }

    public void mostrarPanelConfigurarSala() {
        controlModelo.siguientePantalla(Pantalla.CONFIGURAR_PARTIDA);
    }

    /**
     * Método para redirigir al panel de la sala creada, donde existe una sala
     * pero el jugador aun no se une
     */
    public void mostrarPanelSala() {
        controlModelo.siguientePantalla(Pantalla.SALA_ESPERA_NO_UNIDO);
    }

    /**
     * Método para redirigir al panel de la sala despues de configurar su
     * usuario para unirse
     */
    public void mostrarPanelSalaEsperaJuego() {
        controlModelo.siguientePantalla(Pantalla.SALA_ESPERA);
    }

    public void unirseASala() {
        mostrarPanelConfigurarUsuario();
    }

    public void abandonarSala() {
        ventanaPrincipal.dispose();
    }

    public void salir() {
        ventanaPrincipal.dispose();
    }

}
