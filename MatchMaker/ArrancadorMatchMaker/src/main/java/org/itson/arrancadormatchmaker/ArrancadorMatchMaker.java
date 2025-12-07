package org.itson.arrancadormatchmaker;


import implementacion.MatchMaker;
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

        MatchMaker.getInstance().setPeer(peer);

        peer.setUser("MATCHMAKER");
        peer.start();
    }
}
