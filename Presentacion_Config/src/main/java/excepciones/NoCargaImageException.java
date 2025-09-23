/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 *
 * @author lagar
 */
public class NoCargaImageException extends Exception {

    public NoCargaImageException() {
    }

    public NoCargaImageException(String message) {
        super(message);
    }

    public NoCargaImageException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoCargaImageException(Throwable cause) {
        super(cause);
    }

    public NoCargaImageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
