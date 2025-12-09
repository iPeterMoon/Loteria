package org.itson.arrancadormatchmaker;

import implementaciones.Matchmaker;
import util.IObserver;
import interfaces.peer.IPeer;
import peer.PeerFacade;
import procesadores.ProcesadorEventosMatchmaker;
import util.ConfigLoader;

/**
 *
 * @author Jp
 */
public class ArrancadorMatchMaker {

    public static void main(String[] args) {
        IObserver matchmakerObserver = new ProcesadorEventosMatchmaker();
        IPeer peer = new PeerFacade();
        peer.setObserver(matchmakerObserver);

        Matchmaker.getInstance().setPeer(peer);

        peer.start();
        peer.setUser(ConfigLoader.getInstance().getUsuarioMatchmaker());

    }
}
