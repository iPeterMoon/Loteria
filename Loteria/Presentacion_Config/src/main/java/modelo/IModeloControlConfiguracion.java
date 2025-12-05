package modelo;

import dtos.NuevoUsuarioDTO;

/**
 *
 * @author norma
 */
public interface IModeloControlConfiguracion {

    public void verificarExistenciaPartida();
    
    public void obtenerAvatars();
    
    public void unirseSala(NuevoUsuarioDTO usuario);
}
