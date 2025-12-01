package modelo;

import java.util.Stack;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import util.Subject;

/**
 * Clase singleton que representa el cantador de cartas del juego.
 *
 * @author Alici
 */
public class Cantador extends Subject {

    public final static int TOTAL_CARTAS_LOTERIA = 54;

    /**
     * Carta que esta siendo actualmente cantada
     */
    private int cartaActual;
    
    /**
     * Mazo (representado como una pila) de cartas que tiene el cantador
     */
    private Stack<Integer> mazo;
    
    private boolean enEjecucion;

    /**
     * Instancia unica de la clase Cantador
     */
    private static Cantador instance;

    /**
     * Constructor vacio
     */
    private Cantador() {
        this.enEjecucion = false;
    }

    /**
     * Metodo para obtener la instancia unica del cantador
     *
     * @return la instancia de {@link Cantador}
     */
    public static Cantador getInstance() {
        if (instance == null) {
            instance = new Cantador();
        }
        return instance;
    }

    /**
     * Prepara el mazo para una nueva partida 
     * @param semilla Semilla con la que se barajeará el mazo
     */
    public void prepararMazo(Long semilla){
        reiniciarMazo();
        List<Integer> listaMazo = new ArrayList<>(mazo);
        Collections.shuffle(listaMazo, new Random(semilla));
        mazo.clear();
        mazo.addAll(listaMazo);
    }

    /**
     * Metodo para reiniciar el mazo de cartas, vuelve a crear un stack y se lo pasa 
     * al cantador
     */
    private void reiniciarMazo() {
        Stack<Integer> mazo = new Stack<>();
        for(int i = 1; i <= Cantador.TOTAL_CARTAS_LOTERIA; i++){
            mazo.push(i);
        }
        this.setMazo(mazo);
    }
    
    public void iniciarCanto(long intervalo) {
        if (enEjecucion) {
            return;
        }
        
        enEjecucion = true;
        
        new Thread(() -> {
            try {
                while (!mazo.isEmpty()) {
                    // Quita carta del mazo y actualiza carta actual
                    cartaActual = mazo.pop();
                    
                    // Notifica
                    notifyAllObservers();
                    Thread.sleep(intervalo);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                enEjecucion = false;
            }
        }).start();
    }
    
    public void quitarCarta(int carta) {
        this.mazo.remove(carta);
    }

    /**
     * Método para obtener la carta actual que esta siendo cantada
     *
     * @return Entero que representa el número de la carta actual
     */
    public int getCartaActual() {
        return cartaActual;
    }

    /**
     * Método para asignar la carta que esta siendo cantada actualmente.
     *
     * @param cartaActual Entero que representa el número de la carta actual
     */
    public void setCartaActual(int cartaActual) {
        this.cartaActual = cartaActual;
    }

    /**
     * Método para obtener el mazo de cartas que quedan
     *
     * @return Pila de cartas que tiene el cantador
     */
    public Stack<Integer> getMazo() {
        return mazo;
    }

    /**
     * Método para asignar el mazo de cartas al cantador
     *
     * @param mazo Pila de cartas a asignar al cantador
     */
    public void setMazo(Stack<Integer> mazo) {
        this.mazo = mazo;
    }


}
