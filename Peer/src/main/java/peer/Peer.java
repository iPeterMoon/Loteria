package peer;

import com.google.gson.Gson;
import dtos.PeerInfo;
import eventos.Evento;
import interfaces.IObserver;
import interfaces.IPeer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import network.DiscoveryRegistrar;
import network.EnvioPeer;
//import network.MessageDispatcher;
import network.RecepcionPeer;
import utilPeer.PoolHilos;
import network.OutgoingMessageDispatcher;

/**
 *
 * @author Alici
 */
public class Peer implements IPeer {

    private static Peer instance;
    
    private PeerInfo myInfo;
    private EnvioPeer envioHandler;
    private RecepcionPeer recepcionHandler;
    private IObserver observer;
    private final BlockingQueue<String> outgoingQueue = new LinkedBlockingQueue<>();
    
    private Peer(){
        this.myInfo = new PeerInfo(null, "", 0);
        
        this.envioHandler = new EnvioPeer(outgoingQueue);
        this.recepcionHandler = RecepcionPeer.getInstance();
    }
    
    public static Peer getInstance(){
        if(instance == null) {
            instance = new Peer();
        }
        return instance;
    }
    

    private volatile boolean running = false;

    public Peer(IObserver observer) {
        this.observer = observer;
    }

    @Override
    public PeerInfo getMyInfo() {
        return myInfo;
    }

    @Override
    public void start() {
        if (running) {
            return;
        }

        try {
            String key = recepcionHandler.empezarEscucha();
            if(key == null) {
                throw new Exception("Error al empezar la escucha");
            }
            String[] netInfo = key.split(":");
            myInfo.setIp(netInfo[0]);
            myInfo.setPort(Integer.parseInt(netInfo[1]));
            DiscoveryRegistrar.registrar(myInfo);

            empezarEnvio();
            
            running = true;
            System.out.println("Peer corriendo");
        } catch (Exception e) {
            System.err.println("Error al iniciar peer: " + e.getMessage());
            stop();
        }
    }

    private void empezarEnvio(){
        ExecutorService threadPool = PoolHilos.getInstance().getThreadPool();
        threadPool.submit(envioHandler);
    }
    
    @Override
    public void broadcastEvento(Evento evento) {
        Gson gson = new Gson();
        String json = gson.toJson(evento);
        OutgoingMessageDispatcher.dispatch(json, outgoingQueue);
    }

    @Override
    public void setUser(String user) {
        myInfo.setUser(user);
        DiscoveryRegistrar.registrar(myInfo);
    }

    public void handleEvento(Evento evento) {
        observer.update(evento);
    }

    @Override
    public void stop() {
        running = false;
        envioHandler.stop();
        recepcionHandler.stop();
        System.out.println("[Peer] Detenido.");
    }

    @Override
    public void setObserver(IObserver observer) {
        this.observer = observer;
    }
    
}
