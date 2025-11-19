package main;

import control.RegistroControles;
import interfaces.IModeloJuego;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import mappers.JugadorMapperModelo;
import modelo.IModeloControl;
import interfaces.IModeloVista;
import interfaces.IObserver;
import modelo.Cantador;
import modelo.ModeloControlImp;
import modelo.ModeloVistaFacade;
import modelo.Tarjeta;
import modelo.Jugador;
import modelo.ModeloJuegoFacade;
import peer.PeerFacade;
import procesadores.ProcesadorEventos;

/**
 * Clase que se encarga de configurar el modelo del juego y todo lo necesario
 * para arrancar el programa.
 *
 * @author Alici
 */
public class Arrancador {

    public static void main(String[] args) {

        //Obtener la fachada de la vista.
        IModeloVista modeloVista = ModeloVistaFacade.getInstance();

        //Iniciar el componente de peer
        IObserver modeloJuegoObserver = new ProcesadorEventos();
        PeerFacade nuevoPeer = new PeerFacade();
        nuevoPeer.setObserver(modeloJuegoObserver);

        // 1. Configuración de dependencias del Modelo
        // 1.1 Crear ModeloJuego (necesita IModeloVista)
        ModeloJuegoFacade.getInstance().inicializar(modeloVista, nuevoPeer);
        IModeloJuego modeloJuego = ModeloJuegoFacade.getInstance();

        // 1.2 Crear ModeloControl(necesita IModeloJuego)
        IModeloControl modeloControl = new ModeloControlImp(modeloJuego);

        //Inicializar el registro de controles
        RegistroControles.getInstance().inicializar(modeloControl);

        //Iniciar peer
        nuevoPeer.start();

        // RESPONSABILIDAD QUE DESPUES DEBE PASAR AL MODELO DEL JUEGO AL INICIAR LA PARTIDA
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
        if (nickname != null) {
            nuevoPeer.setUser(nickname);
            Jugador jugadorPrincipal = new Jugador(nickname, "/imagenes_alt/icon_imagen.png", 0, tarjeta);
            modeloJuego.setJugadorPrincipal(JugadorMapperModelo.toDTO(jugadorPrincipal, true));
            modeloVista.agregarJugadorPrincipal(JugadorMapperModelo.toDTO(jugadorPrincipal, true));
        }

        Cantador cantador = Cantador.getInstance();
        cantador.setCartaActual(1);

        //Jugadores secundarios
        Jugador jugadorSecundario1 = new Jugador("Adel", "/imagenes_alt/icon_imagen.png", 0, tarjeta);
        Jugador jugadorSecundario2 = new Jugador("Denise", "/imagenes_alt/icon_imagen.png", 0, tarjeta);

        modeloVista.iniciarFrameJuego();

        modeloVista.agregarJugadorSecundario(JugadorMapperModelo.toDTO(jugadorSecundario1, false));
        modeloVista.agregarJugadorSecundario(JugadorMapperModelo.toDTO(jugadorSecundario2, false));

    }
}
