package managers;

import dtos.aplicacion.MensajeDTO;
import interfaces.peer.IPeer;
import mappers.JugadorMapperModelo;
import mappers.TarjetaMapper;
import modelo.Cantador;
import modelo.Jugador;
import modelo.Sala;
import modelo.Tarjeta;

import repos.RepoTarjetas;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

import dtos.aplicacion.TarjetaDTO;
import enums.TipoMensajePantalla;
import eventos.eventos_aplicacion.EventoIniciarPartida;
import eventos.eventos_aplicacion.EventoSemilla;
import eventos.eventos_aplicacion.EventoTarjetasBarajeadas;
import interfaces.aplicacion.IModeloVistaJuego;
import modelo.ModeloJuegoFacade;

/**
 * Manejador para el inicio de una nueva partida.
 * 
 * @author Peter
 */
public class InicioPartidaManager {

    private IPeer componentePeer;
    private IModeloVistaJuego modeloVista;

    /**
     * Inicializa el Manager para iniciar la partida
     * @param peer Peer del jugador que está iniciando la partida
     * @param modeloVista Interfaz para la presentación del juego.
     */
    public void inicializar(IPeer peer, IModeloVistaJuego modeloVista) {
        if (this.componentePeer != null || this.modeloVista != null) {
            return;
        }
        this.componentePeer = peer;
        this.modeloVista = modeloVista;
    }

    /**
     * Metodo orquestador para iniciar la partida, hace todo lo necesario
     * para barajear el mazo y mostrar la pantalla de la partida.
     */
    public void iniciarPartida() {
        Sala sala = Sala.getInstance();
        if(!sala.getJugadoresSecundario().isEmpty()){
            sala.resetearJugadasDisponibles();
            Cantador.getInstance().detenerCanto();
            
            barajearMazo();
            repartirTarjetas();
            generarEventoInicioPartida();
            mostrarFramePartida();
            if(!sala.isJuegoEnCurso()){
                sala.setJuegoEnCurso(true);
            }
            sala.setPartidaEnCurso(true);
        } else {
            MensajeDTO mensaje = new MensajeDTO("No se puede iniciar", "<html>No puede iniciar la partida si no hay más jugadores</html>", false, TipoMensajePantalla.VALIDACION_SALA_ESPERA);
            ModeloJuegoFacade.getInstance().mostrarMensaje(mensaje);
        }
    }

    /**
     * Metodo que barajea el mazo generando una semilla y llamando al metodo de preparar
     * mazo del Cantador
     */
    private synchronized void barajearMazo() {
        Random random = new Random();
        Long semilla = random.nextLong();
        Cantador.getInstance().prepararMazo(semilla);
        generarEventoSemilla(semilla);
    }
    
    /**
     * Genera un evento con la semilla para pasarselo a los demás jugadores y que
     * ellos barajeen su mazo con la misma semilla
     * @param semilla Semilla con la que se barajeará el mazo
     */
    private void generarEventoSemilla(Long semilla){
        String userSender = Sala.getInstance().getJugadorPrincipal().getNickname();
        EventoSemilla eventoSeed = new EventoSemilla(userSender, semilla);
        componentePeer.broadcastEvento(eventoSeed);
    }

    /**
     * Metodo auxiliar para repartir las tarjetas a todos los jugadores.
     */
    private synchronized void repartirTarjetas(){
        Stack<Tarjeta> tarjetas = barajearTarjetas();
        Sala sala = Sala.getInstance();
        Map<String, TarjetaDTO> jugadoresTarjetas = new HashMap<>();
        Tarjeta tarjeta;
        List<Jugador> jugadores = new LinkedList<>();
        jugadores.add(sala.getJugadorPrincipal());
        jugadores.addAll(sala.getJugadoresSecundario());
        for(Jugador jugador : jugadores){
            tarjeta = tarjetas.pop();
            tarjeta.reiniciarFichas();
            jugador.setTarjeta(tarjeta);
            jugadoresTarjetas.put(jugador.getNickname(), TarjetaMapper.toDTO(tarjeta));
        }
        generarEventoTarjetasBarajeadas(jugadoresTarjetas);
    }
    
    /**
     * Barajea las tarjetas del repositorio de tarjetas
     * @return
     */
    private Stack<Tarjeta> barajearTarjetas(){
        RepoTarjetas repoTarjetas = RepoTarjetas.getInstance();
        List<Tarjeta> listaTarjetas = repoTarjetas.obtenerTarjetas();
        Collections.shuffle(listaTarjetas);
        Stack<Tarjeta> tarjetas = new Stack<>();
        tarjetas.addAll(listaTarjetas);
        return tarjetas;
    }

    /**
     * Metodo para enviar a los demás jugadores las tarjetas barajeadas.
     * @param jugadoresTarjetas Mapa con los nicknames de los jugadores y sus tarjetas.
     */
    private void generarEventoTarjetasBarajeadas(Map<String, TarjetaDTO> jugadoresTarjetas){
        String userSender = Sala.getInstance().getJugadorPrincipal().getNickname();
        EventoTarjetasBarajeadas evento = new EventoTarjetasBarajeadas(userSender, jugadoresTarjetas);
        componentePeer.broadcastEvento(evento);
    }

    /**
     * Metodo para iniciar el frame de la partida. 
     */
    public void mostrarFramePartida(){
        establecerJugadoresEnVista();
        modeloVista.iniciarFrameJuego();
    }

    /**
     * Establece los jugadores en el modelo de la vista.
     */
    public void establecerJugadoresEnVista(){
        Sala sala = Sala.getInstance();
        Jugador jugadorPrincipal = sala.getJugadorPrincipal();
        modeloVista.agregarJugadorPrincipal(JugadorMapperModelo.toDTO(jugadorPrincipal, true));
        for(Jugador jugador : sala.getJugadoresSecundario()){
            modeloVista.agregarJugadorSecundario(JugadorMapperModelo.toDTO(jugador, false));
        }
    }

    private void generarEventoInicioPartida(){
        String userSender = Sala.getInstance().getJugadorPrincipal().getNickname();
        EventoIniciarPartida evento = new EventoIniciarPartida(userSender);
        componentePeer.broadcastEvento(evento);
    }
    
}
