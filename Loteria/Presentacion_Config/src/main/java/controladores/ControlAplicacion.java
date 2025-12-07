package controladores;

import enums.Pantalla;
import modelo.IModeloControlAplicacion;

/**
 *
 * @author Alici
 */
public class ControlAplicacion {

    private final IModeloControlAplicacion controlModelo;

    public ControlAplicacion(IModeloControlAplicacion controlModelo) {
        if (controlModelo == null) {
            throw new IllegalArgumentException("La dependencia IModeloControlAplicacion no puede ser nula.");
        }
        this.controlModelo = controlModelo;
    }

    public void cambiarAvatar(int accion) {
        controlModelo.siguienteAvatar(accion);
    }

    public void mostrarPanelMenu() {
        controlModelo.siguientePantalla(Pantalla.MENU);
    }

    public void mostrarPanelConfigurarUsuario() {
        controlModelo.siguientePantalla(Pantalla.CONFIGURAR_USUARIO);
    }

    public void mostrarPanelConfigurarSala() {
        controlModelo.siguientePantalla(Pantalla.CONFIGURAR_PARTIDA);
    }

    /**
     * Método para redirigir al panel de la sala creada, donde existe una sala
     * pero el jugador aun no se une
     */
    public void mostrarPanelSala() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * Método para redirigir al panel de la sala despues de configurar su
     * usuario para unirse
     */
    public void mostrarPanelSalaEsperaJuego() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
