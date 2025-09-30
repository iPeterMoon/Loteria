package modelo;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author rocha
 */
public class Tarjeta {
    private Map<Point, Integer> cartas;
    private Map<Point, Boolean> fichas;

    public Tarjeta(Map<Point, Integer> cartas) {
        this.cartas = cartas;
        fichas = new HashMap<>();
        iniciarMapaFichas();
    }

    private void iniciarMapaFichas(){
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                fichas.put(new Point(x, y), false);
            }
        }
    }

    public void agregarFicha(Point posicion) {
        fichas.put(posicion, true);
    }

    public void reiniciarTarjeta() {
        iniciarMapaFichas();
    }

    public Map<Point, Boolean> getFichas() {
        return fichas;
    }
}
