package eventos.eventos_aplicacion;

import enums.TipoEvento;
import java.awt.Point;

/**
 *
 * @author pedro
 */
public class EventoFicha extends Evento {

    private final Point posicion;

    public EventoFicha(String userSender, Point posicion) {
        super(TipoEvento.FICHA, userSender);
        this.posicion = posicion;
    }

    public Point getPosicion() {
        return posicion;
    }

    @Override
    public String toString() {
        return super.toString().replace("}", "")
                + ", posicion=" + posicion
                + '}';
    }

}
