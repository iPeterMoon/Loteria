/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.edu.mx.forms;

import itson.edu.mx.controls.control;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Luis Rafael Lagarda Encinas 252605
 */
public class frmPrincipal extends JFrame{
    private control control;
    private JPanel panelCentral;

    public frmPrincipal(){
        generarFrame();
        control = new control(this);
        control.generarMenu();
        setVisible(true);
    }

    private void generarFrame(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Loteria (sistema distribuido)");
        setLayout(new BorderLayout());
        setSize(1000,800);
        setPreferredSize(new Dimension(1000, 800));
        setLocationRelativeTo(null);
        setResizable(false);

        try {
            setIconImage(new ImageIcon(getClass().getResource("/cartas/1.jpeg")).getImage());
        } catch (NullPointerException e) {
            System.err.println("Icono no encontrado: /cartas/1.jpeg");
        }
        
        panelCentral = new JPanel(new CardLayout());
        add(panelCentral, BorderLayout.CENTER);
      
        
        pack();
        setLocationRelativeTo(null);
    }
    
    public JPanel getPanelCentral() {
        return panelCentral;
    }
}

