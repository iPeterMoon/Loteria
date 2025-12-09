/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package managers;

import eventos.eventos_aplicacion.EventoJugada;
import interfaces.peer.IPeer;
import modelo.ModeloJuegoFacade;
import modelo.Sala;
import modelo.Tarjeta;
import enums.JugadasDisponibles;

/**
 *
 * @author Jp
 */
public class CantarJugadaManager {

    private IPeer componentePeer;

    public void inicializar(IPeer peer) {
        this.componentePeer = peer;
    }

    public void cantarJugada(JugadasDisponibles jugada) {
        boolean esValida = validarJugadaLocal(jugada);

        if (esValida) {
            EventoJugada evento = new EventoJugada(Sala.getInstance().getJugadorPrincipal().getNickname(), jugada.toString());
            componentePeer.broadcastEvento(evento);
            ModeloJuegoFacade.getInstance().cantarJugada(evento);
            System.out.println(jugada.toString() + "es correcta");
        } else {
            System.out.println("INTENTASTE CANTAR " + jugada.toString() + "PERO TE FALTAN FICHAS");
        }
    }

    private boolean validarJugadaLocal(JugadasDisponibles jugada) {
        if (jugada == null) {
            return false;
        }

        Sala sala = Sala.getInstance();

        if (sala.getJugadorPrincipal() == null || sala.getJugadorPrincipal().getTarjeta() == null) {
            return false; // No hay jugador principal o tarjeta asignada
        }

        Tarjeta tarjeta = sala.getJugadorPrincipal().getTarjeta();

        switch (jugada) {
            case LLENA -> {
                return validarLlena(tarjeta);
            }
            case CENTRO -> {
                return validarCentro(tarjeta);
            }
            case CHORRO -> {
                return validarChorro(tarjeta);
            }
            case CUATROESQUINAS -> {
                return validarCuatroEsquinas(tarjeta);
            }
            default -> {
                // SI LLEGA ACA NO EXISTE LA JUGADA
                return false;
            }
        }
    }

    private boolean validarLlena(Tarjeta tarjeta) {
        return tarjeta.estaLlena();
    }

    private boolean validarChorro(Tarjeta tarjeta) {
        return tarjeta.estaChorro();
    }

    private boolean validarCentro(Tarjeta tarjeta) {
        return tarjeta.estaCentro();

    }

    private boolean validarCuatroEsquinas(Tarjeta tarjeta) {
        return tarjeta.estaCuatroEsquinas();
    }
}
