package paneles;

import controladores.ControlNavegacion;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import java.awt.BorderLayout;
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
        setLayout(new BorderLayout());
        initComponents();
        obtenerFondo();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        etiquetaTituloMenu = new javax.swing.JLabel();
        buttonCrearSala = new javax.swing.JButton();
        buttonBuscarSala = new javax.swing.JButton();
        iconoInterrogacion = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 178, 0));
        setMaximumSize(new java.awt.Dimension(1336, 768));
        setMinimumSize(new java.awt.Dimension(1336, 768));

        try {
            // Carga la fuente desde el classpath (resources/fuentes/Voltaire-Regular.ttf)
            Font customFont = Font.createFont(
                    Font.TRUETYPE_FONT,
                    getClass().getClassLoader().getResourceAsStream("fuentes/Voltaire-Regular.ttf")).deriveFont(128f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            etiquetaTituloMenu.setFont(customFont);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            // Si falla, usa la fuente por defecto
            etiquetaTituloMenu.setFont(new java.awt.Font("Serif", Font.PLAIN, 128));
        }

        etiquetaTituloMenu.setForeground(new java.awt.Color(206, 232, 13));
        etiquetaTituloMenu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetaTituloMenu.setText("LOTERÍA MEXICANA");

        buttonCrearSala.setBackground(new java.awt.Color(100, 13, 95));
        buttonCrearSala.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        buttonCrearSala.setForeground(new java.awt.Color(255, 255, 255));
        buttonCrearSala.setText("Crear Sala");
        buttonCrearSala.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buttonCrearSala.setFocusPainted(false);

        buttonBuscarSala.setBackground(new java.awt.Color(100, 13, 95));
        buttonBuscarSala.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        buttonBuscarSala.setForeground(new java.awt.Color(255, 255, 255));
        buttonBuscarSala.setText("Buscar Sala");
        buttonBuscarSala.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buttonBuscarSala.setFocusPainted(false);
        buttonBuscarSala.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonBuscarSalaActionPerformed(evt);
            }
        });

        iconoInterrogacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/interrogacion.png"))); // NOI18N
        iconoInterrogacion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(etiquetaTituloMenu, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(488, 488, 488)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(buttonCrearSala,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 360,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(buttonBuscarSala,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 360,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(20, 20, 20)
                                                .addComponent(iconoInterrogacion)))
                                .addContainerGap(488, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(71, 71, 71)
                                .addComponent(etiquetaTituloMenu)
                                .addGap(70, 70, 70)
                                .addComponent(buttonCrearSala, javax.swing.GroupLayout.PREFERRED_SIZE, 70,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(buttonBuscarSala, javax.swing.GroupLayout.PREFERRED_SIZE, 69,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 365,
                                        Short.MAX_VALUE)
                                .addComponent(iconoInterrogacion)
                                .addContainerGap()));
    }// </editor-fold>//GEN-END:initComponents

    private void buttonBuscarSalaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_buttonBuscarSalaActionPerformed
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
    private javax.swing.JLabel iconoInterrogacion;
    // End of variables declaration//GEN-END:variables
}
