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

    public ControlAplicacion(IModeloControlAplicacion controlModelo) {
        if (controlModelo == null) {
            throw new IllegalArgumentException("La dependencia IModeloControlAplicacion no puede ser nula.");
        }
        this.controlModelo = controlModelo;
    }

    public void iniciar() {
        cardLayout = ventanaPrincipal.getCardLayout();

        // OBTENER PANELES DEL CARDLAYOUT
        PanelMenu panelMenu = ventanaPrincipal.getPanelMenu();
        PanelConfiguracionUsuario panelConfUsuario = ventanaPrincipal.getPanelConfiguracionUsuario();
        PanelSalaEspera panelSalaEspera = ventanaPrincipal.getPanelSalaEspera();

        // AGREGAR PANELES AL CARDLAYOUT
        panelContenedor.add(panelMenu, MENU);
        panelContenedor.add(panelConfUsuario, CONFIGURACION_USUARIO);
        panelContenedor.add(panelSalaEspera, SALA_ESPERA);

        cardLayout.show(panelContenedor, MENU);
        ventanaPrincipal.setVisible(true);
        ventanaPrincipal.setLocationRelativeTo(null);
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * Método para redirigir al panel de la sala despues de configurar su
     * usuario para unirse
     */
    public void mostrarPanelSalaEsperaJuego() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void mostrarPanelSalaEspera(boolean unido) {
        cardLayout.show(panelContenedor, SALA_ESPERA);
        PanelSalaEspera panelSalaEspera = ventanaPrincipal.getPanelSalaEspera();

        if (unido) {
            panelSalaEspera.configurarModoJugadorUnido();
        } else {
            panelSalaEspera.configurarModoJugadorNoUnido();
        }
    }

    public void unirseASala() {
        //
    }

    public void abandonarSala() {
        ventanaPrincipal.dispose();
    }

    public void salir() {
        ventanaPrincipal.dispose();
    }

}