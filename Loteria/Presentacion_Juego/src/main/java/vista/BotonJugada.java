package vista;

/**
 * Clase que representa un botón dentro del juego que extiende de JPanel que
 * cumple con la funcion de cantar la jugada.
 */
public class BotonJugada extends javax.swing.JPanel {

    private boolean estado;
    private String nombreJugada;

    /**
     * Constructor que inicializa la interfaz grafica del botonJugada.
     */
    public BotonJugada() {
        initComponents();
        this.botonJugada.setText(nombreJugada);
    }

    /**
     * Metodo para obtener el estado del boton durante el juego.
     *
     * @return estado del botón.
     */
    public boolean isEstado() {
        return estado;
    }

    /**
     * Metodo para cambiar el estado del botón durante el juego.
     *
     * @param estado estadi del botón.
     */
    public void setEstado(boolean estado) {
        this.estado = estado;
        this.setEnabled(estado);
    }

    /**
     * metodo para obtener el nombre de la jugada.
     *
     * @return nombre de la jugada.
     */
    public String getNombreJugada() {
        return nombreJugada;
    }

    /**
     * metodo para asignar nombre al boton de la jugada.
     *
     * @param nombreJugada nombre de la jugada.
     */
    public void setNombreJugada(String nombreJugada) {
        this.nombreJugada = nombreJugada;
        this.botonJugada.setText(nombreJugada);
    }
    
    public void addActionListener(java.awt.event.ActionListener listener) {
        this.botonJugada.addActionListener(listener);
    }
    
    public void removeActionListener(java.awt.event.ActionListener listener) {
        this.botonJugada.removeActionListener(listener);
    }

    /**
     * Método generado automáticamente por el editor de formularios para
     * inicializar los componentes gráficos del panel.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        botonJugada = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(200, 47));
        setMinimumSize(new java.awt.Dimension(200, 47));

        botonJugada.setBackground(new java.awt.Color(100, 13, 95));
        botonJugada.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        botonJugada.setForeground(new java.awt.Color(255, 255, 255));
        botonJugada.setText("jButton1");
        botonJugada.setFocusPainted(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(botonJugada, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(botonJugada, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonJugada;
    // End of variables declaration//GEN-END:variables
}
