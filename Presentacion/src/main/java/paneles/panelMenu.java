/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package paneles;

import controladores.ControlNavegacion;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Luis Rafael Lagarda Encinas 252605
 */
public class PanelMenu extends JPanel {

    private Image iconFondo;

    private ControlNavegacion control;

    private ImageIcon iconoButtonAyuda;

    public PanelMenu(ControlNavegacion control) {
        this.control = control;
        initComponents();
        obtenerFondo();

        iconoButtonAyuda = new ImageIcon(getClass().getClassLoader().getResource("iconos/interrogacion.png"));
        buttonAyuda.setIcon(iconoButtonAyuda);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        etiquetaTituloMenu = new javax.swing.JLabel();
        buttonCrearSala = new javax.swing.JButton();
        buttonBuscarSala = new javax.swing.JButton();
        buttonAyuda = new javax.swing.JButton();

        etiquetaTituloMenu.setFont(new java.awt.Font("Segoe UI Black", 1, 48)); // NOI18N
        etiquetaTituloMenu.setForeground(new java.awt.Color(102, 204, 0));
        etiquetaTituloMenu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaTituloMenu.setText("LOTERIA MEXICANA");

        buttonCrearSala.setBackground(new java.awt.Color(153, 0, 153));
        buttonCrearSala.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        buttonCrearSala.setForeground(new java.awt.Color(255, 255, 255));
        buttonCrearSala.setText("Crear Sala");

        buttonBuscarSala.setBackground(new java.awt.Color(153, 0, 153));
        buttonBuscarSala.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        buttonBuscarSala.setForeground(new java.awt.Color(255, 255, 255));
        buttonBuscarSala.setText("Buscar Sala");
        buttonBuscarSala.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonBuscarSalaActionPerformed(evt);
            }
        });

        buttonAyuda.setOpaque(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(buttonAyuda)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(etiquetaTituloMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(577, 577, 577)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonBuscarSala, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonCrearSala, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(583, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(118, 118, 118)
                .addComponent(etiquetaTituloMenu)
                .addGap(77, 77, 77)
                .addComponent(buttonCrearSala)
                .addGap(30, 30, 30)
                .addComponent(buttonBuscarSala)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 344, Short.MAX_VALUE)
                .addComponent(buttonAyuda, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void buttonBuscarSalaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBuscarSalaActionPerformed
        // TODO add your handling code here:
        control.mostrarPanelBuscarSala();
    }//GEN-LAST:event_buttonBuscarSalaActionPerformed
    private void obtenerFondo() {
        iconFondo = new ImageIcon(getClass().getResource("/fondos/fondo1.png")).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (iconFondo != null) {
            g.drawImage(iconFondo, 0, 0, getWidth(), getHeight(), this);
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonAyuda;
    private javax.swing.JButton buttonBuscarSala;
    private javax.swing.JButton buttonCrearSala;
    private javax.swing.JLabel etiquetaTituloMenu;
    // End of variables declaration//GEN-END:variables
}
