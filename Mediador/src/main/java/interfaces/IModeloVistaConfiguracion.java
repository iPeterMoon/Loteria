package interfaces;

import dtos.JugadorSalaEsperaDTO;
import java.util.List;

/**
 *
 * @author norma
 */
public interface IModeloVistaConfiguracion {
    
    public void actualizarSala(List<JugadorSalaEsperaDTO> jugadores);
    
    public void obtenerDatosDeLaSala(List<JugadorSalaEsperaDTO> jugadores, String nivel, int limiteJugadores);
}
