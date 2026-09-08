package network;

import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import util.ConfigLoader;

public class NetworkHelper {

    /**
     * Propiedad del sistema para forzar manualmente la IP publicada.
     * Uso: java -Dpeer.ip=192.168.0.150 -jar Loteria.jar
     */
    private static final String PROP_IP_MANUAL = "peer.ip";

    /**
     * Busca la dirección IP que se debe publicar a los demás peers.
     *
     * Estrategia, en orden:
     * 1. IP forzada por el usuario con -Dpeer.ip (escape hatch para redes raras).
     * 2. La IP local que el sistema operativo usa para alcanzar al servidor de
     *    discovery. Es la única que garantiza que el discovery y los demás peers
     *    puedan abrir una conexión de regreso hacia nosotros.
     * 3. Barrido de interfaces como último recurso, ignorando adaptadores
     *    virtuales (Docker, VirtualBox, VMware, WSL/Hyper-V).
     *
     * @return IP en formato String
     */
    public static String obtenerIpPrioritaria() {
        String ipManual = System.getProperty(PROP_IP_MANUAL);
        if (ipManual != null && !ipManual.isBlank()) {
            System.out.println("[Red] IP forzada por -D" + PROP_IP_MANUAL + ": " + ipManual);
            return ipManual.trim();
        }

        String ipRuta = obtenerIpDeRutaAlDiscovery();
        if (ipRuta != null) {
            System.out.println("[Red] IP seleccionada (ruta hacia el discovery): " + ipRuta);
            return ipRuta;
        }

        String ipInterfaz = obtenerIpPorBarridoDeInterfaces();
        System.out.println("[Red] IP seleccionada (barrido de interfaces): " + ipInterfaz);
        return ipInterfaz;
    }

    /**
     * Pregunta al sistema operativo qué IP local usaría para hablarle al
     * servidor de discovery. Al ser UDP no se envía ningún paquete: sólo se
     * consulta la tabla de rutas, así que es inmediato y no falla si el
     * discovery está apagado.
     *
     * @return IP local de salida, o null si no se pudo determinar.
     */
    private static String obtenerIpDeRutaAlDiscovery() {
        String ipDiscovery = ConfigLoader.getInstance().getIpServidor();
        int puertoDiscovery = ConfigLoader.getInstance().getPuertoDiscovery();

        try (DatagramSocket socket = new DatagramSocket()) {
            socket.connect(new InetSocketAddress(ipDiscovery, puertoDiscovery));
            InetAddress local = socket.getLocalAddress();

            if (local == null || local.isAnyLocalAddress() || local.isLoopbackAddress()
                    || !(local instanceof Inet4Address)) {
                return null;
            }
            return local.getHostAddress();
        } catch (Exception e) {
            System.err.println("[Red] No se pudo resolver la ruta hacia el discovery ("
                    + ipDiscovery + "): " + e.getMessage());
            return null;
        }
    }

    /**
     * Recorre las interfaces de red buscando una IPv4 utilizable, descartando
     * las de adaptadores virtuales que no son alcanzables desde otra
     * computadora de la red.
     *
     * @return IP encontrada, o 127.0.0.1 si no hay ninguna.
     */
    private static String obtenerIpPorBarridoDeInterfaces() {
        String ipLocal = "127.0.0.1";

        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();

                // Ignorar interfaces apagadas, de loopback o virtuales
                if (networkInterface.isLoopback() || !networkInterface.isUp()
                        || esInterfazVirtual(networkInterface)) {
                    continue;
                }

                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();

                    // Solo nos interesan las IPv4
                    if (address instanceof Inet4Address) {
                        String ip = address.getHostAddress();

                        // PRIORIDAD 2: Rangos de VPN Gaming comunes
                        // 25.x.x.x es el clásico de Hamachi
                        // 26.x.x.x es el clásico de Radmin VPN
                        if (ip.startsWith("25.") || ip.startsWith("26.")) {
                            System.out.println("[Red] IP de VPN detectada y seleccionada: " + ip);
                            return ip;
                        }

                        // PRIORIDAD 1: Rangos privados estándar. Se guarda como
                        // candidata por si no aparece ninguna VPN.
                        if (esIpPrivada(ip)) {
                            ipLocal = ip;
                        }
                    }
                }
            }
        } catch (SocketException e) {
            System.err.println("[Red] Error al buscar interfaces: " + e.getMessage());
        }

        return ipLocal;
    }

    /**
     * Determina si una interfaz corresponde a un adaptador virtual (Docker,
     * VirtualBox, VMware, WSL/Hyper-V), cuyas IPs no son visibles para las
     * demás computadoras de la red.
     *
     * @param networkInterface Interfaz a evaluar.
     * @return true si es virtual y debe descartarse.
     */
    private static boolean esInterfazVirtual(NetworkInterface networkInterface) throws SocketException {
        if (networkInterface.isVirtual() || networkInterface.isPointToPoint()) {
            return true;
        }

        String nombre = (networkInterface.getName() + " " + networkInterface.getDisplayName())
                .toLowerCase();

        return nombre.contains("docker")
                || nombre.contains("virtualbox")
                || nombre.contains("vethernet")
                || nombre.contains("vmware")
                || nombre.contains("vmnet")
                || nombre.contains("hyper-v")
                || nombre.contains("wsl")
                || nombre.startsWith("br-")
                || nombre.startsWith("veth")
                || nombre.startsWith("virbr");
    }

    /**
     * Verifica si una IPv4 pertenece a un rango privado real
     * (10.0.0.0/8, 172.16.0.0/12 o 192.168.0.0/16).
     *
     * @param ip IP en formato String.
     * @return true si es una IP privada válida para la red local.
     */
    private static boolean esIpPrivada(String ip) {
        if (ip.startsWith("10.") || ip.startsWith("192.168.")) {
            return true;
        }

        if (ip.startsWith("172.")) {
            try {
                int segundoOcteto = Integer.parseInt(ip.split("\\.")[1]);
                return segundoOcteto >= 16 && segundoOcteto <= 31;
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                return false;
            }
        }

        return false;
    }
}
