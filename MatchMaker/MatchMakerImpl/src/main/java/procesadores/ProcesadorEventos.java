package procesadores;

import interfaces.IObserver;

/**
 *
 * @author norma
 */
public class ProcesadorEventos implements IObserver {

    private IHandler manejadorPrincipal;

    public ProcesadorEventos() {
        IHandler solicitudSala = new ManejadorSolicitudSala();
        IHandler salaCreada = new ManejadorSalaCreada();
        IHandler unirseSala = new ManejadorUnirseSala();
        IHandler desconexion = new ManejadorDesconexion();

        manejadorPrincipal = solicitudSala;
        solicitudSala.setNext(salaCreada);
        salaCreada.setNext(unirseSala);
        unirseSala.setNext(desconexion);
    }

    @Override
    public void update(Object object) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
}
