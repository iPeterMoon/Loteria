/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package vista;

import Modal.MensajeDialog;
import controladores.ControlesConfiguracionFactory;
import enums.TipoMensajePantalla;
import java.awt.Frame;
import java.net.URL;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import modelo.MensajeSubject;
import modelo.ModeloVistaConfiguracionFacade;
import modelo.SalaSubject;
import util.Subject;

/**
 *
 * @author norma
 */
public class PanelSalaEspera extends javax.swing.JPanel {

    private Frame ventanaPrincipal;

    /**
     * Creates new form PanelSalaEspera
     */
    public PanelSalaEspera() {
        initComponents();
    }

    public void configurarFramePadre(Frame ventanaPadre) {
        ventanaPrincipal = ventanaPadre;
    }

    private int contador = 1;

    public void actualizarVista(Subject subject) {
        if (subject instanceof SalaSubject salaSubject) {
            actualizarDatosSala(salaSubject);
            this.repaint();
            this.revalidate();
        } else if (subject instanceof MensajeSubject){
            actualizarMensaje(subject);
        }
    }
    public void actualizarDatosSala(SalaSubject sala) {
        if(sala.getHost() == null){
            panelListaJugadores.actualizarListaJugadores(new ArrayList<>(), 4);
            configurarModoJugadorNoUnido();
            return;
        }
        if(sala.getHost() != null){
            panelListaJugadores.actualizarListaJugadores(sala.getJugadores(), sala.getLimiteJugadores());
            labelNivel.setText(sala.getNivel().toString());
            actualizarBotones(sala);
            panelListaJugadores.revalidate();
            panelListaJugadores.repaint();
            
            if(contador == 8){
                tirarEfectoSonido();
                contador = 1;
            } else {
                contador++;
            }
        }
    }

