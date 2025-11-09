package eventos;

import enums.TipoEvento;
import java.io.Serializable;

/**
 * Clase base abstracta (Contrato) para todos los eventos.
 * Debe ser Serializable para ser enviado por la red.
 */
public abstract class Evento implements Serializable {
    private static final long serialVersionUID = 100L;

    private final TipoEvento tipoEvento;
    private final String userSender; // user del peer que envía

    public Evento(TipoEvento tipoEvento, String userSender) {
        this.tipoEvento = tipoEvento;
        this.userSender = userSender;
    }

    public TipoEvento getTipoEvento() {
        return tipoEvento;
    }

    public String getUserSender() {
        return userSender;
    }

    @Override
    public String toString() {
        return "Evento{" + "tipoEvento=" + tipoEvento + ", userSender=" + userSender + '}';
    }
    
}
