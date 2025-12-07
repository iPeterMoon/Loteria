package org.itson.arrancadormatchmaker;


import implementaciones.Matchmaker;
import interfaces.IObserver;
import interfaces.IPeer;
import peer.PeerFacade;
import procesadores.ProcesadorEventos;

/**
 *
 * @author Jp
 */
public class ArrancadorMatchMaker {

    public static void main(String[] args) {
        IObserver matchmakerObserver = new ProcesadorEventos();
        IPeer peer = new PeerFacade();
        peer.setObserver(matchmakerObserver);

        Matchmaker.getInstance().setPeer(peer);

        peer.setUser("MATCHMAKER");
        peer.start();
    }
}
