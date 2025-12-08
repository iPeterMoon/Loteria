package modelo;

import java.awt.Point;
import java.util.List;

import dtos.FichaDTO;
import dtos.JugadorDTO;
import eventos.Evento;
import eventos.eventos_aplicacion.EventoFicha;
import interfaces.IModeloJuego;
import interfaces.IModeloVista;
import interfaces.IPeer;
import managers.MovimientoManager;
import mappers.JugadorMapperModelo;
import managers.CantadorManager;

/**
 * Clase que implementa los métodos de la interfaz IModeloJuego
 *
 * @author Alici
 */
public class ModeloJuegoFacade implements IModeloJuego {

    private static ModeloJuegoFacade instancia;
    private IModeloVista vista;
    private final MovimientoManager movimientoManager = new MovimientoManager();
    private final CantadorManager cantadorManager = new CantadorManager();    

    private ModeloJuegoFacade() {
    }

    public static ModeloJuegoFacade getInstance() {
        if (instancia == null) {
            instancia = new ModeloJuegoFacade();
        }
        return instancia;
    }

    public void inicializar(IModeloVista modeloVista, IPeer peer) {
        if (this.vista != null) {
            //Asegura que no se inicialice dos veces
            return;
        }
        this.vista = modeloVista;

        movimientoManager.inicializar(peer);
    }

    /**
     * Método para obtener el jugador principal, es decir el que es dueño de la
     * vista
     *
     * @return Jugador dueño de la vista
     */
    @Override
    public JugadorDTO getJugadorPrincipal() {
        return JugadorMapperModelo.toDTO(Sala.getInstance().getJugadorPrincipal(), true);
    }

    /**
     * Método para asignar el jugador principal, es decir el que es dueño de la
     * vista
     *
     * @param jugadorPrincipal Jugador que es dueño de la vista
     */
    @Override
    public void setJugadorPrincipal(JugadorDTO jugadorPrincipal) {
        Sala.getInstance().setJugadorPrincipal(JugadorMapperModelo.toJugador(jugadorPrincipal));
    }

    /**
     * Valida un movimiento de colocación de ficha en la tarjeta del jugador
     * principal.
     *
     * 1. Obtiene la carta en la posición seleccionada. 2. Compara con la carta
     * actual cantada por el {@link Cantador}. 3. Si coinciden: - Coloca una
     * ficha en la tarjeta del jugador. - Crea un {@link FichaDTO} con la
     * posición y el nickname del jugador. - Notifica a la vista para que
     * actualice la interfaz.
     *
     * @param posicion Posición en la tarjeta donde el jugador intenta colocar
     * la ficha.
     */
    @Override
    public void validaMovimiento(Point posicion) {
        movimientoManager.validaMovimiento(posicion);
    }

    /**
     * Metodo que llama a la vista para colocar la ficha en la tarjeta de un
     * jugador
     *
     * @param ficha DTO con la posicion de la ficha y el jugador a quien va a
     * colocarse la ficha.
     */
    @Override
    public void colocarFicha(EventoFicha ficha) {
        FichaDTO fichaDTO = new FichaDTO(ficha.getUserSender(), ficha.getPosicion());
        vista.colocarFicha(fichaDTO);
    }

    //  LÓGICA DE ABANDONAR PARTIDA 

    public void procesarDesconexion(Evento evento) {
        String idDesconectado = evento.getUserSender(); 
        
        // 1. Acceder a la Sala
        Sala sala = Sala.getInstance();
        
        // 2. Eliminar al jugador desconectado de la lista general
        // (Asumimos que 'getJugadores()' trae la lista donde están todos para saber el orden)
        List<Jugador> listaJugadores = sala.getJugadores();
        
        if (listaJugadores != null) {
            // Borramos al que se fue buscando por Nickname o ID
            listaJugadores.removeIf(j -> j.getNickname().equals(idDesconectado));
        }

        // 3. Obtener MI identidad (Jugador Principal)
        Jugador yoPrincipal = sala.getJugadorPrincipal();

        // 4. VERIFICACIÓN DE HOST (Diagrama 5)
        // Regla: El nuevo Host es SIEMPRE el índice 0 de la lista actualizada.
        if (listaJugadores != null && !listaJugadores.isEmpty() && yoPrincipal != null) {
            
            // Obtenemos al primero de la fila
            Jugador nuevoLider = listaJugadores.get(0);

            // Comparamos: ¿El primero de la lista soy YO?
            if (nuevoLider.getNickname().equals(yoPrincipal.getNickname())) {
                
                // Evitamos reiniciar si ya estamos cantando
                if (!cantadorManager.isCantando()) {
                    System.out.println(">>> [MIGRACION] El Host anterior se fue.");
                    System.out.println(">>> [MIGRACION] Soy el Jugador Principal y estoy en el índice 0.");
                    System.out.println(">>> [MIGRACION] ASUMIENDO ROL DE HOST.");
                    
                    // Iniciamos el canto (3 segundos de espera para dar tiempo a los demás)
                    cantadorManager.iniciarCanto(3000);
                }
            }
        }
    }

}
