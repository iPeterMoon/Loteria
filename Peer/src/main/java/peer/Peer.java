package peer;

import java.net.InetAddress;
import java.util.List;
import dtos.PeerInfo;
import eventos.Evento;
import interfaces.IObserver;
import interfaces.IPeer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import network.DiscoveryRegistrar;
import network.EnvioHandler;
import network.RecepcionHandler;
import procesadorEventos.ProcesadorMensajes;

/**
 *
 * @author Alici
 */
public class Peer implements IPeer {

    private final PeerInfo myInfo;
    private final BlockingQueue<String> incomingQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<Evento> outgoingQueue = new LinkedBlockingQueue<>();
    private final IObserver observer;
    private final List<PeerInfo> peersPartida = new CopyOnWriteArrayList<>();

    private final EnvioHandler envioHandler;
    private final RecepcionHandler recepcionHandler;
    private final ProcesadorMensajes messageProcessor;
    private final DiscoveryRegistrar discoveryRegistrar;

    private volatile boolean running = false;

    public Peer(IObserver observer) {
        this.observer = observer;
        this.myInfo = new PeerInfo(null, "", 0);

        this.envioHandler = new EnvioHandler(outgoingQueue, peersPartida);
        this.recepcionHandler = new RecepcionHandler(incomingQueue);
        this.messageProcessor = new ProcesadorMensajes(incomingQueue, this::handleEvento, peersPartida, myInfo);
        this.discoveryRegistrar = new DiscoveryRegistrar(myInfo);
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
            int puerto = recepcionHandler.start();
            if (puerto == -1) {
                throw new RuntimeException("No se pudo iniciar recepción");
            }

            String ip = InetAddress.getLocalHost().getHostAddress();
            myInfo.setIp(ip);
            myInfo.setPort(puerto);

            startBackgroundThreads();

            discoveryRegistrar.registrar();

            running = true;
        } catch (Exception e) {
            System.err.println("Error al iniciar peer: " + e.getMessage());
            stop();
        }
    }

    private void startBackgroundThreads() {
        Executors.newSingleThreadExecutor().execute(envioHandler);
        Executors.newSingleThreadExecutor().execute(recepcionHandler);
        Executors.newSingleThreadExecutor().execute(messageProcessor);
    }

    @Override
    public void stop() {
        running = false;
        recepcionHandler.stop();
        envioHandler.stop();
        messageProcessor.stop();
        System.out.println("[Peer] Detenido.");
    }

    @Override
    public void broadcastEvento(Evento evento) {
        if (peersPartida.isEmpty()) {
            System.err.println("[Peer] No hay peers para broadcast.");
            return;
        }
        outgoingQueue.offer(evento);
    }

    @Override
    public void setUser(String user) {
        myInfo.setUser(user);
        discoveryRegistrar.actualizarUsuario(user);
    }

    private void handleEvento(Evento evento) {
        observer.update(evento);
    }

    public List<PeerInfo> getPeersPartida() {
        return List.copyOf(peersPartida);
    }
}
