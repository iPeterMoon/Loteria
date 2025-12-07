package peerManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;

import dtos.peer.PeerInfo;
import eventos.eventos_peers.EventoDesconexion;
import network.OutgoingMessageDispatcher;

public class PeerCleaner implements Runnable {

    private final long HEARTBEAT = 500;
    private final long HEARTBEAT_TIMEOUT_MS = HEARTBEAT * 3 ;
    private final long LIMPIEZA_INTERVALO_MS = 2000;
    private final ScheduledExecutorService scheduler;
    
    // Mapa estático para que los manejadores de entrada puedan actualizar tiempos
    private static final Map<String, Long> ultimaVezVistos = new ConcurrentHashMap<>();
    private static PeerCleaner instance;

    private PeerCleaner() {
        this.scheduler = Executors.newScheduledThreadPool(1);
    }

    /**
     * Metodo getInstance para implementar patron singleton
     * @return Instancia unica de la clase.
     */
    public static PeerCleaner getInstance(){
        if(instance == null){
            instance = new PeerCleaner();
        }
        return instance;
    }

    /**
     *  Método estático para actualizar la ultima vez que se vio un peer.
     */ 
    public static void actualizarUltimaVezVisto(PeerInfo peer) {
        String peerKey = ListaPeers.obtenerKey(peer);
        ultimaVezVistos.put(peerKey, System.currentTimeMillis());
    }

    @Override
    public void run() {
        System.out.println("[PeerCleaner] Iniciando ciclo de limpieza...");
        // Inicia su propio scheduler
        scheduler.scheduleAtFixedRate(this::ejecutarLimpieza, 0, LIMPIEZA_INTERVALO_MS, TimeUnit.MILLISECONDS);
    }

    /**
     * Metodo que ejecuta la limpieza de los peers checando 
     * cuanto tiempo ha pasado desde la ultima vez que se recibió
     * un heartbeat, si ya pasó el suficiente tiempo, se eliminan los registros de el
     * y se crea un evento de desconexión para enviar a los otros peers conectados.
     */
    private void ejecutarLimpieza() {
        try {
            long ahoraEnMilis = System.currentTimeMillis();

            for (Map.Entry<String, Long> entrada : ultimaVezVistos.entrySet()) {
                String key = entrada.getKey();
                long ultimaVezVisto = entrada.getValue();

                // SI YA PASÓ EL TIEMPO...
                if (ahoraEnMilis - ultimaVezVisto > HEARTBEAT_TIMEOUT_MS) {                    
                    PeerInfo peerCaido = eliminarPeer(key);
                    crearEventoDesconexion(peerCaido);
                }
            }
        } catch (Exception e) {
            System.err.println("[PeerCleaner] Error: " + e.getMessage());
        }
    }

    /**
     * Metodo para eliminar el peer de los registros del discovery, tanto
     * del peerCleaner como de la listaPeers
     * @param key
     * @return
     */
    private PeerInfo eliminarPeer(String key){
        ultimaVezVistos.remove(key);
        PeerInfo peerCaido = ListaPeers.eliminarPeer(key);
        return peerCaido;
    }

    private void crearEventoDesconexion(PeerInfo peerCaido){
        if (peerCaido != null) {
            System.out.println("[PeerCleaner] Peer muerto detectado: " + peerCaido.getUser()+"@"+ListaPeers.obtenerKey(peerCaido));            
            // El constructor ya lo definimos arriba: new EventoDesconexion(peerCaido)
            EventoDesconexion evento = new EventoDesconexion(peerCaido);
                        
            Gson gson = new Gson();
            String eventoJson = gson.toJson(evento);
            OutgoingMessageDispatcher.dispatch(eventoJson);
        }
    }
}