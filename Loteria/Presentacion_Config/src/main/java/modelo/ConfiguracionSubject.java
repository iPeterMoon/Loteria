package modelo;

import enums.TipoConfiguracion;
import util.Subject;

/**
 *
 * @author Alici
 */
public class ConfiguracionSubject extends Subject {

    private static ConfiguracionSubject instancia;
    private TipoConfiguracion tipoConfiguracion;

    private ConfiguracionSubject() {
    }

    public static ConfiguracionSubject getInstancia() {
        if (instancia == null) {
            instancia = new ConfiguracionSubject();
        }
        return instancia;
    }
}
