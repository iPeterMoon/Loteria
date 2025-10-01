package com.arrancador;

import conexion.ConexionPublisher;
import conexion.ConexionReceiver;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import mappers.JugadorMapper;
import modelo.IModeloVista;
import modelo.ModeloVistaFacade;
import modeloJuego.Tarjeta;
import modeloJuego.Jugador;

/**
 * Clase que se encarga de configurar el modelo del juego y todo lo necesario
 * para arrancar el programa.
 *
 * @author Alici
 */
public class Arrancador {

    public static void main(String[] args) {

        //Cosas de la conexión
        ConexionReceiver receiver = new ConexionReceiver();
        Thread receiverThread = new Thread(receiver);
        receiverThread.setName("ReceiverThread");
        receiverThread.start();

        ConexionPublisher publisher = new ConexionPublisher();
        publisher.anunciarConexion();

        IModeloVista modeloVista = ModeloVistaFacade.getInstance();

        // RESPONSABILIDAD QUE DESPUES DEBE PASAR AL MODELO DEL JUEGO
        //Se genera un mapa de las cartas de la tarjeta
        Map<Point, Integer> cartas = new HashMap<>();
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                cartas.put(new Point(x, y), ((x * 4) + y + 1));
            }
        }
        //Se crea la tarjeta para asignarsela a los jugadores
        Tarjeta tarjeta = new Tarjeta(cartas);

        // Jugador Principal
        String nickname = JOptionPane.showInputDialog("Ingresa tu nickname");
        Jugador jugadorPrincipal = new Jugador(nickname, "/imagenes_alt/icon_imagen.png", 0, tarjeta);

        //Jugadores secundarios
        Jugador jugadorSecundario1 = new Jugador("Adel", "/imagenes_alt/icon_imagen.png", 0, tarjeta);
        Jugador jugadorSecundario2 = new Jugador("Peter", "/imagenes_alt/icon_imagen.png", 0, tarjeta);

        modeloVista.iniciarFrameJuego();

        modeloVista.agregarJugadorPrincipal(JugadorMapper.toDTO(jugadorPrincipal));
        modeloVista.agregarJugadorSecundario(JugadorMapper.toDTO(jugadorSecundario1));
        modeloVista.agregarJugadorSecundario(JugadorMapper.toDTO(jugadorSecundario2));

    }
}
