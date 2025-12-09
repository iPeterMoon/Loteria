package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PanelNotificacion extends JComponent {

    private String mensaje = "";
    private Timer timer;
    private float opacidad = 0.0f;
    private final int DURACION_MS = 2000; // 2s
    private boolean mostrando = false;

    public PanelNotificacion() {
        setOpaque(false);
    }

    public void mostrarMensaje(String msg) {
        System.out.println(msg);
        
        this.mensaje = msg;
        this.mostrando = true;
        this.opacidad = 1.0f;

        // Reiniciar timer si ya estaba corriendo
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }

        // Timer para desvanecer o quitar el mensaje
        timer = new Timer(DURACION_MS, (ActionEvent e) -> {
            ocultarMensaje();
        });
        timer.setRepeats(false);
        timer.start();

        repaint();
        setVisible(true);
    }

    private void ocultarMensaje() {
        mostrando = false;
        setVisible(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (!mostrando) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Configuración de fuente
        g2.setFont(new Font("Segoe UI", Font.BOLD, 24));
        FontMetrics fm = g2.getFontMetrics();
        int textoAncho = fm.stringWidth(mensaje);

        int textoAlto = fm.getAscent();
        int x = (getWidth() - textoAncho) / 2;
        int y = (getHeight() / 2) + (textoAlto / 4);

        int padding = 10;

        // Dibujar fondo
        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRoundRect(x - padding, y - textoAlto - padding, textoAncho + (padding * 2), textoAlto + (padding * 2), 30, 30);

        // Dibujar borde
        g2.setColor(new Color(235, 91, 0));
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(x - padding, y - textoAlto - padding, textoAncho + (padding * 2), textoAlto + (padding * 2), 30, 30);

        // Dibujar Texto
        g2.setColor(Color.WHITE);
        g2.drawString(mensaje, x, y);

        g2.dispose();
    }
}
