package modelo;

import dtos.aplicacion.ConfiguracionJuegoDTO;
import dtos.aplicacion.NuevoUsuarioDTO;
import interfaces.IModeloJuego;

/**
 *
 * @author norma
 */
public class ModeloControlConfigImp implements IModeloControlNegocio {
    
    private IModeloJuego modeloJuego;
    
    public ModeloControlConfigImp(IModeloJuego modeloJuego) {
        this.modeloJuego = modeloJuego;
    }
    
    @Override
    public void verificarExistenciaPartida() {
        
    }
    
    @Override
    public void unirseSala(NuevoUsuarioDTO usuarioNuevo) {
        usuarioNuevo.setIdAvatarSeleccionado(ModeloVistaConfiguracionFacade.getInstance().getAvatarSubject().getNumeroAvatar());
        modeloJuego.unirseSala(usuarioNuevo);
    }
    
    @Override
    public void configurarUsuarioNuevaSala(NuevoUsuarioDTO usuarioNuevo) {
        usuarioNuevo.setIdAvatarSeleccionado(ModeloVistaConfiguracionFacade.getInstance().getAvatarSubject().getNumeroAvatar());
        modeloJuego.configurarUsuarioNuevaSala(usuarioNuevo);
    }
    
    @Override
    public void configurarPartida(ConfiguracionJuegoDTO configuracion) {
    }
    
    @Override
    public void abandonarSala(){
        modeloJuego.abandonarSala();
    }
    
}
