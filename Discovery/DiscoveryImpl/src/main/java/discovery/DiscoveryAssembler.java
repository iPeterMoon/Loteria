package discovery;

import network.Envio;
import network.Recepcion;

/**
 *
 * @author pedro
 */
public class DiscoveryAssembler {

    private static DiscoveryAssembler instance;
    private final Envio envioHandler;
    private final Recepcion recepcionHandler;

    /**
     * Constructor del discovery, inicializa el orden de los manejadores de
     * mensajes.
     */
    private DiscoveryAssembler() {
        this.envioHandler = Envio.getInstance();
        this.recepcionHandler = Recepcion.getInstance();
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
//        try {
        recepcionHandler.empezarEscucha();
        envioHandler.empezarEnvio();
//            // Crear la instancia de PeerCleaner
//            PeerCleaner tareaLimpieza = new PeerCleaner(
//                    this.envio, // Le pasamos el 'envio' que ya tiene Discovery
//                    HEARTBEAT_TIMEOUT_MS);
//
//            // Programar la TAREA (el objeto)
//            scheduler.scheduleAtFixedRate(
//                    tareaLimpieza, // Se pasa el objeto PeerCleaner
//                    0,
//                    LIMPIEZA_INTERVALO_MS,
//                    TimeUnit.MILLISECONDS);
//
//            // 3. Programar la TAREA (el objeto) en lugar del método
//            scheduler.scheduleAtFixedRate(
//                tareaLimpieza, 
//                0,
//                LIMPIEZA_INTERVALO_MS,
//                TimeUnit.MILLISECONDS
//            );
//        } catch (IOException ex) {
//            Logger.getLogger(DiscoveryAssembler.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

}
