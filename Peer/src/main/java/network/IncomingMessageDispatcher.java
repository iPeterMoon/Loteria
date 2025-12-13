/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package network;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Clase encargada de gestionar la cola de mensajes entrantes recibidos desde la
 * red en el componente de Peer.
 *
 * @author norma
 */
public class IncomingMessageDispatcher {

    /**
     * Cola bloqueante que almacena los mensajes entrantes.
     */
    private static BlockingQueue<String> incomingQueue = new LinkedBlockingQueue<>();

    /**
     * Inserta un mensaje recibido en la cola de entrada.
     *
     * @param mensaje Mensaje recibido desde la red.
     */
    public static void dispatch(String mensaje) {
        if (mensaje == null || incomingQueue == null) {
            System.err.println("Error: Mensaje o cola de entrada nulos en el despachador.");
            return;
        }
        try {
            incomingQueue.put(mensaje); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Error al despachar mensaje de entrada: " + e.getMessage());
        }
    }

    /**
     * Obtiene y elimina el siguiente mensaje disponible en la cola de entrada.
     *
     * @return Mensaje recibido desde la red.
     * @throws InterruptedException Si el hilo es interrumpido mientras espera.
     */
    public static String take() throws InterruptedException{
        return incomingQueue.take();
    }
}