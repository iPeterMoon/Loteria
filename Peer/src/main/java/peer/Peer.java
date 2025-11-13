package peer;

import com.google.gson.Gson;
import dtos.PeerInfo;
import eventos.Evento;
import interfaces.IObserver;
import interfaces.IPeer;
import java.util.concurrent.ExecutorService;
import network.DiscoveryRegistrar;
import network.EnvioPeer;
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
    
    private Peer(){
        this.myInfo = new PeerInfo(null, "", 0);
        
        this.envioHandler = EnvioPeer.getInstance();
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

    public PeerInfo getMyInfo() {
        return myInfo;
    }

    @Override
    public void start() {
        if (running) {
            return;
        }

        try {
            
            empezarEscucha();
            
            empezarEnvio();

            DiscoveryRegistrar.registrar(myInfo);

            empezarHeartbeat();
            
            running = true;
            System.out.println("Peer corriendo");
        } catch (Exception e) {
            System.err.println("Error al iniciar peer: " + e.getMessage());
            stop();
        }
    }

    private synchronized void empezarEscucha() throws Exception{
        String key = recepcionHandler.empezarEscucha();
            if(key == null) {
                throw new Exception("Error al empezar la escucha");
            }
            String[] netInfo = key.split(":");
            myInfo.setIp(netInfo[0]);
            myInfo.setPort(Integer.parseInt(netInfo[1]));
    }

    private void empezarEnvio(){
        ExecutorService threadPool = PoolHilos.getInstance().getThreadPool();
        threadPool.submit(envioHandler);
    }
    
    private void empezarHeartbeat() {
        ExecutorService threadPool = PoolHilos.getInstance().getThreadPool();
        Heartbeat heartbeat = new Heartbeat(myInfo);
        threadPool.submit(heartbeat);
    }

    @Override
    public void broadcastEvento(Evento evento) {
        Gson gson = new Gson();
        String json = gson.toJson(evento);
        OutgoingMessageDispatcher.dispatch(json);
    }

    @Override
    public void setUser(String user) {
        myInfo.setUser(user);
        PeersConectados.getInstance().setMyInfo(myInfo);
        DiscoveryRegistrar.registrar(myInfo);
    }

    public void notify(Evento evento) {
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
