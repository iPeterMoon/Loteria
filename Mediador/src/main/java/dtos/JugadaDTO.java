/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

/**
 *
 * @author Jp
 */
public class JugadaDTO {

    private String nicknameJugador;
    private String nombreJugada;

    public JugadaDTO(String nicknameJugador, String nombreJugada) {
        this.nicknameJugador = nicknameJugador;
        this.nombreJugada = nombreJugada;
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

    @Override
    public String toString() {
        return "JugadaDTO{" + "nicknameJugador=" + nicknameJugador + ", nombreJugada=" + nombreJugada + '}';
    }

}
