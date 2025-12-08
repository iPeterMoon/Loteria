package vista;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import modelo.ModeloTarjeta;

/**
 * Clase que representa la tarjeta del jugador
 * @author Jp
 */
public class PanelTarjeta extends javax.swing.JPanel {

    private Image imagenFondo;
    private PanelCartita[][] tarjeta = new PanelCartita[4][4];

    /**
     * Constructor para que se vea en el design de netBeans
     */
    public PanelTarjeta() {
        initComponents();
        dibujarFondo();

        for (int i = 0; i < 16; i++) {
            int fila = i / 4;
            int col = i % 4;
            Point posicion = new Point(fila, col);

            panelCartitas.add(new PanelCartita(i + 1, posicion));
        }
    }

    /**
     * Constructor que se va a usar
     * @param tarjeta arreglo de numeros que representa la tarjeta
     */
    public PanelTarjeta(int tarjeta[][]) {
        initComponents();
        dibujarFondo();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                this.tarjeta[i][j] = new PanelCartita(tarjeta[i][j], new Point(i, j));
                panelCartitas.add(this.tarjeta[i][j]);
            }
        }
    }

    /**
     * Metodo que crea los componentes del JPanel
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panelCartitas = new javax.swing.JPanel();

        setBackground(new java.awt.Color(235, 235, 235));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setMaximumSize(new java.awt.Dimension(359, 759));
        setMinimumSize(new java.awt.Dimension(359, 759));
        setPreferredSize(new java.awt.Dimension(359, 759));
        setLayout(new java.awt.BorderLayout());

        jPanel1.setMaximumSize(new java.awt.Dimension(359, 22));
        jPanel1.setMinimumSize(new java.awt.Dimension(359, 22));
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(359, 22));
        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        panelCartitas.setBackground(new java.awt.Color(235, 235, 235));
        panelCartitas.setLayout(new java.awt.GridLayout(4, 4, 10, 10));
        add(panelCartitas, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    
    /**
     * metodo para dibujar la tarjeta
     */
    private void dibujarFondo() {
        imagenFondo = new javax.swing.ImageIcon(getClass().getResource("/fondos/fondo_tarjeta_loteria.png"))
                .getImage();
    }
    /**
     * Metodo protegio de JPanel para pintar el Jpanel.
     * @param g graficas de java.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagenFondo != null) {
            g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
        }
    }
    
    /**
     * Metodo que actualiza la tarjeta con una nueva tarjeta del modelo
     * Reemplaza las cartas mostradas con las del modelo y luego actualiza las fichas
     * @param tarjeta tarjeta del Jugador con las cartas aleatorias
     */
    public void actualizarTarjeta(ModeloTarjeta tarjeta){
        // Limpiar el panel de cartas antigas
        panelCartitas.removeAll();
        
        // Agregar nuevas cartas basadas en el ModeloTarjeta
        for (int idx = 0; idx < panelCartitas.getComponentCount(); idx++) {
            java.awt.Component comp = panelCartitas.getComponent(idx);
            if (comp instanceof PanelCartita) {
                panelCartitas.remove(comp);
            }
        }
        
        // Recrear las cartas con los números del ModeloTarjeta
        if (tarjeta != null && tarjeta.getCartas() != null) {
            for (int fila = 0; fila < 4; fila++) {
                for (int col = 0; col < 4; col++) {
                    Point posicion = new Point(fila, col);
                    Integer numeroCarta = tarjeta.getCartas().get(posicion);
                    if (numeroCarta != null) {
                        this.tarjeta[fila][col] = new PanelCartita(numeroCarta, posicion);
                        panelCartitas.add(this.tarjeta[fila][col]);
                    }
                }
            }
        }
        
        // Actualizar las fichas
        actualizarFichas(tarjeta);
        
        // Refrescar la interfaz
        panelCartitas.revalidate();
        panelCartitas.repaint();
    }
    
    /**
     * Metodo que actualiza la tarjeta para insertar las fichas
     * @param tarjeta tarjeta del  Jugador
     */
    public void actualizarFichas(ModeloTarjeta tarjeta){
        // Recorre los componentes del panel contenedor
        for (int idx = 0; idx < panelCartitas.getComponentCount(); idx++) {
            java.awt.Component comp = panelCartitas.getComponent(idx);
            if (comp instanceof PanelCartita) {
                PanelCartita cartita = (PanelCartita) comp;
                Point posicion = cartita.getPosicion();
                Boolean tieneFicha = tarjeta.getFichas().get(posicion);
                if (tieneFicha != null && tieneFicha) {
                    cartita.agregarFicha();
                }
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel panelCartitas;
    // End of variables declaration//GEN-END:variables
}
