/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package network;

import java.util.concurrent.BlockingQueue;

/**
 *
 * @author norma
 */
public class OutgoingMessageDispatcher {

    private OutgoingMessageDispatcher() {
    }

    public static void dispatch(String mensaje, BlockingQueue<String> outgoingQueue) {
        if (mensaje == null || outgoingQueue == null) {
            System.err.println("Error: Mensaje o cola de salida nulos en el despachador.");
            return;
        }
        try {
            outgoingQueue.put(mensaje);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Error al despachar mensaje de salida: " + e.getMessage());
        }
    }
}