/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package procesadores;

import enums.TipoEvento;
import eventos.Evento;
import eventos.eventos_aplicacion.EventoFicha;
import modelo.ModeloJuegoFacade;

/**
 *
 * @author Alici
 */
public class ManejadorEventoColocarFicha extends ManejadorEventos {

    @Override
    public void procesar(Evento evento) {
        if (evento.getTipoEvento().equals(TipoEvento.FICHA)) {
            ModeloJuegoFacade.getInstance().colocarFicha((EventoFicha) evento);
        } else if (next != null) {
            next.procesar(evento);
        }
    }

}
