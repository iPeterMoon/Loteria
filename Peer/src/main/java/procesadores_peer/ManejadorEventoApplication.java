package procesadores_peer;

import com.google.gson.JsonObject;
import eventos.Evento;
import peer.Peer;
import utilPeer.EventoApplicationMapper;

/**
 * Manejador que procesa los eventos de aplicación recibidos del JSON.
 * Utiliza EventoApplicationMapper para deserializar automáticamente
 * a la clase correcta basándose en el tipoEvento.
 *
 * @author rocha
 */
public class ManejadorEventoApplication extends ManejadorMensajesLlegada {

    /**
     * Procesa el evento recibido. Si es un evento de aplicación (tiene tipoEvento),
     * lo deserializa a la clase correcta y lo notifica.
     * Si no es un evento de aplicación, lo pasa al siguiente manejador.
     *
     * @param json evento en formato JSON
     */
    @Override
    public void procesar(JsonObject json) {
        try {
            // Mapear a Evento usando la convención de nombres
            Evento evento = EventoApplicationMapper.mapearEvento(json);

            if (evento != null) {
                // Es un evento de aplicación, notificar
                Peer.getInstance().notify(evento);
            } else if (next != null) {
                // No es un evento de aplicación, pasar al siguiente
                next.procesar(json);
            }
        } catch (Exception e) {
            System.err.println("[ManejadorEventoApplication] Error procesando evento: " + e.getMessage());
            if (next != null) {
                next.procesar(json);
            }
        }
    }
}

