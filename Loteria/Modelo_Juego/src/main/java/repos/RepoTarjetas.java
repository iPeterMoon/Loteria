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
        
        int[] arregloCartas2 = {49,19,9,54,25,20,16,45,2,36,23,35,28,38,41,14};
        tarjetas.add(crearTarjeta(arregloCartas2));
        
        int[] arregloCartas3 = {14,48,8,15,39,34,49,45,2,36,29,23,41,11,3,30};
        tarjetas.add(crearTarjeta(arregloCartas3));

        int[] arregloCartas4 = {42,20,16,33,35,43,8,17,25,13,34,22,46,26,2,10};
        tarjetas.add(crearTarjeta(arregloCartas4));

        int[] arregloCartas5 = {29,30,15,6,5,2,39,19,13,12,4,50,25,23,26,44};
        tarjetas.add(crearTarjeta(arregloCartas5));

        int[] arregloCartas6 = {48,38,42,34,43,10,46,8,5,30,7,53,33,24,15,25};
        tarjetas.add(crearTarjeta(arregloCartas6));
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
