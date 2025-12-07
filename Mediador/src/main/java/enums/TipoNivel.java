/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package enums;

/**
 *
 * @author Alici
 */
public enum TipoNivel {
    BASICO(5000),
    INTERMEDIO(3000),
    DIFICIL(1000);

    private final int intervalo;

    TipoNivel(int intervalo) {
        this.intervalo = intervalo;
    }

    public int getIntervalo() {
        return intervalo;
    }

}
