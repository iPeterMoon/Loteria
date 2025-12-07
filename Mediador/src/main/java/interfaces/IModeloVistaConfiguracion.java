package interfaces;

import dtos.aplicacion.JugadorSalaEsperaDTO;
import enums.TipoNivel;
import java.util.List;

/**
 *
 * @author norma
 */
public interface IModeloVistaConfiguracion {
    
    public void actualizarJugadoresSala(List<JugadorSalaEsperaDTO> jugadores);
    
    public void actualizarDatosSala(int limiteJugadores, TipoNivel nivel);
    
}
