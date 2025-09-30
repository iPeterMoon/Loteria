package dtos;

import java.awt.Point;

public class FichaDTO {

    private String nicknameJugador;
    private Point posicion;

    public FichaDTO(String nicknameJugador, Point posicion) {
        this.nicknameJugador = nicknameJugador;
        this.posicion = posicion;
    }

    public String getNicknameJugador() {
        return nicknameJugador;
    }

    public Point getPosicion() {
        return posicion;
    }
}

