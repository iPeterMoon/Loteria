/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author rocha
 */
public class TarjetaSubject extends Subject {
    private int[][] cartas;
    private boolean[][] fichas;

    public TarjetaSubject(int[][] cartas) {
        this.cartas = cartas;
        this.fichas = new boolean[4][4];
    }
    
    public void agregarFicha(int numeroCarta) {
        // Buscar la posición de la carta y cambiarlo a true
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (cartas[i][j] == numeroCarta) {
                    fichas[i][j] = true;
                }
            }
        }
        
        // Notificar cambios
        this.notifyAllObservers();
    }
}
