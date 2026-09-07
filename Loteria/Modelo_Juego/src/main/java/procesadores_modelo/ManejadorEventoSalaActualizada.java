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
import java.util.Map;
import mappers.JugadorMapperModelo;
import modelo.Jugador;
import modelo.ModeloJuegoFacade;
import modelo.Sala;
import modelo.Tarjeta;

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
        
        // Preservar los puntos y la tarjeta ya repartida de jugadores existentes
        // (incluyendo el principal). El roster que manda el Matchmaker no conoce
        // las tarjetas repartidas peer-to-peer, así que sin esto se perdería la
        // tarjeta del jugador (y ya no podría colocar fichas) cada vez que este
        // evento llega en plena partida (p. ej. al reasignarse el host).
        Map<String, Integer> puntosAcumulados = new java.util.HashMap<>();
        Map<String, Tarjeta> tarjetasAcumuladas = new java.util.HashMap<>();

        if (sala.getJugadorPrincipal() != null) {
            puntosAcumulados.put(sala.getJugadorPrincipal().getNickname(), sala.getJugadorPrincipal().getPuntos());
            if (sala.getJugadorPrincipal().getTarjeta() != null) {
                tarjetasAcumuladas.put(sala.getJugadorPrincipal().getNickname(), sala.getJugadorPrincipal().getTarjeta());
            }
        }

        List<Jugador> jugadoresActuales = sala.getJugadoresSecundario();
        if (jugadoresActuales != null) {
            for (Jugador j : jugadoresActuales) {
                puntosAcumulados.put(j.getNickname(), j.getPuntos());
                if (j.getTarjeta() != null) {
                    tarjetasAcumuladas.put(j.getNickname(), j.getTarjeta());
                }
            }
        }

        // Actualizar el jugador principal con sus puntos y tarjeta preservados
        for (JugadorDTO jugadorDTO : jugadoresDTO) {
            if (jugadorDTO.getNickname().equals(nicknamePrincipal)) {
                // Restaurar puntos acumulados del principal
                if (puntosAcumulados.containsKey(nicknamePrincipal)) {
                    jugadorDTO.setPuntos(puntosAcumulados.get(nicknamePrincipal));
                }
                Jugador jugadorPrincipal = JugadorMapperModelo.toJugador(jugadorDTO);
                if (jugadorPrincipal.getTarjeta() == null && tarjetasAcumuladas.containsKey(nicknamePrincipal)) {
                    jugadorPrincipal.setTarjeta(tarjetasAcumuladas.get(nicknamePrincipal));
                }
                sala.setJugadorPrincipal(jugadorPrincipal);
                break;
            }
        }

        // Actualizar jugadores secundarios con sus puntos y tarjeta preservados
        for (JugadorDTO jugadorDTO : jugadoresDTO) {
            if (!jugadorDTO.getNickname().equals(nicknamePrincipal)) {
                // Restaurar puntos acumulados si el jugador ya existía
                if (puntosAcumulados.containsKey(jugadorDTO.getNickname())) {
                    jugadorDTO.setPuntos(puntosAcumulados.get(jugadorDTO.getNickname()));
                }
                Jugador jugador = JugadorMapperModelo.toJugador(jugadorDTO);
                if (jugador.getTarjeta() == null && tarjetasAcumuladas.containsKey(jugadorDTO.getNickname())) {
                    jugador.setTarjeta(tarjetasAcumuladas.get(jugadorDTO.getNickname()));
                }
                jugadoresSecundarios.add(jugador);
            }
        }

        sala.setJugadoresSecundario(jugadoresSecundarios);
        
        // Actualizar vista con todos los jugadores incluyendo el principal
        List<JugadorDTO> todosLosJugadores = new ArrayList<>();
        if (sala.getJugadorPrincipal() != null) {
            todosLosJugadores.add(JugadorMapperModelo.toDTO(sala.getJugadorPrincipal(), true));
        }
        for (Jugador j : jugadoresSecundarios) {
            todosLosJugadores.add(JugadorMapperModelo.toDTO(j, false));
        }
        
        ModeloJuegoFacade.getInstance().actualizarJugadoresSala(todosLosJugadores);
    }
}
