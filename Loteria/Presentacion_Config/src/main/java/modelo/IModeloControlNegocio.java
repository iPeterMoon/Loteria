package modelo;

import dtos.aplicacion.ConfiguracionDTO;
import dtos.aplicacion.NuevoUsuarioDTO;

/**
 *
 * @author norma
 */
public interface IModeloControlNegocio {

    public void verificarExistenciaPartida();

    public void obtenerAvatars();

    public void unirseSala(NuevoUsuarioDTO usuario);

    public void configurarUsuarioNuevaSala(NuevoUsuarioDTO usuarioNuevo);

    public void configurarPartida(ConfiguracionDTO configuracion);
}
