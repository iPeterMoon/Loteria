package factory;

import interfaces.IEnvio;
import interfaces.IRecepcion;
import red.EnvioHandler;
import red.RecepcionHandler;

/**
 * RedFactory.java
 * 
 * Fabrica para los componentes de red (Envio/Recepcion)
 * @author Peter
 */
public class RedFactory {
    
    /**
     * @return Devuelve la instancia de la aplicación del manejador de envío de mensajes
     */
    public static IEnvio crearEnvioHandler(){
        IEnvio envioHandler = EnvioHandler.getInstance();
        return envioHandler;
    }
    
    /**
     * @return Devuelve la instancia de la aplicación del manejador de recepción de mensajes
     */
    public static IRecepcion crearRecepcionHandler(){
        IRecepcion recepcion = RecepcionHandler.getInstance();
        return recepcion;
    }
    
    
}
