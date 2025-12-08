package vista;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;

import modelo.JugadorSubject;

/**
 * Panel de la interfaz gráfica de usuario (GUI) diseñado para mostrar la
 * información de un jugador: su avatar, nombre y puntaje.
 *
 * @author pedro
 */
public class PanelJugadorPrincipal extends javax.swing.JPanel {

    private final int ANCHO_ICONO_JUGADOR = 127;
    private final int ALTO_ICONO_JUGADOR = ANCHO_ICONO_JUGADOR;
    private final String RUTA_RECURSO_ICONO_DEFAULT = "/imagenes_alt/icon_imagen.png";
    private final String RUTA_RECURSO_FONDO_PANEL = "/fondos/fondo_jugador_principal.png";

    private static final Logger LOGGER = Logger.getLogger(PanelJugadorPrincipal.class.getName());

    private Image imagenFondo;
    private ImageIcon fotoJugador;

    /**
     * Constructor que inicializa el panel del jugador principal.
     */
    public PanelJugadorPrincipal() {
        initComponents();
        cargarFondo();
        cargarFotoDefault();
    }

    /**
     * Metodo que crea todos los componentes del JPanel.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelIcono = new javax.swing.JLabel();
        labelNombreJugador = new javax.swing.JLabel();
        labelPuntaje = new javax.swing.JLabel();

        labelIcono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes_alt/icon_imagen.png"))); // NOI18N

        labelNombreJugador.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        labelNombreJugador.setForeground(new java.awt.Color(255, 255, 255));
        labelNombreJugador.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelNombreJugador.setText("Jugador 1");

        labelPuntaje.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        labelPuntaje.setForeground(new java.awt.Color(255, 255, 255));
        labelPuntaje.setText("Puntaje: 0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(labelIcono, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelNombreJugador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addComponent(labelPuntaje, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(labelIcono, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(labelNombreJugador)
                .addGap(27, 27, 27)
                .addComponent(labelPuntaje)
                .addContainerGap(44, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Metodo que se encarga de cargar el fondo del panel
     */
    private void cargarFondo() {
        URL recurso = getClass().getResource(RUTA_RECURSO_FONDO_PANEL);
        if (recurso != null) {
            imagenFondo = new ImageIcon(recurso).getImage();
        } else {
            LOGGER.warning("No se encontró el fondo del jugador principal.");
        }
    }

    /**
     * Metodo que carga la foto o avatar del jugador.
     */
    private void cargarFotoDefault() {
        fotoJugador = cargarYEscalarImagen(RUTA_RECURSO_ICONO_DEFAULT);
        labelIcono.setIcon(fotoJugador);
    }

    /**
     * Carga una imagen desde una ruta de recurso, la escala a un tamaño
     * predefinido y la devuelve como un ImageIcon. Si la ruta del recurso no es
     * válida, registra una advertencia y devuelve un ImageIcon vacío.
     *
     * @param ruta La ruta interna del recurso de la imagen a cargar.
     * @return Un {@link ImageIcon} con la imagen escalada, o un ImageIcon vacío
     * si el recurso no se encontró.
     */
    private ImageIcon cargarYEscalarImagen(String ruta) {
        URL recurso = getClass().getResource(ruta);
        if (recurso == null) {
            LOGGER.log(Level.WARNING, "No se encontr\u00f3 la imagen: {0}", ruta);
            return new ImageIcon();
        }
        Image original = new ImageIcon(recurso).getImage();
        Image escalada = original.getScaledInstance(ANCHO_ICONO_JUGADOR, ALTO_ICONO_JUGADOR, Image.SCALE_SMOOTH);
        return new ImageIcon(escalada);
    }

    /**
     * Sobrescribe el método para dibujar componentes personalizados. En este
     * caso, se utiliza para pintar la imagen de fondo del panel.
     *
     * @param g el contexto gráfico {@link Graphics} en el que se va a pintar.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagenFondo != null) {
            g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
        }
    }

    /**
     * Actualiza los componentes de la interfaz (nombre, puntaje y avatar) para
     * mostrar la información del jugador proporcionado.
     *
     * @param jugador El objeto {@link JugadorSubject} que contiene los datos a
     * mostrar.
     */
    public void setJugador(JugadorSubject jugador) {
        labelNombreJugador.setText(jugador.getNickname());
        labelPuntaje.setText("Puntaje: " + jugador.getPuntaje());

        String rutaAvatar = obtenerRutaAvatar(jugador.getFoto());
        fotoJugador = cargarYEscalarImagen(rutaAvatar);
        if (fotoJugador.getImage() != null) {
            labelIcono.setIcon(fotoJugador);
        }

        repaint();
        revalidate();
    }

    /**
     * Método auxiliar para convertir el ID del avatar en una ruta de archivo.
     * AJUSTA los nombres de los archivos según tus recursos reales.
     */
    private String obtenerRutaAvatar(int idAvatar) {
        // Ejemplo: Si tus avatares son "avatar_1.png", "avatar_2.png", etc.
        // Si el id es 0 o inválido, regresa el default.
        if (idAvatar <= 0) return RUTA_RECURSO_ICONO_DEFAULT;
        
        // Ajusta esta ruta a donde tengas tus avatares guardados
        return "/avatars/" + idAvatar + ".jpg"; 
    }
    
    /**
     * Comprueba si el jugador proporcionado es el mismo que se está mostrando
     * actualmente en este panel, basándose en la comparación de sus nicknames.
     *
     * @param jugador El jugador a comparar.
     * @return {@code true} si el nickname del jugador coincide con el del
     * panel, {@code false} en caso contrario.
     */
    public boolean esMismoJugador(JugadorSubject jugador) {
        return labelNombreJugador.getText().equals(jugador.getNickname());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel labelIcono;
    private javax.swing.JLabel labelNombreJugador;
    private javax.swing.JLabel labelPuntaje;
    // End of variables declaration//GEN-END:variables
}
