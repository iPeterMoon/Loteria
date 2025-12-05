package modelo;

import dtos.JugadorSalaEsperaDTO;
import interfaces.IModeloVistaConfiguracion;
import interfaces.IObserver;
import java.util.List;
import vista.ModelObserver;

/**
 *
 * @author norma
 */
public class ModeloVistaFacade implements IModeloVistaConfiguracion{

    private static ModeloVistaFacade instancia;

    public static ModeloVistaFacade getInstance() {
        if (instancia == null) {
            instancia = new ModeloVistaFacade();
        }
        return instancia;
    }

    private SalaSubject salaSubject;

    private List<JugadorSalaEsperaDTO> jugadores;
    
    private IObserver observer = new ModelObserver();

    /**
     * Constructor privado que inicializa la lista de jugadores.
     *
     * Se restringe la creación de instancias externas para mantener el control
     * del Singleton.
     */
    private ModeloVistaFacade() {
        this.salaSubject = new SalaSubject(); 
        this.salaSubject.addObserver(this.observer);
    }
    
    @Override
    public void actualizarSala(List<JugadorSalaEsperaDTO> jugadores){
        this.salaSubject.setJugadores(jugadores);
        
    }

    @Override
    public void obtenerDatosDeLaSala(List<JugadorSalaEsperaDTO> jugadores, String nivel) {
        this.salaSubject.setJugadores(jugadores);
        this.salaSubject.setNivel(nivel);
    }

    

}
