/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos.aplicacion;

/**
 *
 * @author norma
 */
public class JugadorSalaEsperaDTO {

    private String nickname;

    private int fotoPerfil;

    private boolean jugadorPrincipal;

    private int puntos;

    public JugadorSalaEsperaDTO() {
    }

    public JugadorSalaEsperaDTO(String nickname, int fotoPerfil, boolean jugadorPrincipal) {
        this.nickname = nickname;
        this.fotoPerfil = fotoPerfil;
        this.jugadorPrincipal = jugadorPrincipal;
        this.puntos = 0;
    }

    public JugadorSalaEsperaDTO(String nickname, int fotoPerfil, boolean jugadorPrincipal, int puntos) {
        this.nickname = nickname;
        this.fotoPerfil = fotoPerfil;
        this.jugadorPrincipal = jugadorPrincipal;
        this.puntos = puntos;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(int fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public boolean isJugadorPrincipal() {
        return jugadorPrincipal;
    }

    public void setJugadorPrincipal(boolean jugadorPrincipal) {
        this.jugadorPrincipal = jugadorPrincipal;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

}
