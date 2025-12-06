package modelo;

import dtos.NuevoUsuarioDTO;
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
    public void obtenerAvatars() {
//        Map<Integer, Image> opciones = modeloJuego.obtenerAvatars();
//        vista.mostrarAvatares(opciones);
    }

    @Override
    public void unirseSala(NuevoUsuarioDTO usuario) {
        modeloJuego.unirseSala(usuario);
    }

}
