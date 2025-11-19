package discovery;

import network.Envio;
import network.Recepcion;
import peerManager.PeerCleaner;

/**
 *
 * @author pedro
 */
public class DiscoveryAssembler {

    private static DiscoveryAssembler instance;
    private final Envio envioHandler;
    private final Recepcion recepcionHandler;
    private final PeerCleaner cleaner;

    /**
     * Constructor del discovery, inicializa el orden de los manejadores de
     * mensajes.
     */
    private DiscoveryAssembler() {
        this.envioHandler = Envio.getInstance();
        this.recepcionHandler = Recepcion.getInstance();
        this.cleaner = PeerCleaner.getInstance();
    }

    /**
     * Obtiene la unica instancia de esta clase
     *
     * @return Instancia de esta clase
     */
    public static DiscoveryAssembler getInstance() {
        if (instance == null) {
            instance = new DiscoveryAssembler();
        }
        return instance;
    }

    /**
     * Inicializa el servidor de discovery, los componentes de red y también un
     * hilo aparte para eliminar a los peers muertos cada cierto tiempo.
     */
    public void start() {
        recepcionHandler.empezarEscucha();
        envioHandler.empezarEnvio();
        Thread cleanerThread = new Thread(cleaner);
        cleanerThread.start();
    }

}
