package vista;

import control.ControlSeleccionarCarta;
import control.RegistroControles;

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
 * clase que representa las 54 cartas del juego.
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
     * Constructor que inicializa el JPanel de la carta del cantador.
     */
    public PanelCartita() {
        initComponents();
        this.flagCantador = true;
        this.numeroCarta = 1;
        iniciar();
    }

    /**
     * Constructro que inicaliza el JPanel con la posicion de la carta.
     * 
     * @param numeroCarta indice de la carta.
     * @param posicion    posicion de la carta.
     */
    public PanelCartita(int numeroCarta, Point posicion) {
        initComponents();
        this.numeroCarta = numeroCarta;
        this.posicion = posicion;
        this.flagCantador = false;
        iniciar();
    }

    /**
     * Contructor que inicializa el JPanel que valida la carta.
     * 
     * @param abstracta   abstraccion.
     * @param numeroCarta indice de la carta.
     */
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

    /**
     * Metodo para inicializar la carta cargando su icono, la ficha y los eventos
     * del mouse.
     * Si la carta no pertenece al cantador, también se genera el efecto hover.
     */
    private void iniciar() {
        iconoOriginal = cargarCarta(numeroCarta);
        if (!flagCantador) {
            iconoHover = crearBrillo(iconoOriginal, FACTOR_BRILLO);
            ficha = cargarFicha();
            agregarEventosMouse();
        }
        iconoActual = iconoOriginal;
    }

    /**
     * Metodo para agrega eventos de interacción con el mouse para la carta
     * con mauseEntered que aplica el icono con brillo, mouseExited
     * que restaura el icono original y mouse Pressed qie ejecuta
     * el metood onclick.
     */
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

    /**
     * Metodo de accion ejecutada cuando se presiona la carta.
     * Llama al controlador para colocar una ficha en la posición correspondiente.
     */
    private void onclick() {
        RegistroControles controles = RegistroControles.getInstance();
        controles.getControlSeleccionarJugada().colocarFicha(posicion);
    }

    /**
     * Metodo para agregar visualmente una ficha al centro de la carta,
     * siempre y cuando la ficha no se haya agregado previamente.
     */
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

    /**
     * Metodo para carga la imagen de la carta según su número.
     * Escala la imagen dependiendo de si la carta pertenece al cantador o no.
     * 
     * @param numero número identificador de la carta.
     * @return el icono de la carta escalado.
     */
    private ImageIcon cargarCarta(int numero) {
        String url = "/cartas/" + numero + ".jpeg";
        URL recurso = getClass().getResource(url);
        if (recurso == null) {
            System.err.println("No se encontró la imagen: " + url);
        }
        ImageIcon original = new ImageIcon(recurso);
        Image imagenEscalada = original.getImage().getScaledInstance(CARTA_ANCHO_TARJETA, CARTA_ALTO_TARJETA,
                Image.SCALE_SMOOTH);
        if (flagCantador) {
            imagenEscalada = original.getImage().getScaledInstance(CARTA_ANCHO_CANTADOR, CARTA_ALTO_CANTADOR,
                    Image.SCALE_SMOOTH);
            setMinimumSize(new Dimension(160, 240));
            setMaximumSize(new Dimension(160, 240));
            setPreferredSize(new Dimension(160, 240));
        }
        return new ImageIcon(imagenEscalada);
    }
    
    public void actualizarCartaCantador(int carta) {
        iconoOriginal = cargarCarta(carta);
        iconoActual = iconoOriginal;
    }

    /**
     * Metodo que carga la ficha que puede colocarse sobre la carta.
     * 
     * @return un label con la imagen escalada de la ficha.
     */
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

    /**
     * Metodo para crea un efecto de brillo sobre un icono dado aplicando un factor
     * de rescale.
     * 
     * @param icono  imagen original a modificar.
     * @param factor intensidad del brillo.
     * @return un nueva imagen con el brillo aplicado.
     */
    private ImageIcon crearBrillo(ImageIcon icono, float factor) {
        Image img = icono.getImage();
        BufferedImage buff = new BufferedImage(
                img.getWidth(null),
                img.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);
        buff.getGraphics().drawImage(img, 0, 0, null);

        RescaleOp op = new RescaleOp(factor, 0, null);
        op.filter(buff, buff);

        return new ImageIcon(buff);
    }

    /**
     * Metodo para Marca visualmente la carta como perteneciente a un jugador
     * secundario,
     * cambiando el color de fondo si la carta es abstracta.
     */
    public void marcarComoJugadorSecundario() {
        if (esAbstracta) {
            setBackground(new Color(237, 67, 67));
            repaint();
        }
    }

    /**
     * Metodo protegio de JPanel para pintar el Jpanel.
     * 
     * @param g graficas de java.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (iconoActual != null) {
            g.drawImage(iconoActual.getImage(), 0, 0, getWidth(), getHeight(), this);
        }

    }

    /**
     * 
     * Metodo para verifica si la carta pertenece al cantador.
     * 
     * @return true si la carta es del cantador, de lo contrario false.
     */
    public boolean isFlagCantador() {
        return flagCantador;
    }

    /**
     * Metodo que define si la carta pertenece al cantador.
     * 
     * @param flagCantador valor booleano que indica si la carta es del cantador.
     */
    public void setFlagCantador(boolean flagCantador) {
        this.flagCantador = flagCantador;
    }

    /**
     * Metodo que obtiene la posición de la carta dentro del juego.
     * 
     * @return un objeto point con las coordenadas de la carta.
     */
    public Point getPosicion() {
        return posicion;
    }

    /**
     * Metodo para asignar posicion a las cartas.
     * 
     * @param posicion valor con las nuevas coordenadas.
     */
    public void setPosicion(Point posicion) {
        this.posicion = posicion;
    }

    /**
     * Metodo que crea todos los componentes del JPanel
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
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
