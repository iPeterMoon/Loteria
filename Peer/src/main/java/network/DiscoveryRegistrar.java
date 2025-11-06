/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package network;

import com.google.gson.Gson;
import dtos.PeerInfo;
import eventos.EventoNuevoPeer;
import factory.RedFactory;
import util.ConfigLoader;

/**
 *
 * @author Jp
 */
public class DiscoveryRegistrar {

    private final PeerInfo myInfo;
    private final String discoveryIp;
    private final int discoveryPort;

    public DiscoveryRegistrar(PeerInfo myInfo) {
        this.myInfo = myInfo;
        this.discoveryIp = ConfigLoader.getInstance().getIpServidor();
        this.discoveryPort = ConfigLoader.getInstance().getPuertoDiscovery();
    }

    public void registrar() {
        EventoNuevoPeer evento = new EventoNuevoPeer(myInfo, myInfo.getUser());
        RedFactory.crearEnvioHandler()
                .sendEvent(discoveryIp, discoveryPort, new Gson().toJson(evento));
    }

    public void actualizarUsuario(String user) {
        myInfo.setUser(user);
        registrar();
    }
}