    private void tirarEfectoSonido() {
        try {
            String rutaAudio = "/audios/fx/unirse.wav";
            URL url = getClass().getResource(rutaAudio);
            if (url != null) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(url);
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.start();
            }
        } catch (Exception e) {
            System.out.println("[FICHA] Error al reproducir el audio");
            System.out.println(e.getMessage());
        }
    }

    public void configurarModoJugadorUnido() {
        btnAbandonarSala.setVisible(true);
        lblEspera.setVisible(true);
        btnAccion.setVisible(false);
    }

    public void configurarModoJugadorNoUnido() {
        btnAccion.setVisible(true);
        lblEspera.setVisible(false);
        btnAbandonarSala.setVisible(false);
    }

    private void configurarModoJugadorUnido(SalaSubject sala) {
        String principalNickname = sala.getJugadorPrincipalUser();

        btnAbandonarSala.setVisible(true);
        lblEspera.setVisible(true); 
        btnAccion.setVisible(false); 
        
        if (principalNickname != null && principalNickname.equals(sala.getHost())) {
            btnAccion.setText("Iniciar Partida");
            btnAccion.setVisible(true);
            lblEspera.setVisible(false);
        } else {
            lblEspera.setVisible(true);
            btnAccion.setVisible(false);
        }
    }

    private void configurarModoJugadorNoUnidoLimite(SalaSubject sala) {
        configurarModoJugadorNoUnido(); 

        int jugadoresActuales = sala.getJugadores().size();
        int limite = sala.getLimiteJugadores();

        if (jugadoresActuales >= limite) {
            btnAccion.setVisible(false); 
            lblEspera.setText("Sala llena.");
            lblEspera.setVisible(true); 
            btnAbandonarSala.setVisible(false); 
        } else {
            btnAccion.setText("Unirme a la sala"); 
            btnAccion.setVisible(true);
            lblEspera.setVisible(false);
            btnAbandonarSala.setVisible(false); 
        }
    }

    private void actualizarBotones(SalaSubject sala) {
        String principalNickname = sala.getJugadorPrincipalUser();

        boolean esJugadorUnido = false;
        if(sala.getJugadores() != null && principalNickname != null){
            esJugadorUnido = sala.getJugadores().stream()
                    .anyMatch(j -> j.getNickname().equals(principalNickname));
        }

        if (principalNickname != null && esJugadorUnido) {
            configurarModoJugadorUnido(sala);
        } else {
            configurarModoJugadorNoUnidoLimite(sala);
        }

        repaint();
        revalidate();
    }

    private void actualizarMensaje(Subject subject) {
        if (subject instanceof MensajeSubject validacion) {
            if (validacion.getTipoMensaje() == TipoMensajePantalla.VALIDACION_SALA_ESPERA || validacion.getTipoMensaje() == TipoMensajePantalla.INFORMACION) {
                MensajeDialog mensajeDialog = new MensajeDialog(ventanaPrincipal, true);
                mensajeDialog.setDatos(validacion.getTitulo(), validacion.getMensaje());
                mensajeDialog.mostrarDialogo();

                if (validacion.isExitoso()) {
                    // Si el mensaje es de fin de juego, cerrar ventana de juego y mostrar menú
                    if (validacion.getTitulo().contains("FIN DEL JUEGO")) {
                        cerrarVentanaJuego();
                        // El flujo ya fue manejado por mostrarMenuPrincipal() en FinalizarJuegoManager
                        // No hacer nada aquí, dejar que el menú se muestre
                    } else {
                        ControlesConfiguracionFactory controles = ControlesConfiguracionFactory.getInstance();
                        controles.getControlAplicacion().mostrarPanelSalaEsperaJuego();
                    }
                }
            }
        }
    }
    
    private void cerrarVentanaJuego() {
        try {
            // Usar reflexión para evitar dependencias directas entre módulos de presentación
            Class<?> frameJuegoClass = Class.forName("vista.FrameJuego");
            java.lang.reflect.Method destruirMethod = frameJuegoClass.getMethod("destruirInstancia");
            destruirMethod.invoke(null);
        } catch (Exception e) {
            System.err.println("Error al cerrar ventana de juego: " + e.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblSala = new javax.swing.JLabel();
        btnAbandonarSala = new javax.swing.JButton();
        lblNivel = new javax.swing.JLabel();
        labelNivel = new javax.swing.JLabel();
        lblEspera = new javax.swing.JLabel();
        btnAccion = new javax.swing.JButton();
        panelListaJugadores = new vista.PanelListaJugadores();

        setBackground(new java.awt.Color(255, 178, 0));

        lblSala.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        lblSala.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSala.setText("Sala");

        btnAbandonarSala.setBackground(new java.awt.Color(235, 91, 0));
        btnAbandonarSala.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnAbandonarSala.setForeground(new java.awt.Color(255, 255, 255));
        btnAbandonarSala.setText("Abandonar Sala");
        btnAbandonarSala.setFocusPainted(false);
        btnAbandonarSala.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbandonarSalaActionPerformed(evt);
            }
        });

        lblNivel.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        lblNivel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNivel.setText("Nivel");

        labelNivel.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        labelNivel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelNivel.setText("Básico");

        lblEspera.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblEspera.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEspera.setText("Esperando a que inicie la partida...");

        btnAccion.setBackground(new java.awt.Color(100, 13, 95));
        btnAccion.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnAccion.setForeground(new java.awt.Color(255, 255, 255));
        btnAccion.setText("Unirme a la sala");
        btnAccion.setFocusPainted(false);
        btnAccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAccionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSala, javax.swing.GroupLayout.PREFERRED_SIZE, 1275, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelListaJugadores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAbandonarSala, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(47, 47, 47)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAccion, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNivel, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelNivel, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEspera, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblSala)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(panelListaJugadores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(lblNivel)
                        .addGap(18, 18, 18)
                        .addComponent(labelNivel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblEspera)
                        .addGap(61, 61, 61)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAccion, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAbandonarSala, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(65, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAbandonarSalaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbandonarSalaActionPerformed
        ControlesConfiguracionFactory controles = ControlesConfiguracionFactory.getInstance();
        controles.getControlConfiguracion().abandonarSala();
    }//GEN-LAST:event_btnAbandonarSalaActionPerformed

    private void btnAccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAccionActionPerformed
        if (btnAccion.getText().equals("Unirme a la sala")) {
            ControlesConfiguracionFactory controles = ControlesConfiguracionFactory.getInstance();
            controles.getControlAplicacion().unirseASala();
        } else if (btnAccion.getText().equals("Iniciar Partida")) {
            ControlesConfiguracionFactory controles = ControlesConfiguracionFactory.getInstance();
            controles.getControlIniciarPartida().iniciarPartida();
            // Cerrar la ventana de configuración a través del modelo
            ModeloVistaConfiguracionFacade.getInstance().cerrarVentana();
        }
    }//GEN-LAST:event_btnAccionActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAbandonarSala;
    private javax.swing.JButton btnAccion;
    private javax.swing.JLabel labelNivel;
    private javax.swing.JLabel lblEspera;
    private javax.swing.JLabel lblNivel;
    private javax.swing.JLabel lblSala;
    private vista.PanelListaJugadores panelListaJugadores;
    // End of variables declaration//GEN-END:variables
}
