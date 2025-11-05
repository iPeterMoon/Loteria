package dtos;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO (Contrato) que representa la información de un peer. Debe ser
 * Serializable para ser enviado dentro de un Evento si es necesario.
 */
public class PeerInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String user;
    private String ip;
    private int port; // Puerto en el que la 'Red' de este peer está escuchando

    public PeerInfo(String user, String ip, int port) {
        this.user = user;
        this.ip = ip;
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public void setUser(String user){
        this.user = user;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "PeerInfo{" + "user='" + user + '\'' + ", ip='" + ip + '\'' + ", port=" + port + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PeerInfo peerInfo = (PeerInfo) o;
        return Objects.equals(user, peerInfo.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }
}
