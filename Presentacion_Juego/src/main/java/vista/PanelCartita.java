package vista;

import control.ControlSeleccionarJugada;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Jp
 */
public class PanelCartita extends javax.swing.JPanel {

    private static final int CARTA_ANCHO_TARJETA = 78;
    private static final int CARTA_ALTO_TARJETA = 128;
    private static final int CARTA_ANCHO_CANTADOR = 160;
    private static final int CARTA_ALTO_CANTADOR = 240;
    private static final int FICHA_ANCHO = 50;
    private static final int FICHA_ALTO = 50;

    private static final float FACTOR_BRILLO = 1.2f;

    private int numeroCarta;
    private Point posicion;
    private ImageIcon iconoOriginal;
    private ImageIcon iconoHover;
    private ImageIcon iconoActual;
    private boolean flagCantador;
    private JLabel ficha;
    private boolean esAbstracta = false;

    /**
     * Constructor para que se vea en el design de netbeans
     */
    public PanelCartita() {
        initComponents();
        this.flagCantador = true;
        this.numeroCarta = 1;
        iniciar();
    }

    /**
     * Creates new form PanelCartita
     *
     * @param numeroCarta
     * @param posicion
     */
    public PanelCartita(int numeroCarta, Point posicion) {
        initComponents();
        this.numeroCarta = numeroCarta;
        this.posicion = posicion;
        this.flagCantador = false;
        iniciar();
    }
    
    public PanelCartita(boolean abstracta, int numeroCarta) {
        initComponents();
        this.numeroCarta = numeroCarta;
        this.esAbstracta = abstracta;

        if (abstracta) {
            this.flagCantador = false;
            this.iconoOriginal = null;
            this.iconoActual = null;
            this.setBackground(Color.LIGHT_GRAY);
            this.setOpaque(true);
        } else {
            iniciar();
        }
    }

    private void iniciar() {
        iconoOriginal = cargarCarta(numeroCarta);
        if (!flagCantador) {
            iconoHover = crearBrillo(iconoOriginal, FACTOR_BRILLO);
            ficha = cargarFicha();
            agregarEventosMouse();
        }
        iconoActual = iconoOriginal;
    }

    private void agregarEventosMouse() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                iconoActual = iconoHover;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                iconoActual = iconoOriginal;
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                onclick();
            }
        });
    }

    private void onclick() {
        ControlSeleccionarJugada.colocarFicha(posicion);
    }

    public void agregarFicha() {
        if (ficha != null && ficha.getParent() == null) {
            int x = (getWidth() - FICHA_ANCHO) / 2;
            int y = (getHeight() - FICHA_ALTO) / 2;
            ficha.setBounds(x, y, FICHA_ANCHO, FICHA_ALTO);
            add(ficha);
            revalidate();
            repaint();
        }
    }

    private ImageIcon cargarCarta(int numero) {
        String url = "/cartas/" + numero + ".jpeg";
        URL recurso = getClass().getResource(url);
        if (recurso == null) {
            System.err.println("No se encontró la imagen: " + url);
        }
        ImageIcon original = new ImageIcon(recurso);
        Image imagenEscalada = original.getImage().getScaledInstance(CARTA_ANCHO_TARJETA, CARTA_ALTO_TARJETA, Image.SCALE_SMOOTH);
        if (flagCantador) {
            imagenEscalada = original.getImage().getScaledInstance(CARTA_ANCHO_CANTADOR, CARTA_ALTO_CANTADOR, Image.SCALE_SMOOTH);
            setMinimumSize(new Dimension(160, 240));
            setMaximumSize(new Dimension(160, 240));
            setPreferredSize(new Dimension(160, 240));
        }
        return new ImageIcon(imagenEscalada);
    }

    private JLabel cargarFicha() {
        String url = "/otros/ficha.png";
        URL recurso = getClass().getResource(url);
        if (recurso == null) {
            System.err.println("No se encontró la ficha: " + url);
        }
        ImageIcon original = new ImageIcon(recurso);
        Image imagenEscalada = original.getImage().getScaledInstance(FICHA_ANCHO, FICHA_ALTO, Image.SCALE_SMOOTH);
        return new JLabel(new ImageIcon(imagenEscalada));
    }

    private ImageIcon crearBrillo(ImageIcon icono, float factor) {
        Image img = icono.getImage();
        BufferedImage buff = new BufferedImage(
                img.getWidth(null),
                img.getHeight(null),
                BufferedImage.TYPE_INT_ARGB
        );
        buff.getGraphics().drawImage(img, 0, 0, null);

        RescaleOp op = new RescaleOp(factor, 0, null);
        op.filter(buff, buff);

        return new ImageIcon(buff);
    }
    
    public void marcarComoJugadorSecundario() {
        if (esAbstracta) {
            setBackground(Color.DARK_GRAY);
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (iconoActual != null) {
            g.drawImage(iconoActual.getImage(), 0, 0, getWidth(), getHeight(), this);
        }

    }

    public boolean isFlagCantador() {
        return flagCantador;
    }

    public void setFlagCantador(boolean flagCantador) {
        this.flagCantador = flagCantador;
    }

    public Point getPosicion(){
        return posicion;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        setMinimumSize(new Dimension(CARTA_ANCHO_TARJETA, CARTA_ALTO_TARJETA));
        setOpaque(false);
        setPreferredSize(new Dimension(CARTA_ANCHO_TARJETA, CARTA_ALTO_TARJETA));
        setLayout(null);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
