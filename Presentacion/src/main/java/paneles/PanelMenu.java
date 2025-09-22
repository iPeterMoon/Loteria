package paneles;

import controladores.ControlNavegacion;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.FontFormatException;

/**
 *
 * @author Luis Rafael Lagarda Encinas 252605
 */
public class PanelMenu extends JPanel {

    private Image iconFondo;

    private ControlNavegacion control;

    public PanelMenu(ControlNavegacion control) {
        this.control = control;
        initComponents();
        obtenerFondo();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        etiquetaTituloMenu = new javax.swing.JLabel();
        buttonCrearSala = new javax.swing.JButton();
        buttonBuscarSala = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        etiquetaTituloMenu.setFont(new Font("Voltaire",Font.PLAIN, 128));
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

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/interrogacion.png"))); // NOI18N
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(etiquetaTituloMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(488, 488, 488)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(buttonCrearSala, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonBuscarSala, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(488, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(etiquetaTituloMenu)
                .addGap(65, 65, 65)
                .addComponent(buttonCrearSala, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(buttonBuscarSala, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 386, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(15, 15, 15))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void buttonBuscarSalaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_buttonBuscarSalaActionPerformed
        // TODO add your handling code here:
        control.mostrarPanelBuscarSala();
    }// GEN-LAST:event_buttonBuscarSalaActionPerformed

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
    private javax.swing.JButton buttonBuscarSala;
    private javax.swing.JButton buttonCrearSala;
    private javax.swing.JLabel etiquetaTituloMenu;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
