package procesadores;

import eventos.Evento;
import util.IObserver;

/**
 *
 * @author norma
 */
public class ProcesadorEventosMatchmaker implements IObserver {

    private IHandler manejadorPrincipal;

    public ProcesadorEventosMatchmaker() {
        IHandler solicitudSala = new ManejadorSolicitudSala();
        IHandler salaCreada = new ManejadorSalaCreada();
        IHandler unirseSala = new ManejadorUnirseSala();
        IHandler nuevaSala = new ManejadorSalaCreada();
        IHandler salidaSala = new ManejadorSalirSalaEspera();
        IHandler desconexion = new ManejadorDesconexion();
        IHandler finJuego = new ManejadorFinJuego();

        manejadorPrincipal = solicitudSala;
        solicitudSala.setNext(salaCreada);
        salaCreada.setNext(unirseSala);
        unirseSala.setNext(nuevaSala);
        nuevaSala.setNext(salidaSala);
        salidaSala.setNext(desconexion);
        desconexion.setNext(finJuego);
    }

    private void procesar(Evento evento) {
        try {
            manejadorPrincipal.procesar(evento);
        } catch (Exception e) {
            System.err.println("[ProcesadorEventosMatchmaker] Evento inválido: " + e.getMessage());
        }
    }

    @Override
    public void update(Object object) {
        if (object instanceof Evento evento) {
            procesar(evento);
        }
    }
}
