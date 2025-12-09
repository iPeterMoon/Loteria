/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos.aplicacion;

/**
 *
 * @author Jp
 */
public class JugadaDTO {

    private String nicknameJugador;
    private String nombreJugada;
    private int puntos;

    public JugadaDTO(String nicknameJugador, String nombreJugada, int puntos) {
        this.nicknameJugador = nicknameJugador;
        this.nombreJugada = nombreJugada;
        this.puntos = puntos;
    }

    public String getNicknameJugador() {
        return nicknameJugador;
    }

    public void setNicknameJugador(String nicknameJugador) {
        this.nicknameJugador = nicknameJugador;
    }

    public String getNombreJugada() {
        return nombreJugada;
    }

    public void setNombreJugada(String nombreJugada) {
        this.nombreJugada = nombreJugada;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    @Override
    public String toString() {
        return "JugadaDTO{" + "nicknameJugador=" + nicknameJugador + ", nombreJugada=" + nombreJugada + ", puntos=" + puntos + '}';
    }

}
