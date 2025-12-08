package control;

import modelo.IModeloControl;

/**
 * Se encarga de mantener y proveer todas las instancias de control.
 */
public class RegistroControles {

    private static RegistroControles instancia;

    private ControlSeleccionarCarta controlSeleccionarJugada;
    private ControlJugadas controlJugadas;
    private ControlAbandonarPartida controlAbandonarPartida;

    /**
     * Constructor privado para evitar la instanciación externa.
     */
    private RegistroControles() {
    }

    public static RegistroControles getInstance() {
        if (instancia == null) {
            instancia = new RegistroControles();
        }
        return instancia;
    }

    public void inicializar(IModeloControl modeloControl) {
        if (this.controlSeleccionarJugada != null) {
            //Evitar doble inicialización
            return;
        }
        this.controlSeleccionarJugada = new ControlSeleccionarCarta(modeloControl);

        if (this.controlJugadas != null) {
            //Evitar doble inicialización
            return;
        }

        this.controlAbandonarPartida = new ControlAbandonarPartida(modeloControl);
        if (this.controlAbandonarPartida != null) {
            //Evitar doble inicialización
            return;
        }
        this.controlAbandonarPartida = new ControlAbandonarPartida(modeloControl);
    }

    /**
     * Metodo que regresa el control para la selección de una carta
     *
     * @return el Control para seleccionar una carta
     */
    public ControlSeleccionarCarta getControlSeleccionarJugada() {
        if (controlSeleccionarJugada == null) {
            throw new IllegalStateException("El Registro de Controles no ha sido inicializado. Llame a inicializar() en Arrancador");
        }
        return controlSeleccionarJugada;
    }

    public ControlJugadas getControlJugadas() {
        if (controlJugadas == null) {
            throw new IllegalStateException("El Registro de Controles no ha sido inicializado. Llame a inicializar() en Arrancador");
        }
        return controlJugadas;
    }

    public ControlAbandonarPartida getControlAbandonarPartida() {
        if (controlAbandonarPartida == null) {
            throw new IllegalStateException("El Registro de Controles no ha sido inicializado. Llame a inicializar() en Arrancador");
        }
        return controlAbandonarPartida;
    }

}
