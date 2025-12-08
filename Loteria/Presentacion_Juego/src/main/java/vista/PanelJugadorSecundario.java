package vista;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

import modelo.JugadorSubject;
import org.netbeans.lib.awtextra.AbsoluteConstraints;

/**
 * Clase que representa a los jugadores secuntarios
 *
 * @author norma
 */
public class PanelJugadorSecundario extends javax.swing.JPanel {

    private Image imagenFondo;
    private static final int ANCHO_ALTO = 80;
    private ImageIcon fotoJugador;

    private PanelTarjetaSecundaria panelTarjetaSecundaria;
    
    /**
     * Constructor que inicaliza a los jugadores secundarios
     */
    public PanelJugadorSecundario() {
        setOpaque(true);
        initComponents();
        
        panelTarjetaSecundaria = new PanelTarjetaSecundaria();
        
        this.add(panelTarjetaSecundaria, new AbsoluteConstraints(160,10, -1 , -1));
        
        dibujarFondo();
        cargarFotoJugador();
    }

    /**
     * Metodo que asigna un nombre
     *
     * @param nombre nombre del jugador
     */
    public void setNombre(String nombre) {
        labelNombre.setText(nombre);
    }

    /**
     * Metodo que da un puntaje
     *
     * @param puntaje
     */
    public void setPuntaje(int puntaje) {
        labelPuntaje.setText("Puntaje: " + puntaje);
    }

    /**
     * Metodo que crea los componentes del JPanel
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelFoto = new javax.swing.JLabel();
        labelNombre = new javax.swing.JLabel();
        labelPuntaje = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(275, 130));
        setMinimumSize(new java.awt.Dimension(275, 130));
        setPreferredSize(new java.awt.Dimension(275, 130));
        setVerifyInputWhenFocusTarget(false);
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        labelFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes_alt/icon_imagen.png"))); // NOI18N
        labelFoto.setPreferredSize(new java.awt.Dimension(80, 80));
        add(labelFoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 18, -1, -1));

        labelNombre.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        labelNombre.setForeground(new java.awt.Color(255, 255, 255));
        labelNombre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelNombre.setText("Jugador 2");
        add(labelNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 104, 85, -1));

        labelPuntaje.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        labelPuntaje.setForeground(new java.awt.Color(255, 255, 255));
        labelPuntaje.setText("Puntaje: 0");
        add(labelPuntaje, new org.netbeans.lib.awtextra.AbsoluteConstraints(168, 104, -1, -1));
    }// </editor-fold>//GEN-END:initComponents
    private void dibujarFondo() {
        imagenFondo = new javax.swing.ImageIcon(getClass().getResource("/fondos/fondo_jugador_secundario.png"))
                .getImage();
    }

    /**
     * Metodo protegio de JPanel para pintar el Jpanel.
     *
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
     * Metodo que carga la foto o avatar del jugador.
     */
    private void cargarFotoJugador() {
        fotoJugador = cargarFoto("/imagenes_alt/icon_imagen.png");
        labelFoto.setIcon(fotoJugador);
    }

    /**
     * Metodo que obtiene la direccion e iagen el usuario.
     *
     * @return icono para el jugador.
     */
    private ImageIcon cargarFoto(String ruta) {
        URL recurso = getClass().getResource(ruta);
        if (recurso == null) {
            return new ImageIcon();
        }
        ImageIcon original = new ImageIcon(recurso);
        Image imagenEscalada = original.getImage().getScaledInstance(ANCHO_ALTO, ANCHO_ALTO, Image.SCALE_SMOOTH);
        return new ImageIcon(imagenEscalada);
    }

    /**
     * Metodo que se encarga de pintar el fondo donde se encuentra el jugador
     * secundario.
     */
    public void setJugador(JugadorSubject jugador) {
        labelNombre.setText(jugador.getNickname());
        labelPuntaje.setText("Puntaje: " + jugador.getPuntaje());
        
        String rutaAvatar = obtenerRutaAvatar(jugador.getFoto());
        fotoJugador = cargarFoto(rutaAvatar);
        if (fotoJugador.getImage() != null) {
            labelFoto.setIcon(fotoJugador);
        }
        
        if (panelTarjetaSecundaria != null && jugador.getTarjeta() != null) {
             panelTarjetaSecundaria.actualizarFichas(jugador.getTarjeta());
        }
        
        repaint();
        revalidate();
    }
    
    /**
     * Método auxiliar para rutas de avatar (igual que en principal).
     */
    private String obtenerRutaAvatar(int idAvatar) {
        if (idAvatar <= 0) return "/imagenes_alt/icon_imagen.png";
        return "/avatars/" + idAvatar + ".jpg"; 
    }

    /**
     * Metodo para verificar si existe el mismo jugador.
     *
     * @param jugador jugador.
     * @return true si existe, contrario false.
     */
    public boolean esMismoJugador(JugadorSubject jugador) {
        return labelNombre.getText().equals(jugador.getNickname());
    }

    /**
     * Metodo que actualiza la targeta de los jugadores secundarios.
     *
     * @param jugador jugador
     */
    public void actualizarTarjetaAbstracta(JugadorSubject jugador) {
        
        if (panelTarjetaSecundaria != null) {
            panelTarjetaSecundaria.actualizarFichas(jugador.getTarjeta());
        }
        
        repaint();
        revalidate();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel labelFoto;
    private javax.swing.JLabel labelNombre;
    private javax.swing.JLabel labelPuntaje;
    // End of variables declaration//GEN-END:variables
}
