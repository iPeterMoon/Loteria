/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modal;

import java.awt.BorderLayout;
import java.awt.Frame;
import javax.swing.JDialog;

/**
 *
 * @author Alici
 */
public class MensajeDialog extends JDialog {

    private final PanelMensaje panelMensaje;

    public MensajeDialog(Frame owner, boolean modal) {

        super(owner, modal);

        panelMensaje = new PanelMensaje();
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        getContentPane().add(panelMensaje, BorderLayout.CENTER);

        panelMensaje.getBtnAceptar().addActionListener(e -> {
            setVisible(false);
            dispose();
        });

        pack();
        setLocationRelativeTo(owner);
    }

    public void mostrarDialogo() {
        setVisible(true);
    }

    public void setDatos(String titulo, String mensaje) {
        panelMensaje.setLblTitulo(titulo);
        panelMensaje.setLblMensaje(mensaje);
    }
}
