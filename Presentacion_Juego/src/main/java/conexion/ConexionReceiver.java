package conexion;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ConexionReceiver.java Clase que se encarga de recibir mensajes de anuncio por
 * Multicast (UDP). Si el anuncio no es propio, establece una conexión TCP con
 * el peer remoto y se vuelve a anunciar para que todos los demás se enteren
 * (estrategia de propagación).
 *
 * @author pedro
 */
public class ConexionReceiver implements Runnable {

    private final int MULTICAST_PORT = 4446;
    private final String MULTICAST_ADDRESS = "230.0.0.1";
    private volatile boolean isRunning = true;

    // Almacena las conexiones TCP salientes (somos el cliente para ellos)
    private final ConcurrentHashMap<String, PeerHandler> peersConectados = new ConcurrentHashMap<>();

    private final int miPuertoTCP;
    private final String miIpLocal;

    /**
     * Constructor que recibe el puerto TCP de escucha de esta instancia.
     *
     * @param miPuertoTCP Puerto TCP en el que escucha el ServidorTCP local.
     * @throws UnknownHostException
     */
    public ConexionReceiver(int miPuertoTCP) throws UnknownHostException {
        this.miPuertoTCP = miPuertoTCP;
        String ipDetectada = null;
        try {
            NetworkInterface netIf = SeleccionadorInterfaz.seleccionarInterfazRed();
            if (netIf != null) {
                Enumeration<InetAddress> addresses = netIf.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    if (addr instanceof Inet4Address) {
                        ipDetectada = addr.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            System.err.println("[RECEIVER] Error al seleccionar interfaz para obtener IP local: " + e.getMessage());
        }
        this.miIpLocal = (ipDetectada != null) ? ipDetectada : InetAddress.getLocalHost().getHostAddress();
        System.out.println("[RECEIVER] Mi IP local para comparaciones es: " + this.miIpLocal);
    }

    @Override
    public void run() {
        prepararSocket();
    }

    private void prepararSocket() {
        MulticastSocket socket = null;
        try {
            socket = new MulticastSocket(MULTICAST_PORT);
            InetAddress groupAddress = InetAddress.getByName(MULTICAST_ADDRESS);
            SocketAddress group = new InetSocketAddress(groupAddress, MULTICAST_PORT);

            NetworkInterface netIf = SeleccionadorInterfaz.seleccionarInterfazRed();
            if (netIf != null) {
                socket.joinGroup(group, netIf);
                System.out.println("[RECEIVER] Unido al grupo multicast: " + groupAddress.getHostAddress());
                recibirMensajes(socket);
            } else {
                System.err.println(
                        "[RECEIVER] No se pudo unir al grupo multicast. No se encontró una interfaz de red adecuada.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                try {
                    // Cierra el socket de forma segura
                    socket.leaveGroup(new InetSocketAddress(MULTICAST_ADDRESS, MULTICAST_PORT),
                            SeleccionadorInterfaz.seleccionarInterfazRed());
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void recibirMensajes(MulticastSocket socket) {
        while (isRunning) {
            try {
                byte[] buf = new byte[256];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                String received = new String(packet.getData(), 0, packet.getLength());
                String[] partes = received.split(":");
                String ipRemota = partes[0];
                int puertoRemoto = Integer.parseInt(partes[1]);

                String peerID = ipRemota + ":" + puertoRemoto;

                // 1. Evitar mi propio anuncio (misma IP y mismo Puerto TCP)
                if (ipRemota.equals(miIpLocal) && puertoRemoto == miPuertoTCP) {
                    continue;
                }

                // 2. Si es un Peer nuevo (o no tengo una conexión TCP saliente registrada)
                if (!peersConectados.containsKey(peerID)) {
                    System.out.println("[RECEIVER] Descubierto nuevo Peer: " + peerID);

                    // Intenta establecer la conexión TCP (nosotros somos el cliente)
                    establecerConexionTCP(ipRemota, puertoRemoto, peerID);
                } else {
                    //Si el Peer ya está en la lista, no hacemos nada
                    System.out.println("[RECEIVER] Anuncio recibido de " + peerID + ", ya conectado. Ignorando re-anuncio");
                }

            } catch (SocketException e) {
                if (!isRunning) {
                    System.out.println("[RECEIVER] Socket de recepción cerrado.");
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void establecerConexionTCP(String ipRemota, int puertoRemoto, String peerID) {
        Socket peerSocket = new Socket();
        try {
            System.out.println("[RECEIVER] Intentando conexión TCP a " + peerID + "...");
            // Timeout de 5 segundos para que no se quede bloqueado
            peerSocket.connect(new InetSocketAddress(ipRemota, puertoRemoto), 5000);

            System.out.println("[RECEIVER] Conexión TCP exitosa a " + peerID);

            // Inicia un nuevo PeerHandler para manejar este socket como cliente
            PeerHandler handler = new PeerHandler(peerSocket);
            // Registrar el Peer
            peersConectados.put(peerID, handler);

            //Integración con PeerService: registrar conexión saliente
            PeerService.getInstancia().agregarHandlerSaliente(peerID, handler);
            new Thread(handler).start();

            // Re-anuncio: Solo se hace si la conexión fue exitosa
            ConexionPublisher.anunciarConexion(miPuertoTCP);

        } catch (IOException e) {
            // Si la conexion falla, NO lo añadimos a peersConectados, por lo que puede
            // reintentar en el futuro.
            System.err.println("[RECEIVER] Error al conectar a Peer " + peerID + ": " + e.getMessage());
            try {
                // Asegurar que el socket se cierre en caso de error de conexión
                if (!peerSocket.isClosed()) {
                    peerSocket.close();
                }
            } catch (IOException closeEx) {

            }
        }
    }

    public void stop() {
        this.isRunning = false;
        // Cierra los handlers salientes
        peersConectados.values().forEach(PeerHandler::closeConnection);
    }
}
