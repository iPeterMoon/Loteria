package modelo;

import dtos.aplicacion.ConfiguracionJuegoDTO;
import dtos.aplicacion.NuevoUsuarioDTO;

/**
 *
 * @author norma
 */
public interface IModeloControlNegocio {

    public void verificarExistenciaPartida();

    public void unirseSala(NuevoUsuarioDTO usuario);

    public void configurarUsuarioNuevaSala(NuevoUsuarioDTO usuarioNuevo);

    public void configurarPartida(ConfiguracionJuegoDTO configuracion);
    
    public void abandonarSala();
}
