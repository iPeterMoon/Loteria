package modelo;

import dtos.aplicacion.NuevoUsuarioDTO;

/**
 *
 * @author norma
 */
public interface IModeloControlNegocio {

    public void verificarExistenciaPartida();
    
    public void obtenerAvatars();
    
    public void unirseSala(NuevoUsuarioDTO usuario);
}
