package modelo;

import enums.JugadasDisponibles;
import java.awt.Point;

import interfaces.IModeloJuego;

/**
 * Implementación de la interfaz IModeloControl.
 *
 * Recibe las acciones del control y las delega para actualizar el estado del
 * juego.
 *
 * @author rocha
 */
public class ModeloControlImp implements IModeloControl {

    private final IModeloJuego modeloJuego;

    /**
     * Constructor vacío
     * @param modeloJuego
     */
    public ModeloControlImp(IModeloJuego modeloJuego) {
        this.modeloJuego = modeloJuego;
    }

    /**
     * Coloca una ficha en la posición indicada.
     *
     * que se encarga de verificar y procesar la jugada dentro del modelo del
     * juego mediante el objeto IModeloJuego.
     *
     * @param posicion Objeto Point que representa la coordenada (x, y) donde se
     * desea colocar la ficha.
     */
    @Override
    public void colocarFicha(Point posicion) {
        modeloJuego.validaMovimiento(posicion);
    }

    /**
     * Verifica la jugada de tipo Llena enviando la validación al modelo del
     * juego.
     * <p>
     * Solicita al modelo que valide si se cumplen las condiciones necesarias
     * para cantar la jugada Llena.
     * </p>
     */
    @Override
    public void verificarJugadaLlena() {
        modeloJuego.validarJugada(JugadasDisponibles.LLENA);
    }

    /**
     * Verifica la jugada de tipo Chorro enviando la validación al modelo del
     * juego.
     * <p>
     * Solicita al modelo que valide si se cumplen las condiciones necesarias
     * para cantar la jugada Llena.
     * </p>
     */
    @Override
    public void verificarJugadaChorro() {
        modeloJuego.validarJugada(JugadasDisponibles.CHORRO);
    }

    /**
     * Verifica la jugada de tipo Centro enviando la validación al modelo del
     * juego.
     * <p>
     * Solicita al modelo que valide si se cumplen las condiciones necesarias
     * para cantar la jugada Llena.
     * </p>
     */
    @Override
    public void verificarJugadaCentro() {
        modeloJuego.validarJugada(JugadasDisponibles.CENTRO);
    }

    /**
     * Verifica la jugada de tipo Cuatro esquinas enviando la validación al
     * modelo del juego.
     * <p>
     * Solicita al modelo que valide si se cumplen las condiciones necesarias
     * para cantar la jugada Llena.
     * </p>
     */
    @Override
    public void verificarJugadaCuatroEsquinas() {
        modeloJuego.validarJugada(JugadasDisponibles.CUATROESQUINAS);
    }

    /**
     * Devuelve la interfaz del modelo del juego que utiliza para comunicarse.
     *
     * @return La interfaz del modelo de juego.
     */
    public IModeloJuego getModeloJuego() {
        return modeloJuego;
    }

    @Override
    public void abandonarPartida() {
        System.exit(0);
    }

}
