/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.awt.Image;

/**
 *
 * @author rocha
 */
public class JugadorSubject extends Subject {
    private String nickname;
    private int puntaje;
    private Image foto;
    private TarjetaSubject tarjeta;
    private boolean jugadorPrincipal;

    public JugadorSubject() {
    }

    public JugadorSubject(String nickname, int puntaje, Image foto, TarjetaSubject tarjeta, boolean jugadorPrincipal) {
        this.nickname = nickname;
        this.puntaje = puntaje;
        this.foto = foto;
        this.tarjeta = tarjeta;
        this.jugadorPrincipal = jugadorPrincipal;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public Image getFoto() {
        return foto;
    }

    public void setFoto(Image foto) {
        this.foto = foto;
    }

    public TarjetaSubject getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(TarjetaSubject tarjeta) {
        this.tarjeta = tarjeta;
    }

    public boolean isJugadorPrincipal() {
        return jugadorPrincipal;
    }

    public void setJugadorPrincipal(boolean jugadorPrincipal) {
        this.jugadorPrincipal = jugadorPrincipal;
    }

    @Override
    public String toString() {
        return "JugadorSubject{" + "nickname=" + nickname + ", puntaje=" + puntaje + ", foto=" + foto + ", tarjeta=" + tarjeta + ", jugadorPrincipal=" + jugadorPrincipal + '}';
    }
    
    
}
