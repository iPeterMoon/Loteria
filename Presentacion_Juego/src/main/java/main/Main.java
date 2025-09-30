package main;

import javax.swing.ImageIcon;

import java.awt.Image;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import modelo.JugadorSubject;
import modelo.ModeloVistaFacade;
import modelo.Tarjeta;
import vista.FrameJuego;
import vista.IObserver;
import vista.ModelObserver;

public class Main {
    public static void main(String[] args) {
        FrameJuego frame = FrameJuego.getInstance();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        
        ImageIcon imagenIcono = new ImageIcon(frame.getClass().getResource("/imagenes_alt/icon_imagen.png"));

        Image imagen = imagenIcono.getImage();

        Map<Point, Integer> cartas = new HashMap<>();
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                cartas.put(new Point(x, y), ((x*4)+y+1));
            }
        }
        
        Tarjeta tarjeta = new Tarjeta(cartas);
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

    }
}
