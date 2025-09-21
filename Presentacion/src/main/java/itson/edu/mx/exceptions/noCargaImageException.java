/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.edu.mx.exceptions;

/**
 *
 * @author lagar
 */
public class noCargaImageException extends Exception{

    public noCargaImageException() {
    }

    public noCargaImageException(String message) {
        super(message);
    }

    public noCargaImageException(String message, Throwable cause) {
        super(message, cause);
    }

    public noCargaImageException(Throwable cause) {
        super(cause);
    }

    public noCargaImageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
