package controladores;

import ventanas.VentanaPrincipal;
import vista.PanelMenu;
import vista.PanelCrearSala;
import java.awt.CardLayout;
import javax.swing.JPanel;

/**
 *
 * @author Luis Rafael Lagarda Encinas 252605
 */
public class ControlNavegacion {

    private VentanaPrincipal ventanaPrincipal;
    private JPanel panelContenedor;
    private CardLayout cardLayout;

    private final String MENU = "MENU";
    private final String BUSCAR_SALA = "BUSCAR_SALA";

    public void iniciar() {
        this.ventanaPrincipal = new VentanaPrincipal();
        panelContenedor = ventanaPrincipal.getPanelContenedor();
        cardLayout = ventanaPrincipal.getCardLayout();

        //CREAR PANELES DEL CARDLAYOUT
        PanelMenu panelMenu = new PanelMenu(this);
        PanelCrearSala panelSala = new PanelCrearSala(this);

        //AGREGAR PANELES AL CARDLAYOUT
        panelContenedor.add(panelMenu, MENU);
        panelContenedor.add(panelSala, BUSCAR_SALA);

        cardLayout.show(panelContenedor, MENU);
        ventanaPrincipal.setVisible(true);
        ventanaPrincipal.setLocationRelativeTo(null);
    }

    public void mostrarPanelMenu() {
        cardLayout.show(panelContenedor, MENU);
    }

    public void mostrarPanelCrearSala() {
        cardLayout.show(panelContenedor, BUSCAR_SALA);
    }
}
