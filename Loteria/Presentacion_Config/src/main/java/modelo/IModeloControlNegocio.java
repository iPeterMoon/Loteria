package modelo;

import dtos.NuevoUsuarioDTO;

/**
 *
 * @author norma
 */
public interface IModeloControlNegocio {

    public void verificarExistenciaPartida();
    
    public void obtenerAvatars();
    
    public void unirseSala(NuevoUsuarioDTO usuario);
}
