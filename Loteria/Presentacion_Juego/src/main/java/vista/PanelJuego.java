package vista;

import control.ControlAbandonarPartida;
import control.RegistroControles;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import modelo.*;
import util.Subject;

/**
 * clase que representa el panel principal de la interfaz del juego. Se encarga
 * de mostrar el fondo de la partida, el jugador principal, los jugadores
 * secundarios, los botones de jugadas disponibles y la carta del cantador.
 * además, actualiza la vista en función de los cambios de los JugadorSubject
 * que observa.
 *
 * @author pedro
 */
public class PanelJuego extends javax.swing.JPanel {

    private Image imagenFondo;
    private List<PanelJugadorSecundario> panelesJugadoresSecundarios;

    ActionListener listenerCuatroEsquinas = evt -> {
        RegistroControles.getInstance().getControlJugadas().cantarCuatroEsquinas();
    };

    ActionListener listenerChorro = evt -> {
        RegistroControles.getInstance().getControlJugadas().cantarChorro();
    };

    ActionListener listenerCentro = evt -> {
        RegistroControles.getInstance().getControlJugadas().cantarCentro();
    };

    ActionListener listenerLlena = evt -> {
        RegistroControles.getInstance().getControlJugadas().cantarLlena();
    };

    /**
     * Constructor que crea un nuevo panel de juego, inicializando sus
     * componentes y cargando los botones de jugadas.
     */
    public PanelJuego() {
        initComponents();
        dibujarFondo();

        panelesJugadoresSecundarios = new ArrayList<>();
        botonCuatroEsquinas.setNombreJugada("Cuatro esquinas");
        botonChorro.setNombreJugada("Chorro");
        botonCentro.setNombreJugada("Centro");
        botonLlena.setNombreJugada("Llena");

        agregarListenersBotones();
    }

    public void agregarListenersBotones() {
        botonCuatroEsquinas.addActionListener(listenerCuatroEsquinas);
        botonCuatroEsquinas.setEstado(true);

        botonChorro.addActionListener(listenerChorro);
        botonChorro.setEstado(true);

        botonCentro.addActionListener(listenerCentro);
        botonCentro.setEstado(true);

        botonLlena.addActionListener(listenerLlena);
        botonLlena.setEstado(true);
    }

