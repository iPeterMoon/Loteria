package network;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class NetworkHelper {

    /**
     * Busca la dirección IP más apropiada para juegos en red local o VPN.
     * Prioriza rangos comunes de VPN (25.x.x.x de Hamachi, 26.x.x.x de Radmin)
     * sobre las IPs locales tradicionales (192.168.x.x).
     * @return IP en formato String
     */
    public static String obtenerIpPrioritaria() {
        String ipLocal = "127.0.0.1";
        
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                
                // Ignorar interfaces apagadas o de loopback (localhost)
                if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                    continue;
                }

                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    
                    // Solo nos interesan las IPv4
                    if (address instanceof Inet4Address) {
                        String ip = address.getHostAddress();
                        
                        // PRIORIDAD 1: Rangos de VPN Gaming comunes
                        // 25.x.x.x es el clásico de Hamachi
                        // 26.x.x.x es el clásico de Radmin VPN
                        if (ip.startsWith("25.") || ip.startsWith("26.")) {
                            System.out.println("[Red] IP de VPN detectada y seleccionada: " + ip);
                            return ip;
                        }
                        
                        // PRIORIDAD 2: Rangos locales estándar (192.168.x.x, 10.x.x.x, 172.x.x.x)
                        // Guardamos esta como "candidata" por si no encontramos una VPN
                        if (ip.startsWith("192.") || ip.startsWith("10.") || ip.startsWith("172.")) {
                            ipLocal = ip;
                        }
                    }
                }
            }
        } catch (SocketException e) {
            System.err.println("[Red] Error al buscar interfaces: " + e.getMessage());
        }
        
        System.out.println("[Red] IP seleccionada: " + ipLocal);
        return ipLocal;
    }
}