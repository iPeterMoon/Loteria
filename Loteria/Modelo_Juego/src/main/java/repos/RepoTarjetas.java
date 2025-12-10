package repos;

import java.awt.Point;
import java.util.List;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import modelo.Tarjeta;

public class RepoTarjetas {
    
    List<Tarjeta> tarjetas = new LinkedList<>();

    /**
     * Repositorio para guardar tarjetas de loteria pre hechas
     * DATOS MOCKEADOS.
     */
    private RepoTarjetas(){
        //Tarjeta 1
        int[] arregloCartas = {1,2,3,4,23,24,25,26,33,34,35,36,28,29,30,31};
        tarjetas.add(crearTarjeta(arregloCartas));
        
        // Tarjeta 1 (Mezcla variada)
        int[] c1 = {10, 21, 35, 48, 5, 52, 17, 3, 29, 44, 12, 1, 54, 23, 8, 30};
        tarjetas.add(crearTarjeta(c1));
        
        // Tarjeta 2
        int[] c2 = {2, 15, 28, 41, 53, 9, 22, 36, 49, 4, 18, 31, 45, 11, 24, 38};
        tarjetas.add(crearTarjeta(c2));
        
        // Tarjeta 3
        int[] c3 = {7, 20, 33, 46, 6, 19, 32, 47, 13, 26, 40, 51, 16, 27, 39, 50};
        tarjetas.add(crearTarjeta(c3));

        // Tarjeta 4
        int[] c4 = {14, 25, 37, 42, 34, 43, 16, 3, 52, 11, 24, 8, 54, 1, 19, 32};
        tarjetas.add(crearTarjeta(c4));

        // Tarjeta 5 (Incluye cartas altas)
        int[] c5 = {50, 51, 52, 53, 1, 2, 3, 4, 25, 26, 27, 28, 10, 20, 30, 40};
        tarjetas.add(crearTarjeta(c5));

        // Tarjeta 6
        int[] c6 = {5, 15, 35, 45, 6, 16, 36, 46, 7, 17, 37, 47, 8, 18, 38, 48};
        tarjetas.add(crearTarjeta(c6));
        
        // Tarjeta 7
        int[] c7 = {9, 19, 29, 39, 12, 22, 32, 42, 14, 24, 34, 44, 13, 23, 33, 43};
        tarjetas.add(crearTarjeta(c7));
        
        // Tarjeta 8 (Dispersa)
        int[] c8 = {54, 41, 27, 11, 31, 21, 1, 15, 45, 35, 51, 25, 5, 9, 33, 17};
        tarjetas.add(crearTarjeta(c8));
        
        // Tarjeta 9
        int[] c9 = {2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32};
        tarjetas.add(crearTarjeta(c9));
        
        // Tarjeta 10
        int[] c10 = {31, 33, 35, 37, 39, 41, 43, 45, 47, 49, 51, 53, 13, 23, 7, 17};
        tarjetas.add(crearTarjeta(c10));
    }

    private static RepoTarjetas instance;

    public static RepoTarjetas getInstance(){
        if(instance == null){
            instance = new RepoTarjetas();
        }
        return instance;
    }

    /**
     * Metodo auxiliar para crear una tarjeta a partir de un arreglo de cartas
     * @param arregloCartas Arreglo con los numeros de la carta que componen a la tarjeta
     * @return Tarjeta conteniendo el arreglo de cartas especificado
     */
    private Tarjeta crearTarjeta(int[] arregloCartas){
        Map<Point, Integer> cartas = new HashMap<>();
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                cartas.put(new Point(x, y), arregloCartas[(x * 4) + y]);
            }
        }
        return new Tarjeta(cartas);
    }

    /**
     * @return La lista de tarjetas guardadas en el repositorio
     */
    public List<Tarjeta> obtenerTarjetas(){
        return tarjetas;
    }
}
