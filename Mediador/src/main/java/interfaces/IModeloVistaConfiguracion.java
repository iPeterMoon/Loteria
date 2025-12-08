package interfaces;

import dtos.aplicacion.JugadorSalaEsperaDTO;
import dtos.aplicacion.MensajeDTO;
import enums.TipoConfiguracion;
import enums.TipoNivel;
import java.util.List;

/**
 *
 * @author norma
 */
public interface IModeloVistaConfiguracion {

    public void actualizarJugadoresSala(List<JugadorSalaEsperaDTO> jugadores);

    public void actualizarDatosSala(String host, int limiteJugadores, TipoNivel nivel);

    public void actualizarMensaje(MensajeDTO mensaje);

    public void actualizarTipoConfiguracion(TipoConfiguracion tipoConfiguracion);

}
