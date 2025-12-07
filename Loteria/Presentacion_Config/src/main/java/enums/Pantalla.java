/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package enums;

/**
 *
 * @author Alici
 */
public enum Pantalla {
    MENU("menu"),
    CONFIGURAR_USUARIO("configurar_usuario"),
    CONFIGURAR_PARTIDA("configurar_partida"),
    SALA_ESPERA("sala_espera");

    private final String nombre;

    Pantalla(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

}
