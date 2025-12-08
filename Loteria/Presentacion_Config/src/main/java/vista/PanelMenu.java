package vista;

import controladores.ControlesConfiguracionFactory;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Font;

/**
 *
 * @author Luis Rafael Lagarda Encinas 252605
 */
public class PanelMenu extends JPanel {

    private Image iconFondo;

    public PanelMenu() {

        initComponents();
        obtenerFondo();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        etiquetaTituloMenu = new javax.swing.JLabel();
        buttonCrearSala = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 178, 0));
        setMaximumSize(new java.awt.Dimension(1336, 768));
        setMinimumSize(new java.awt.Dimension(1336, 768));

        etiquetaTituloMenu.setFont(new Font("Voltaire",Font.PLAIN, 128));
        etiquetaTituloMenu.setForeground(new java.awt.Color(206, 232, 13));
        etiquetaTituloMenu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaTituloMenu.setText("LOTERIA MEXICANA");

        buttonCrearSala.setBackground(new java.awt.Color(100, 13, 95));
        buttonCrearSala.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        buttonCrearSala.setForeground(new java.awt.Color(255, 255, 255));
        buttonCrearSala.setText("Crear Sala");
        buttonCrearSala.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        buttonCrearSala.setFocusPainted(false);
        buttonCrearSala.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCrearSalaActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/interrogacion.png"))); // NOI18N
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(etiquetaTituloMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(488, 488, 488)
                        .addComponent(buttonCrearSala, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel1)))
                .addContainerGap(488, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(etiquetaTituloMenu)
                .addGap(70, 70, 70)
                .addComponent(buttonCrearSala, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 468, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void buttonCrearSalaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCrearSalaActionPerformed
        // TODO add your handling code here:
        ControlesConfiguracionFactory controles = ControlesConfiguracionFactory.getInstance();
        controles.getControlAplicacion().mostrarPanelConfigurarUsuario();
    }//GEN-LAST:event_buttonCrearSalaActionPerformed

    private void buttonBuscarSalaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_buttonBuscarSalaActionPerformed

    }// GEN-LAST:event_buttonBuscarSalaActionPerformed

    private void obtenerFondo() {
        iconFondo = new ImageIcon(getClass().getResource("/fondos/fondo_menu.png")).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (iconFondo != null) {
            g.drawImage(iconFondo, 0, 0, getWidth(), getHeight(), this);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonCrearSala;
    private javax.swing.JLabel etiquetaTituloMenu;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
