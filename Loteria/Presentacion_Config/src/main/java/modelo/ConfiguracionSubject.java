package modelo;

import enums.TipoConfiguracion;
import util.Subject;

/**
 *
 * @author Alici
 */
public class ConfiguracionSubject extends Subject {
    
    private TipoConfiguracion tipoConfiguracion;
    
    public ConfiguracionSubject() {
        setTipoConfiguracion(TipoConfiguracion.CREAR_SALA);
    }
    
    public TipoConfiguracion getTipoConfiguracion() {
        return tipoConfiguracion;
    }
    
    public void setTipoConfiguracion(TipoConfiguracion tipoConfiguracion) {
        this.tipoConfiguracion = tipoConfiguracion;
        notifyAllObservers();
    }
    
}
