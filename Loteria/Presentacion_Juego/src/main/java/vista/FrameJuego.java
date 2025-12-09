package vista;

import modelo.JugadorSubject;
import util.Subject;

/**
 * Clase Frame que genara el Frame  principal del juego.
 * @author pedro
 */
public class FrameJuego extends javax.swing.JFrame {
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrameJuego.class.getName());
    private PanelJuego panelJuego;
    private static FrameJuego frameJuego;

    /**
     * Constructor que crea el frame principal del juego.
     */
    private FrameJuego() {
        initComponents();
    }
    
    /**
     * Metodo statico que retorna la instancia de FrameJuego.
     * @return instancia de FrameJuego.
     */
    public static FrameJuego getInstance(){
        if(frameJuego==null){
            frameJuego = new FrameJuego();
        }
        return frameJuego;
    }

    /**
     * Metodo que crea todo el componente Frame. 
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelJuego = new PanelJuego();
        getContentPane().setLayout(new java.awt.BorderLayout());
        getContentPane().add(panelJuego, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /**
     * Metodo para actualizar la vista del jugador.  
     * @param subject representacion del sujeto.
     */
    public void actualizarVista(Subject subject){
        panelJuego.actualizarVista(subject);
    }
    
    /**
     * Método para eliminar un jugador de la vista.
     * Delega la acción al panel principal del juego.
     * * @param nickname El nombre del jugador a eliminar.
     */
    public void eliminarJugador(JugadorSubject jugador){
        if (panelJuego != null) {
            panelJuego.eliminarJugador(jugador);
        }
    }
    
    public static void destruirInstancia() {
        if (frameJuego != null) {
            frameJuego.dispose();
            frameJuego = null;
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
