package main;

import controladores.ControlNavegacion;
import javax.swing.SwingUtilities;

/**
 *
 * @author Luis Rafael Lagarda Encinas 252605
 */
public class LoteriaMexicana {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ControlNavegacion controlNavegacion = new ControlNavegacion();
            controlNavegacion.iniciar();
        });
    }
}
