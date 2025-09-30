package modelo;

import java.util.LinkedList;
import java.util.List;

import dtos.FichaDTO;

/**
 *
 * @author rocha
 */
public class ModeloVistaFacade implements IModeloVista {
    
    private List<JugadorSubject> jugadores;
    private static ModeloVistaFacade modeloVista;

    private ModeloVistaFacade() {
        this.jugadores = new LinkedList<>();
    }

    public static ModeloVistaFacade getInstance(){
        if(modeloVista == null){
            modeloVista = new ModeloVistaFacade();
        }
        return modeloVista;
    }

    @Override
    public void colocarFicha(FichaDTO fichaDTO) {
        for (JugadorSubject jugador : jugadores) {
            if (jugador.getNickname().equals(fichaDTO.getNicknameJugador())) {
                jugador.colocarFicha(fichaDTO.getPosicion());
                break;
            }
        }    
    }

    public void agregarJugador(JugadorSubject jugador){
        jugadores.add(jugador);
    }
}
