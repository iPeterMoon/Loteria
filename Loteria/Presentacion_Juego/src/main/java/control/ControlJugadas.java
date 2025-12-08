package control;

import modelo.IModeloControl;

/**
 * Clase encargada de gestionar el control de las jugadas del juego.
 * <p>
 * Actúa como intermediario entre la vista y el modelo, delegando al
 * {@link IModeloControl} la verificación de cada tipo de jugada.
 * </p>
 *
 * @author Jp
 */
public class ControlJugadas {

    /**
     * Referencia al modelo encargado de validar las jugadas.
     */
    private final IModeloControl controlModelo;

    /**
     * Constructor que inicializa el controlador de jugadas.
     *
     * @param controlModelo Implementación del modelo de control que se usará
     * para verificar las jugadas.
     * @throws IllegalArgumentException si el modelo recibido es nulo.
     */
    public ControlJugadas(IModeloControl controlModelo) {
        if (controlModelo == null) {
            throw new IllegalArgumentException("La dependencia IModeloControl no puede ser nula.");
        }
        this.controlModelo = controlModelo;
    }

    /**
     * Solicita al modelo la verificación de la jugada de tipo Llena.
     */
    public void cantarLlena() {
        controlModelo.verificarJugadaLlena();
    }

    /**
     * Solicita al modelo la verificación de la jugada de tipo Chorro.
     */
    public void cantarChorro() {
        controlModelo.verificarJugadaChorro();
    }

    /**
     * Solicita al modelo la verificación de la jugada de tipo Centro.
     */
    public void cantarCentro() {
        controlModelo.verificarJugadaCentro();
    }

    /**
     * Solicita al modelo la verificación de la jugada de tipo Cuatro Esquinas.
     */
    public void cantarCuatroEsquinas() {
        controlModelo.verificarJugadaCuatroEsquinas();
    }

}
