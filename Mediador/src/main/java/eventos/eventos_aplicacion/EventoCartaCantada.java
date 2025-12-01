/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eventos.eventos_aplicacion;

import enums.TipoEvento;
import eventos.Evento;

/**
 *
 * @author rocha
 */
public class EventoCartaCantada extends Evento {
    private int cartaActual;
    
    public EventoCartaCantada(String userSender, int cartaActual) {
        super(TipoEvento.CARTA_CANTADA, userSender);
        this.cartaActual = cartaActual;
    }

    public int getCartaActual() {
        return cartaActual;
    }

    public void setCartaActual(int cartaActual) {
        this.cartaActual = cartaActual;
    }

    @Override
    public String toString() {
        return "EventoCartaCantada{" + "cartaActual=" + cartaActual + '}';
    }

}
