/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eventos.eventos_aplicacion;

import enums.TipoEvento;
import eventos.Evento;

/**
 *
 * @author Jp
 */
public class EventoFinJuego extends Evento {
    private String ganador;

    public EventoFinJuego(String userSender, String ganador) {
        super(TipoEvento.FIN_JUEGO, userSender);
        this.ganador = ganador;
    }

    public String getGanador() {
        return ganador;
    }

    public void setGanador(String ganador) {
        this.ganador = ganador;
    }
    
    
}
