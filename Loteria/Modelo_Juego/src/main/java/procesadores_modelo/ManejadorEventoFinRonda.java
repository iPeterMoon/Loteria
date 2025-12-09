/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package procesadores_modelo;

import enums.TipoEvento;
import eventos.Evento;
import eventos.eventos_aplicacion.EventoFinRonda;
import modelo.ModeloJuegoFacade;

/**
 *
 * @author Jp
 */
public class ManejadorEventoFinRonda extends ManejadorEventos {

    @Override
    public void procesar(Evento evento) {
        if (evento.getTipoEvento().equals(TipoEvento.FIN_RONDA)) {
            EventoFinRonda eventoFin = (EventoFinRonda) evento;
            ModeloJuegoFacade.getInstance().finalizarRonda(eventoFin.getMotivo());
        } else if (next != null) {
            next.procesar(evento);
        }
    }

}
