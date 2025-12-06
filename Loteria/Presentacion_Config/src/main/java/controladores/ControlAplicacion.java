package controladores;

import ventanas.FrameConfiguracion;
import vista.PanelMenu;
import java.awt.CardLayout;
import javax.swing.JPanel;
import modelo.IModeloControlAplicacion;
import vista.PanelConfiguracionUsuario;

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

    public ControlAplicacion(IModeloControlAplicacion controlModelo) {
        if (controlModelo == null) {
            throw new IllegalArgumentException("La dependencia IModeloControlAplicacion no puede ser nula.");
        }
        this.controlModelo = controlModelo;
    }

    public void iniciar() {
        this.ventanaPrincipal = FrameConfiguracion.getInstancia();
        panelContenedor = ventanaPrincipal.getPanelContenedor();
        cardLayout = ventanaPrincipal.getCardLayout();

        //OBTENER PANELES DEL CARDLAYOUT
        PanelMenu panelMenu = ventanaPrincipal.getPanelMenu();
        PanelConfiguracionUsuario panelConfUsuario = ventanaPrincipal.getPanelConfiguracionUsuario();

        //AGREGAR PANELES AL CARDLAYOUT
        panelContenedor.add(panelMenu, MENU);
        panelContenedor.add(panelConfUsuario, CONFIGURACION_USUARIO);

        cardLayout.show(panelContenedor, MENU);
        ventanaPrincipal.setVisible(true);
        ventanaPrincipal.setLocationRelativeTo(null);
    }

    public void cambiarAvatar(int accion) {
        controlModelo.siguienteAvatar(accion);
    }

    public void mostrarPanelMenu() {
        cardLayout.show(panelContenedor, MENU);
    }

    public void mostrarPanelCrearSala() {
        cardLayout.show(panelContenedor, CONFIGURACION_USUARIO);
    }
}
