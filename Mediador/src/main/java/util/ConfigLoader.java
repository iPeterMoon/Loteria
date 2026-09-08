package util;

import com.google.gson.Gson;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigLoader {

    private static ConfigLoader instancia;
    private ConfiguracionDefault config;
    private static final Logger LOG = Logger.getLogger(ConfigLoader.class.getName());

    private static final String CONFIG_FILE_NAME = "configuracion.json";

    private static class ConfiguracionDefault {

        String ip_servidor = "automundo.ddns.net";
        int puerto_discovery = 5000;
        int puerto_matchmaker = 6000;
        String usuario_matchmaker = "MATCHMAKER";
        /**
         * Puerto fijo en el que cada Peer (jugador o MatchMaker) escucha
         * conexiones entrantes. Usar un puerto fijo permite abrir una sola
         * regla de firewall por máquina en vez de uno distinto cada vez que
         * se reinicia la aplicación. 0 = puerto aleatorio (comportamiento
         * anterior).
         */
        int puerto_peer = 12347;
    }

    // Constructor privado para Singleton
    private ConfigLoader() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE_NAME); Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {

            if (inputStream == null) {
                throw new RuntimeException("No se pudo encontrar " + CONFIG_FILE_NAME + " en los recursos.");
            }

            Gson gson = new Gson();
            this.config = gson.fromJson(reader, ConfiguracionDefault.class);
            LOG.info("Configuración cargada exitosamente desde recursos.");

        } catch (Exception e) {
            LOG.log(Level.SEVERE, "No se pudo cargar " + CONFIG_FILE_NAME + ". Usando valores por defecto.", e);
            this.config = new ConfiguracionDefault();
        }
    }

    /**
     * Obtiene la instancia única de ConfigLoader.
     *
     * @return
     */
    public static synchronized ConfigLoader getInstance() {
        if (instancia == null) {
            instancia = new ConfigLoader();
        }
        return instancia;
    }

    // Getters públicos para acceder a los valores
    public String getIpServidor() {
        return config.ip_servidor;
    }

    public int getPuertoDiscovery() {
        return config.puerto_discovery;
    }

    public int getPuertoMatchmaker() {
        return config.puerto_matchmaker;
    }

    public String getUsuarioMatchmaker() {
        return config.usuario_matchmaker;
    }

    /**
     * Puerto fijo en el que este Peer debe intentar escuchar. Si el puerto
     * está ocupado (por ejemplo, dos procesos en la misma máquina), el
     * componente de recepción cae automáticamente a uno aleatorio.
     *
     * @return Puerto configurado, o 0 si se debe usar siempre uno aleatorio.
     */
    public int getPuertoPeer() {
        return config.puerto_peer;
    }

}
