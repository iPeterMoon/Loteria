/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package procesadores_modelo;

import enums.TipoEvento;
import eventos.Evento;
import eventos.eventos_aplicacion.EventoCartaCantada;
import modelo.Cantador;
import modelo.ModeloJuegoFacade;

/**
 *
 * @author rocha
 */
public class ManejadorEventoCartaCantada extends ManejadorEventos {

    @Override
    public void procesar(Evento evento) {
        if (evento.getTipoEvento() == TipoEvento.CARTA_CANTADA) {
            manejarCartaCantada((EventoCartaCantada) evento);
        } else if (next != null) {
            next.procesar(evento);
        }
    }
    
    private void manejarCartaCantada(EventoCartaCantada evento) {
        Cantador cantador = Cantador.getInstance();
        int carta = evento.getCartaActual();
        
        // Quitar carta del mazo local
        cantador.quitarCarta(carta);
        
        // Actualizar carta actual
        cantador.setCartaActual(carta);
        
        // Pasarlo a la fachada
        ModeloJuegoFacade.getInstance().actualizarCarta(carta);
    }
}
