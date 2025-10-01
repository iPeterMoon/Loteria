package com.arrancador;

import conexion.ConexionPublisher;
import conexion.ConexionReceiver;
import conexion.ServerTCP;

import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import modelo.JugadorSubject;
import modelo.ModeloVistaFacade;
import modeloJuego.Tarjeta;
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

        FrameJuego frame = FrameJuego.getInstance();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        ImageIcon imagenIcono = new ImageIcon(frame.getClass().getResource("/imagenes_alt/icon_imagen.png"));

        Image imagen = imagenIcono.getImage();

        Map<Point, Integer> cartas = new HashMap<>();
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                cartas.put(new Point(x, y), ((x * 4) + y + 1));
            }
        }
        ModeloTarjeta tarjeta = new ModeloTarjeta(cartas);
        JugadorSubject jugadorPrincipal = new JugadorSubject("Jerson", 0, imagen, tarjeta, true);
        JugadorSubject jugadorSecundario = new JugadorSubject("Peter", 0, imagen, tarjeta, false);
        JugadorSubject jugadorSecundario1 = new JugadorSubject("Juampi", 0, imagen, tarjeta, false);
        JugadorSubject jugadorSecundario2 = new JugadorSubject("Isabel", 0, imagen, tarjeta, false);

        frame.setJugadorPrincipal(jugadorPrincipal);
        frame.agregarJugadorSecundario(jugadorSecundario);
        frame.agregarJugadorSecundario(jugadorSecundario1);
        frame.agregarJugadorSecundario(jugadorSecundario2);

        IObserver observer = new ModelObserver();

        jugadorPrincipal.addObserver(observer);
        jugadorSecundario.addObserver(observer);
        jugadorSecundario1.addObserver(observer);
        jugadorSecundario2.addObserver(observer);

        ModeloVistaFacade modeloVista = ModeloVistaFacade.getInstance();
        modeloVista.agregarJugador(jugadorPrincipal);
        modeloVista.agregarJugador(jugadorSecundario);
        modeloVista.agregarJugador(jugadorSecundario1);
        modeloVista.agregarJugador(jugadorSecundario2);

        // prueba temporal para verificar validamovimiento
        // Crear un jugador compatible con ModeloJuegoImp
        Jugador jugadorPrueba = new Jugador();
        jugadorPrueba.setNickname("Jerson");
        jugadorPrueba.setPuntos(0);
        Tarjeta tarjetaa = new Tarjeta(cartas);
        jugadorPrueba.setTarjeta(tarjetaa);

        // Configurar Cantador con la carta que se "cantó"
        Cantador cantador = Cantador.getInstance();
        cantador.setCartaActual(1);

        // Crear el modelo de juego y asignar el jugador principal
        ModeloJuegoImp modelo = ModeloJuegoImp.getInstance();
        modelo.setJugadorPrincipal(jugadorPrueba);
    }
}
