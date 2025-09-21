/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.edu.mx.controls;

import itson.edu.mx.forms.frmPrincipal;
import itson.edu.mx.jPanels.panelMenu;
import itson.edu.mx.jPanels.panelSala;
import java.awt.CardLayout;
import javax.swing.JPanel;

/**
 *
 * @author Luis Rafael Lagarda Encinas 252605
 */
public class control {
    private frmPrincipal principal;
    private panelMenu menu;
    private panelSala sala;
    private CardLayout card;
    private JPanel panelCentral;

    public control(frmPrincipal fp) {
        this.principal = fp;
        this.panelCentral = principal.getPanelCentral();
        this.card = (CardLayout) panelCentral.getLayout();
    }
    
    public void generarMenu(){
        if(menu == null){
            menu = new panelMenu(this);
            panelCentral.add(menu,"menu");
        }
        card.show(panelCentral, "menu");
    }
    
    public void generarSala(){
        if(sala == null){
            sala = new panelSala(this);
            panelCentral.add(sala,"sala");
        }
        card.show(panelCentral, "sala");
    }
}
