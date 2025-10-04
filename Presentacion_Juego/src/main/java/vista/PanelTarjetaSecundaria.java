/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import javax.swing.BorderFactory;
import org.netbeans.lib.awtextra.AbsoluteLayout;

import modelo.ModeloTarjeta;

/**
 * Metodo que representa la targeta secundaria
 * @author norma
 */
public class PanelTarjetaSecundaria extends javax.swing.JPanel {

    /**
     * Constructor que inicializa la tarjeta del jugador secundario
     */
    public PanelTarjetaSecundaria() {
        initComponents();

        panelCartitasAbstractas.setLayout(new GridLayout(4, 4, 3, 3));
        panelCartitasAbstractas.setPreferredSize(new Dimension(70, 70));

        for (int i = 0; i < 16; i++) {
            int fila = i / 4;
            int col = i % 4;
            Point posicion = new Point(fila, col);
            PanelCartita pc = new PanelCartita(true, i + 1);
            pc.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            pc.setPosicion(posicion);
            panelCartitasAbstractas.add(pc);
        }

        panelCartitasAbstractas.revalidate();
        panelCartitasAbstractas.repaint();
    }

    /**
     * Metodo que crea los componentes del JPanel
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelCartitasAbstractas = new javax.swing.JPanel();

        javax.swing.GroupLayout panelCartitasAbstractasLayout = new javax.swing.GroupLayout(panelCartitasAbstractas);
        panelCartitasAbstractas.setLayout(panelCartitasAbstractasLayout);
        panelCartitasAbstractasLayout.setHorizontalGroup(
            panelCartitasAbstractasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 111, Short.MAX_VALUE)
        );
        panelCartitasAbstractasLayout.setVerticalGroup(
            panelCartitasAbstractasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 84, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelCartitasAbstractas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelCartitasAbstractas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    /**
     * Metodo que actualiza las trajeta del jugador secundarion
     * con las fichas simuladas
     * @param tarjeta tarjeta del jugador secundario
     */
    public void actualizarFichas(ModeloTarjeta tarjeta) {
        for (int idx = 0; idx < panelCartitasAbstractas.getComponentCount(); idx++) {
            java.awt.Component comp = panelCartitasAbstractas.getComponent(idx);
            if (comp instanceof  PanelCartita) {
                PanelCartita cartita = (PanelCartita) comp;
                Point posicion = cartita.getPosicion(); 
                Boolean marcada = tarjeta.getFichas().get(posicion);
                if (marcada != null && marcada) {
                    cartita.marcarComoJugadorSecundario();
                }
            }
        }
        repaint();
        revalidate();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panelCartitasAbstractas;
    // End of variables declaration//GEN-END:variables
}
