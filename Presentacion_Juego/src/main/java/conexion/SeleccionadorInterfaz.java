package conexion;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * SeleccionadorInterfaz.java
 * 
 * Esta clase se encarga de seleccionar una interfaz de red para la comunicación
 * en MultiCast
 * 
 * @author pedro
 */
public class SeleccionadorInterfaz {

    /**
     * Metodo principal para seleccionar una interfaz de red, se encarga de iterar
     * en todas las interfaces de red
     * disponibles y regresar la primera interfaz disponible que cumpla con ciertos
     * requisitos.
     * 
     * @return Interfaz de red válida
     * @throws SocketException
     */
    public static NetworkInterface seleccionarInterfazRed() throws SocketException {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface iface = interfaces.nextElement();
            String displayName = iface.getDisplayName();
            // Filtros: activa, no loopback, no virtual (por propiedad o nombre)
            if (iface.isUp() && !iface.isLoopback() && !iface.isVirtual() &&
                    !displayName.contains("VirtualBox") && !displayName.contains("VMware") &&
                    !displayName.contains("Loopback") && !iface.isPointToPoint()) {
                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    if (addr instanceof Inet4Address) {
                        System.out
                                .println("Usando interfaz: " + displayName + " con dirección " + addr.getHostAddress());
                        return iface;
                    }
                }
            }
        }
        return null;
    }
}
