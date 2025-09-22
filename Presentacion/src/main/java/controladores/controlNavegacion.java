/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import ventanas.VentanaPrincipal;
import paneles.PanelMenu;
import paneles.PanelSala;
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
        PanelSala panelSala = new PanelSala(this);

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

    public void mostrarPanelBuscarSala() {
        cardLayout.show(panelContenedor, BUSCAR_SALA);
    }
}
