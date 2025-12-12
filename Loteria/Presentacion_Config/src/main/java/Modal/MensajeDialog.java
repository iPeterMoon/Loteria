package Modal;

import java.awt.BorderLayout;
import java.awt.Frame;
import javax.swing.JDialog;

/**
 * Diálogo personalizado que extiende JDialog para mostrar mensajes de forma
 * modal.
 *
 * @author Alici
 */
public class MensajeDialog extends JDialog {

    /**
     * Componente PanelMensaje que contiene la interfaz visual del mensaje
     * (título, cuerpo del mensaje y el botón de aceptar).
     */
    private final PanelMensaje panelMensaje;

    /**
     * Constructor para crear un nuevo MensajeDialog.
     *
     * @param owner El Frame propietario (padre) del diálogo.
     * @param modal Un valor booleano que indica si el diálogo debe ser modal
     * (true) o no (false).
     */
    public MensajeDialog(Frame owner, boolean modal) {

        super(owner, modal);

        panelMensaje = new PanelMensaje();
        // Evita que el diálogo se cierre al pulsar la X
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        // Añade el panel de mensaje al centro del diálogo
        getContentPane().add(panelMensaje, BorderLayout.CENTER);

        // Define la acción para el botón Aceptar del panel
        panelMensaje.getBtnAceptar().addActionListener(e -> {
            // Oculta y libera los recursos del diálogo
            setVisible(false);
            dispose();
        });

        // Ajusta el tamaño del diálogo al contenido preferido
        pack();
        // Centra el diálogo en relación a su ventana propietaria
        setLocationRelativeTo(owner);
    }

    /**
     * Hace visible el diálogo, mostrando el mensaje al usuario.
     */
    public void mostrarDialogo() {
        setVisible(true);
    }

    /**
     * Establece el título y el cuerpo del mensaje que se mostrarán en el
     * diálogo.
     *
     * @param titulo La cadena de texto a establecer como título del mensaje.
     * @param mensaje La cadena de texto a establecer como cuerpo del mensaje.
     */
    public void setDatos(String titulo, String mensaje) {
        panelMensaje.setLblTitulo(titulo);
        panelMensaje.setLblMensaje(mensaje);
    }
}
