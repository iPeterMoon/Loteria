package excepciones;

/**
 * Excepcion para cuando una sala excede el limite de jugadores
 * @author Peter
 */
public class ExceedLimitException extends Exception{
    
    public ExceedLimitException(){
        super();
    }

    public ExceedLimitException(String message) {
        super(message);
    }
}
