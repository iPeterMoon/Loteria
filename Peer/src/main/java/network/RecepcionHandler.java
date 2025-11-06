/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package network;

import factory.RedFactory;
import interfaces.IRecepcion;
import interfaces.IRedListener;
import java.util.concurrent.BlockingQueue;

/**
 *
 * @author Jp
 */
public class RecepcionHandler implements Runnable, IRedListener {

    private final IRecepcion recepcion;
    private final BlockingQueue<String> incomingQueue;
    private volatile boolean running = false;

    public RecepcionHandler(BlockingQueue<String> incomingQueue) {
        this.recepcion = RedFactory.crearRecepcionHandler();
        this.incomingQueue = incomingQueue;
    }

    public synchronized int start() {
        if (running) {
            return -1;
        }

        recepcion.setEventListener(this);
        try {
            int puerto = recepcion.empezarEscucha();
            running = true;
            new Thread(this, "RecepcionHandler-Thread").start();
            System.out.println("[RecepcionHandler] Escuchando en puerto: " + puerto);
            return puerto;
        } catch (Exception e) {
            System.err.println("[RecepcionHandler] Error al iniciar: " + e.getMessage());
            return -1;
        }
    }

    @Override
    public void onMensajeRecibido(String mensaje) {
        if (mensaje != null && !mensaje.isBlank()) {
            boolean encolado = incomingQueue.offer(mensaje);
            if (!encolado) {
                System.err.println("[RecepcionHandler] Cola llena. Mensaje descartado.");
            }
        }
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public synchronized void stop() {
        if (!running) {
            return;
        }
        running = false;
        recepcion.stop();
        System.out.println("[RecepcionHandler] Detenido.");
    }
}
