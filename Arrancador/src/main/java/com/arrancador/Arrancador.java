package com.arrancador;

import conexion.ConexionPublisher;
import conexion.ConexionReceiver;
import conexion.ServerTCP;
import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import mappers.JugadorMapper;
import modelo.IModeloVista;
import modelo.ModeloVistaFacade;
import modeloJuego.Tarjeta;
import modeloJuego.Jugador;
import modeloJuego.Cantador;
import modeloJuego.Jugador;
import modelo.ModeloTarjeta;
import modeloJuego.ModeloJuegoImp;
import vista.FrameJuego;
import vista.IObserver;
import vista.ModelObserver;

/**
 * Clase que se encarga de configurar el modelo del juego y todo lo necesario
 * para arrancar el programa.
 *
 * @author Alici
 */
public class Arrancador {

    public static void main(String[] args) {

        ConexionPublisher publisher = new ConexionPublisher();
        publisher.anunciarConexion();

        try {
            // Iniciar servidor TCP (Obtener Puerto Unico)
            ServerTCP server = new ServerTCP();
            int puertoTCP = server.getPuertoEscucha();

            // Iniciar el Servidor TCP en un hilo para que no bloque el Main
            Thread serverThread = new Thread(server);
            serverThread.setName("Hilo-ServidorTCP");
            serverThread.start();

            // Iniciar Receptor de Conexión (Listener MultiCast)
            // Necesita el puerto local para no intentar conectarse a si mismo
            ConexionReceiver receiver = new ConexionReceiver(puertoTCP);
            Thread receiverThread = new Thread(receiver);
            receiverThread.setName("Hilo-ConexionReceiver");
            receiverThread.start();

            // Anunciar la conexión inicial
            // Una llamada estática (la propagación la hará el receiver)
            ConexionPublisher.anunciarConexion(puertoTCP);

            System.out.println("\n--- JUEGO INICIADO ---");
            System.out.println("Tu puerto de escucha (único): " + puertoTCP);
            System.out.println("Esperando anuncios de otros Peers...");

        } catch (IOException e) {
            System.err.println("Error fatal al inicializar la aplicación de juego: " + e.getMessage());
            e.printStackTrace();
        }

        // RESPONSABILIDAD QUE DESPUES DEBE PASAR AL MODELO DEL JUEGO
        //Se genera un mapa de las cartas de la tarjeta
        Map<Point, Integer> cartas = new HashMap<>();
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                cartas.put(new Point(x, y), ((x * 4) + y + 1));
            }
        }
        //Se crea la tarjeta para asignarsela a los jugadores
        MopdeloTarjeta tarjeta = new ModeloTarjeta(cartas);
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
