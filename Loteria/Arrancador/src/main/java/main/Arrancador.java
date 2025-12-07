package main;

import controladores.ControlesConfiguracionFactory;
import interfaces.IModeloJuego;
import interfaces.IModeloVistaConfiguracion;
import interfaces.IModeloVistaJuego;
import interfaces.IObserver;
import modelo.IModeloControlAplicacion;
import modelo.IModeloControlNegocio;
import modelo.ModeloControlAplicacion;
import modelo.ModeloControlConfigImp;
import modelo.ModeloJuegoFacade;
import modelo.ModeloVistaConfiguracionFacade;
import modelo.ModeloVistaFacade;
import peer.PeerFacade;
import procesadores_modelo.ProcesadorEventos;
import ventanas.FrameConfiguracion;

/**
 * Clase que se encarga de configurar el modelo del juego y todo lo necesario
 * para arrancar el programa.
 *
 * @author Alici
 */
public class Arrancador {

    public static void main(String[] args) {

        FrameConfiguracion frameInicio = FrameConfiguracion.getInstancia();

        ControlesConfiguracionFactory controlesFactory = ControlesConfiguracionFactory.getInstance();

        IModeloControlAplicacion modeloAplicacion = new ModeloControlAplicacion();
        IModeloJuego modeloJuego = ModeloJuegoFacade.getInstance();

        IModeloControlNegocio modeloControl = new ModeloControlConfigImp(modeloJuego);

        controlesFactory.inicializar(modeloControl, modeloAplicacion);

//        controlesFactory.getControlAplicacion().iniciar();
//        //Obtener la fachada de la vista.
        IModeloVistaJuego modeloVistaJuego = ModeloVistaFacade.getInstance();
        IModeloVistaConfiguracion modeloVistaConfiguracion = ModeloVistaConfiguracionFacade.getInstance();
//
//        //Iniciar el componente de peer
        IObserver modeloJuegoObserver = new ProcesadorEventos();
        PeerFacade nuevoPeer = new PeerFacade();
        nuevoPeer.setObserver(modeloJuegoObserver);
//        
//        Cantador cantador = Cantador.getInstance();
//
//        // 1. Configuración de dependencias del Modelo
//        // 1.1 Crear ModeloJuego (necesita IModeloVista)
        ModeloJuegoFacade.getInstance().inicializar(modeloVistaJuego, modeloVistaConfiguracion, nuevoPeer);
//        IModeloJuego modeloJuego = ModeloJuegoFacade.getInstance();
//
//        // 1.2 Crear ModeloControl(necesita IModeloJuego)
//        IModeloControl modeloControl = new ModeloControlImp(modeloJuego);
//
//        //Inicializar el registro de controles
//        RegistroControles.getInstance().inicializar(modeloControl);
//
//        //Iniciar peer
//        nuevoPeer.start();
//
//        // Jugador Principal
//        String nickname = JOptionPane.showInputDialog("Ingresa tu nickname");
//        if (nickname != null) {
//            nuevoPeer.setUser(nickname);
//            Jugador jugadorPrincipal = new Jugador(nickname, "/imagenes_alt/icon_imagen.png", 0, null);
//            modeloJuego.setJugadorPrincipal(JugadorMapperModelo.toDTO(jugadorPrincipal, true));
//        }
//
//        cantador.setCartaActual(1);
//
//        //Jugadores secundarios
//        Jugador jugadorSecundario1 = new Jugador("Adel", "/imagenes_alt/icon_imagen.png", 0, null);
//        Jugador jugadorSecundario2 = new Jugador("Denise", "/imagenes_alt/icon_imagen.png", 0, null);
//        
//        modeloJuego.agregarJugadorSecundario(JugadorMapperModelo.toDTO(jugadorSecundario1, false));
//        modeloJuego.agregarJugadorSecundario(JugadorMapperModelo.toDTO(jugadorSecundario2, false));
//
//        // Mock de sala para prueba
//        Sala.getInstance().setHost(jugadorSecundario1);
//        
//        modeloJuego.iniciarPartida();
    }
}
