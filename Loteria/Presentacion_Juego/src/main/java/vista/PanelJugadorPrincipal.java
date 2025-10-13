package vista;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

import modelo.JugadorSubject;

/**
 * Clase que extiende de JPanel que representa
 * al Jugador principal y secundarios
 * @author pedro
 */
public class PanelJugadorPrincipal extends javax.swing.JPanel {
    private final int ANCHO_ALTO = 127;
    private Image imagenFondo;
    private ImageIcon fotoJugador;

    /**
     * Constructor que inicializa el panel del jugador principal.
     */
    public PanelJugadorPrincipal() {
        setOpaque(true); // Asegura que el panel pinte el fondo
        initComponents();
        dibujarFondo();
        cargarFotoJugador();
    }
    /**
     * Metodo que crea todos los componentes del JPanel.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblFoto = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblPuntaje = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        lblFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes_alt/icon_imagen.png"))); // NOI18N

        lblNombre.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblNombre.setForeground(new java.awt.Color(255, 255, 255));
        lblNombre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNombre.setText("Jugador 1");

        lblPuntaje.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblPuntaje.setForeground(new java.awt.Color(255, 255, 255));
        lblPuntaje.setText("Puntaje: 0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(lblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addComponent(lblPuntaje, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(lblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(lblNombre)
                .addGap(27, 27, 27)
                .addComponent(lblPuntaje)
                .addContainerGap(44, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    /**
     * Metodo que carga la foto o avatar del jugador.
     */
    private void cargarFotoJugador(){
        fotoJugador = cargarFoto();
        lblFoto.setIcon(fotoJugador);
    }
    
    /**
     * Metodo que obtiene la direccion e iagen el usuario
     * @return icono para el jugador.
     */
    private ImageIcon cargarFoto() {
        String url = "/imagenes_alt/icon_imagen.png";
        URL recurso = getClass().getResource(url);
        if (recurso == null) {
            System.err.println("No se encontró la imagen: " + url);
        }
        ImageIcon original = new ImageIcon(recurso);
        Image imagenEscalada = original.getImage().getScaledInstance(ANCHO_ALTO, ANCHO_ALTO, Image.SCALE_SMOOTH);
        return new ImageIcon(imagenEscalada);
    }
    
    /**
     * Metodo que se encarga de pintar el fondo donde se encuentra el
     * jugador principal.
     */
    private void dibujarFondo() {
        imagenFondo = new javax.swing.ImageIcon(getClass().getResource("/fondos/fondo_jugador_principal.png"))
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
     * Metodo para asgnar un lugar al jugador con sus datos.
     * @param jugador jugador.
     */
    public void setJugador(JugadorSubject jugador) {
        lblNombre.setText(jugador.getNickname());
        lblPuntaje.setText("Puntaje: " + jugador.getPuntaje());
        fotoJugador = new ImageIcon(jugador.getFoto());
        cargarFoto();
        repaint();
        revalidate();
    }
    
    /**
     * Metodo para verificar si existe el mismo jugador.
     * @param jugador jugador.
     * @return true si existe, contrario false.
     */
    public boolean esMismoJugador(JugadorSubject jugador) {
        return lblNombre.getText().equals(jugador.getNickname());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblFoto;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPuntaje;
    // End of variables declaration//GEN-END:variables
}