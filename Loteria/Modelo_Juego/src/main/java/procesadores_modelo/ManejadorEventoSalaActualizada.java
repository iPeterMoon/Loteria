/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package procesadores_modelo;

import dtos.aplicacion.JugadorDTO;
import enums.TipoEvento;
import eventos.Evento;
import eventos.eventos_aplicacion.EventoSalaActualizada;
import java.util.ArrayList;
import java.util.List;
import mappers.JugadorMapperModelo;
import modelo.Jugador;
import modelo.ModeloJuegoFacade;
import modelo.Sala;

/**
 *
 * @author norma
 */
public class ManejadorEventoSalaActualizada extends ManejadorEventos {

    @Override
    public void procesar(Evento evento) {
        if (evento.getTipoEvento().equals(TipoEvento.SALA_ACTUALIZADA)) {
            manejarSalaActualizada((EventoSalaActualizada) evento);
        } else if (next != null) {
            next.procesar(evento);
        }
    }

    private void manejarSalaActualizada(EventoSalaActualizada evento) {
        Sala sala = Sala.getInstance();
        List<JugadorDTO> jugadoresDTO = evento.getJugadores();

        List<Jugador> jugadoresSecundarios = new ArrayList<>();
        String nicknamePrincipal = (sala.getJugadorPrincipal() != null) ? sala.getJugadorPrincipal().getNickname() : "";

        for (JugadorDTO jugadorDTO : jugadoresDTO) {
            if (!jugadorDTO.getNickname().equals(nicknamePrincipal)) {
                jugadoresSecundarios.add(JugadorMapperModelo.toJugador(jugadorDTO));
            }
        }

        sala.setJugadoresSecundario(jugadoresSecundarios);
        ModeloJuegoFacade.getInstance().actualizarJugadoresSala(jugadoresDTO);
    }
}
