package modelo;

import java.util.List;

import dtos.FichaDTO;

/**
 *
 * @author rocha
 */
public class ModeloVistaFacade implements IModeloVista {
    
    private List<JugadorSubject> jugadores;
    
    @Override
    public void colocarFicha(FichaDTO fichaDTO) {
        for (JugadorSubject jugador : jugadores) {
            if (jugador.getNickname().equals(fichaDTO.getNicknameJugador())) {
                jugador.colocarFicha(fichaDTO.getPosicion());
                break;
            }
        }    
    }
}
