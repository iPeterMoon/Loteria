/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package procesadores_modelo;

import eventos.Evento;
import interfaces.IObserver;

/**
 *
 * @author Alici
 */
public class ProcesadorEventos implements IObserver {

    private IHandler manejadorPrincipal;

    public ProcesadorEventos() {
        ManejadorEventos eventoFicha = new ManejadorEventoColocarFicha();
        ManejadorEventoSemilla eventoSemilla = new ManejadorEventoSemilla();
        ManejadorEventoTarjetasBarajeadas eventoTarjetasBarajeadas = new ManejadorEventoTarjetasBarajeadas();
        ManejadorEventoCartaCantada eventoCartaCantada = new ManejadorEventoCartaCantada();
        ManejadorJugadas eventoJugadas = new ManejadorJugadas();

        this.manejadorPrincipal = eventoFicha;

        manejadorPrincipal.setNext(eventoSemilla);
        eventoSemilla.setNext(eventoTarjetasBarajeadas);
        eventoTarjetasBarajeadas.setNext(eventoCartaCantada);
        eventoCartaCantada.setNext(eventoJugadas);
    }

    private void procesar(Evento evento) {
        try {
            manejadorPrincipal.procesar(evento);
        } catch (Exception e) {
            System.err.println("[ProcesadorEventos] Evento inválido: " + e.getMessage());
        }
    }

    @Override
    public void update(Object object) {
        if (object instanceof Evento evento) {
            procesar(evento);
        }
    }

}
