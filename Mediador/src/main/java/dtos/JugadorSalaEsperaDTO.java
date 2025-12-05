/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.awt.Image;

/**
 *
 * @author norma
 */
public class JugadorSalaEsperaDTO {

    private String nickname;
    
    private int fotoPerfil;
    
    private boolean jugadorPrincipal;

    public JugadorSalaEsperaDTO() {
    }

    public JugadorSalaEsperaDTO(String nickname, int fotoPerfil, boolean jugadorPrincipal) {
        this.nickname = nickname;
        this.fotoPerfil = fotoPerfil;
        this.jugadorPrincipal = jugadorPrincipal;
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
    
    
}
