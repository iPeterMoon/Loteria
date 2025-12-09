/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package procesadores_modelo;

import eventos.Evento;
import util.IObserver;

/**
 *
 * @author Alici
 */
public class ProcesadorEventos implements IObserver {

    private final IHandler manejadorPrincipal;

    public ProcesadorEventos() {
        ManejadorEventos eventoFicha = new ManejadorEventoColocarFicha();
        ManejadorEventos eventoSemilla = new ManejadorEventoSemilla();
        ManejadorEventos eventoTarjetasBarajeadas = new ManejadorEventoTarjetasBarajeadas();
        ManejadorEventos eventoInicioPartida = new ManejadorEventoInicioPartida();
        ManejadorEventos eventoCartaCantada = new ManejadorEventoCartaCantada();
        ManejadorEventos eventoSalaActualizada = new ManejadorEventoSalaActualizada();
        ManejadorEventos eventoInfoSala = new ManejadorEventoInfoSala();
        ManejadorEventos eventoJugadas = new ManejadorEventoJugada();
        ManejadorEventos eventoDesconexion = new ManejadorEventoDesconexion();
        ManejadorEventos eventoNuevoHost = new ManejadorEventoNuevoHost();

        this.manejadorPrincipal = eventoFicha;

        manejadorPrincipal.setNext(eventoSemilla);
        eventoSemilla.setNext(eventoTarjetasBarajeadas);
        eventoTarjetasBarajeadas.setNext(eventoInicioPartida);
        eventoInicioPartida.setNext(eventoCartaCantada);
        eventoCartaCantada.setNext(eventoSalaActualizada);
        eventoSalaActualizada.setNext(eventoInfoSala);
        eventoInfoSala.setNext(eventoJugadas);
        eventoJugadas.setNext(eventoDesconexion);
        eventoDesconexion.setNext(eventoNuevoHost);

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
