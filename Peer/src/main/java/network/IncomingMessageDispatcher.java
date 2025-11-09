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
public class IncomingMessageDispatcher {

    private IncomingMessageDispatcher() {
    } 

    public static void dispatch(String mensaje, BlockingQueue<String> incomingQueue) {
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
}