    /**
     * Metodo que crea todos los componentes del JPanel.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        botonAbandonar = new javax.swing.JButton();
        panelJugadorPrincipal = new vista.PanelJugadorPrincipal();
        botonCuatroEsquinas = new vista.BotonJugada();
        botonChorro = new vista.BotonJugada();
        botonCentro = new vista.BotonJugada();
        botonLlena = new vista.BotonJugada();
        panelContenedorSecundarios = new javax.swing.JPanel();
        panelJugadorSecundario2 = new vista.PanelJugadorSecundario();
        panelTarjeta = new vista.PanelTarjeta();
        panelContenedorCantador = new javax.swing.JPanel();
        panelCartaCantador = new vista.PanelCartita();
        lblCarta = new javax.swing.JLabel();
        panelNotificacion = new vista.PanelNotificacion();

        setMaximumSize(new java.awt.Dimension(1336, 768));
        setMinimumSize(new java.awt.Dimension(1336, 768));
        setPreferredSize(new java.awt.Dimension(1336, 768));

        botonAbandonar.setBackground(new java.awt.Color(235, 91, 0));
        botonAbandonar.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        botonAbandonar.setForeground(new java.awt.Color(255, 255, 255));
        botonAbandonar.setText("Abandonar");
        botonAbandonar.setFocusPainted(false);
        botonAbandonar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAbandonarActionPerformed(evt);
            }
        });

        botonCuatroEsquinas.setRequestFocusEnabled(false);

        panelContenedorSecundarios.setBackground(new java.awt.Color(255, 178, 0));
        panelContenedorSecundarios.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 29, 0));
        panelContenedorSecundarios.add(panelJugadorSecundario2);

        panelContenedorCantador.setBackground(new java.awt.Color(255, 255, 255));

        panelCartaCantador.setToolTipText("Carta Cantada");

        javax.swing.GroupLayout panelContenedorCantadorLayout = new javax.swing.GroupLayout(panelContenedorCantador);
        panelContenedorCantador.setLayout(panelContenedorCantadorLayout);
        panelContenedorCantadorLayout.setHorizontalGroup(
            panelContenedorCantadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelContenedorCantadorLayout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(panelCartaCantador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );
        panelContenedorCantadorLayout.setVerticalGroup(
            panelContenedorCantadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelContenedorCantadorLayout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(panelCartaCantador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        lblCarta.setBackground(new java.awt.Color(0, 0, 0));
        lblCarta.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblCarta.setText("Carta");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(217, 217, 217)
                .addComponent(panelContenedorSecundarios, javax.swing.GroupLayout.PREFERRED_SIZE, 914, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(160, 160, 160)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelJugadorPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonAbandonar, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(114, 114, 114))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(panelNotificacion, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(panelTarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(180, 180, 180)
                        .addComponent(lblCarta))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(panelContenedorCantador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(botonCuatroEsquinas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(botonChorro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(botonCentro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(botonLlena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(panelContenedorSecundarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelTarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, 579, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(lblCarta)
                        .addGap(13, 13, 13)
                        .addComponent(panelContenedorCantador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(75, 75, 75)
                        .addComponent(botonCuatroEsquinas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(botonChorro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonCentro, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addComponent(botonLlena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(panelJugadorPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(62, 62, 62)
                        .addComponent(botonAbandonar, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(panelNotificacion, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void botonAbandonarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAbandonarActionPerformed
        ControlAbandonarPartida control = RegistroControles.getInstance().getControlAbandonarPartida();
        control.abandonarPartida();
    }//GEN-LAST:event_botonAbandonarActionPerformed

    /**
     * Metodo para inicializa el fondo del panel de juego con una imagen
     * predeterminada.
     */
    private void dibujarFondo() {
        imagenFondo = new javax.swing.ImageIcon(getClass().getResource("/fondos/fondoPantalla.png")).getImage();
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
     * Metodo que actualiza la vista del panel en función de los cambios del
     * Subject.
     *
     * @param subject el objeto observado que notifica los cambios.
     */
    public void actualizarVista(Subject subject) {
        actualizarJugador(subject);
        actualizarCantador(subject);
        actualizarJugada(subject);
        repaint();
        revalidate();
    }

    /**
     * metodo que actualiza la información de un jugador en la interfaz.
     *
     * @param subject representa a un jugador.
     */
    private void actualizarJugador(Subject subject) {
        if (subject instanceof JugadorSubject jugador) {

            if (panelJugadorPrincipal != null && panelJugadorPrincipal.esMismoJugador(jugador)) {
                panelJugadorPrincipal.setJugador(jugador);
                panelTarjeta.actualizarFichas(jugador.getTarjeta());
                return;
            }

            boolean jugadorEncontradoEnSecundarios = false;
            List<PanelJugadorSecundario> copiaPaneles = new ArrayList<>(panelesJugadoresSecundarios);

            for (PanelJugadorSecundario panel : copiaPaneles) {
                if (panel.esMismoJugador(jugador)) {
                    panel.setJugador(jugador);
                    jugadorEncontradoEnSecundarios = true;
                    break;
                }
            }

            if (jugadorEncontradoEnSecundarios) {
                actualizarPanelesSecundarios();
                return;
            }

            if (jugador.isJugadorPrincipal()) {
                this.setJugadorPrincipal(jugador);
            } else {
                agregarJugadorSecundario(jugador);
                actualizarPanelesSecundarios();
            }
        }
    }

    private void actualizarCantador(Subject subject) {
        if (subject instanceof CantadorSubject cantador) {
            if (panelCartaCantador != null) {
                panelCartaCantador.actualizarCartaCantador(cantador.getCartaActual());
            }
        }
    }

    /**
     * metodo que define el jugador principal de la partida.
     *
     * @param jugador objeto que representa al jugador principal.
     */
    public void setJugadorPrincipal(JugadorSubject jugador) {
        panelJugadorPrincipal.setJugador(jugador);
        // Actualizar la tarjeta mostrada con la tarjeta real del jugador
        panelTarjeta.actualizarTarjeta(jugador.getTarjeta());
        repaint();
        revalidate();
    }

    /**
     * Metodo para agregar un nuevo jugador secundario al panel.
     *
     * @param jugador objetoque representa al jugador secundario.
     */
    public void agregarJugadorSecundario(JugadorSubject jugador) {
        PanelJugadorSecundario panel = new PanelJugadorSecundario();
        panel.setJugador(jugador);
        this.panelesJugadoresSecundarios.add(panel);
        actualizarPanelesSecundarios();
    }

    /**
     * Metodo para eliminar un jugador secundario del panel. Busca por nickname,
     * lo elimina de la lista y actualiza la vista.
     *
     * @param jugador El jugador a eliminar
     */
    public void eliminarJugador(JugadorSubject jugador) {
        PanelJugadorSecundario aEliminar = null;
        for (PanelJugadorSecundario panel : panelesJugadoresSecundarios) {
            if (panel.esMismoJugador(jugador)) {
                aEliminar = panel;
                break;
            }
        }

        if (aEliminar != null) {
            panelesJugadoresSecundarios.remove(aEliminar);
            actualizarPanelesSecundarios();
        }
    }

    /**
     * Metodo que refresca la lista de paneles de jugadores secundarios en la
     * interfaz.
     */
    private void actualizarPanelesSecundarios() {
        panelContenedorSecundarios.removeAll();
        for (PanelJugadorSecundario panel : panelesJugadoresSecundarios) {
            panelContenedorSecundarios.add(panel);
        }
        repaint();
        revalidate();
    }

    private void actualizarJugada(Subject subject) {
        if (subject instanceof JugadaSubject jugada) {
            String mensaje = jugada.getJugador() + " cantó: " + jugada.getNombre() + "! +" + jugada.getPuntos();

            javax.swing.SwingUtilities.invokeLater(() -> {
                this.mostrarNotificacion(mensaje);
            });

            switch (jugada.getNombre()) {
                case "LLENA" -> {
                    botonLlena.setEstado(false);
                    botonLlena.removeActionListener(listenerLlena);
                }
                case "CHORRO" -> {
                    botonChorro.setEstado(false);
                    botonChorro.removeActionListener(listenerChorro);
                }
                case "CUATROESQUINAS" -> {
                    botonCuatroEsquinas.setEstado(false);
                    botonCuatroEsquinas.removeActionListener(listenerCuatroEsquinas);
                }
                case "CENTRO" -> {
                    botonCentro.setEstado(false);
                    botonCentro.removeActionListener(listenerCentro);
                }
                default -> {
                    System.out.println("No existe la jugada: " + jugada.getNombre());
                }
            }
        }
    }

    private void mostrarNotificacion(String mensaje) {
        if (panelNotificacion != null) {
            panelNotificacion.mostrarMensaje(mensaje);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonAbandonar;
    private vista.BotonJugada botonCentro;
    private vista.BotonJugada botonChorro;
    private vista.BotonJugada botonCuatroEsquinas;
    private vista.BotonJugada botonLlena;
    private javax.swing.JLabel lblCarta;
    private vista.PanelCartita panelCartaCantador;
    private javax.swing.JPanel panelContenedorCantador;
    private javax.swing.JPanel panelContenedorSecundarios;
    private vista.PanelJugadorPrincipal panelJugadorPrincipal;
    private vista.PanelJugadorSecundario panelJugadorSecundario2;
    private vista.PanelNotificacion panelNotificacion;
    private vista.PanelTarjeta panelTarjeta;
    // End of variables declaration//GEN-END:variables
}
