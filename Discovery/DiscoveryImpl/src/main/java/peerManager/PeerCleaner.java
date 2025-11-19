package peerManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import discovery.ListaPeers;
import dtos.PeerInfo;
import eventos.EventoDesconexion;
import mediadores.OutgoingMessageDispatcher;

public class PeerCleaner implements Runnable {

    private final OutgoingMessageDispatcher dispatcher;
    private final long timeoutMs;
    private final long intervaloLimpiezaMs = 2000;
    private final ScheduledExecutorService scheduler;
    
    // Mapa estático para que los manejadores de entrada puedan actualizar tiempos
    private static final Map<String, Long> ultimaVezVistos = new ConcurrentHashMap<>();

    public PeerCleaner(OutgoingMessageDispatcher dispatcher, long timeoutMs) {
        this.dispatcher = dispatcher;
        this.timeoutMs = timeoutMs;
        this.scheduler = Executors.newScheduledThreadPool(1);
    }

    // Método estático llamado por ManejadorHeartbeat
    public static void actualizarUltimaVezVisto(String peerKey) {
        ultimaVezVistos.put(peerKey, System.currentTimeMillis());
    }

    @Override
    public void run() {
        System.out.println("[PeerCleaner] Iniciando ciclo de limpieza...");
        // Inicia su propio scheduler
        scheduler.scheduleAtFixedRate(this::ejecutarLimpieza, 0, intervaloLimpiezaMs, TimeUnit.MILLISECONDS);
    }

    private void ejecutarLimpieza() {
        try {
            long ahora = System.currentTimeMillis();

            for (Map.Entry<String, Long> entrada : ultimaVezVistos.entrySet()) {
                String key = entrada.getKey();
                long ultimaVez = entrada.getValue();

                // SI YA PASÓ EL TIEMPO...
                if (ahora - ultimaVez > timeoutMs) {
                    
                    // 1. Borrar de mi mapa local
                    ultimaVezVistos.remove(key);

                    // 2. Borrar de ListaPeers y obtener el objeto
                    PeerInfo peerCaido = ListaPeers.eliminarPeer(key);

                    if (peerCaido != null) {
                        System.out.println("[PeerCleaner] Peer muerto detectado: " + key);
                        
                        // 3. CREAR EL EVENTO (Aquí usamos tu clase Evento)
                        // El constructor ya lo definimos arriba: new EventoDesconexion(peerCaido)
                        EventoDesconexion evento = new EventoDesconexion(peerCaido);
                        
                        // 4. PONER EN LA COLA DE SALIDA
                        dispatcher.agregarMensaje(evento);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("[PeerCleaner] Error: " + e.getMessage());
        }
    }
}