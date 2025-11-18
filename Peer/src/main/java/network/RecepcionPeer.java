package network;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import procesadorEventos.ProcesadorMensajesLlegada;
import utilPeer.PoolHilos;

/**
 *
 * @author pedro
 */
public class RecepcionPeer {

    private static RecepcionPeer instance;

    private final ExecutorService threadPool;

    private RecepcionPeer() {
        this.threadPool = PoolHilos.getInstance().getThreadPool();
    }

    public static RecepcionPeer getInstance() {
        if (instance == null) {
            instance = new RecepcionPeer();
        }
        return instance;
    }
    
    private ProcesadorMensajesLlegada procesador;
    private RedListener redListener;
    
    /**
     * Empieza la recepción de mensajes
     * @return El key con la ip y puerto del Peer
     */
    public String empezarEscucha(){
        try{
            redListener = new RedListener();
            procesador = new ProcesadorMensajesLlegada();
            int port = redListener.start();
            
            ejecutarHilos();
            
            String ip = InetAddress.getLocalHost().getHostAddress();
            return ip + ":" + port;
            
        } catch (UnknownHostException e){
            System.out.println("Problema al obtener la dirección ip");
            return null;
        }
    }
    
    private void ejecutarHilos(){
        threadPool.execute(redListener);
        threadPool.execute(procesador);
    }
    
    public void stop(){
        redListener.stop();
        procesador.stop();
    }

}
