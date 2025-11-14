/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package procesadorEventos;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import enums.TipoEvento;
import eventos.Evento;
import eventos.EventoFicha;
import java.util.Map;
import peer.Peer;

/**
 * Manejador que procesa los eventos de juego. Si el evento no corresponde, lo
 * pasa al siguiente manejador.
 *
 * @author rocha
 */
public class ManejadorEventoApplication extends ManejadorMensajesLlegada {

    private final Gson gson = new Gson();

    // Mapa con los tipos de eventos del juego
    private static final Map<TipoEvento, Class<? extends Evento>> EVENTO_MAP = Map.of(
            TipoEvento.FICHA, EventoFicha.class
    );

    /**
     * Procesa el evento recibido. Si corresponde a algún evento del juego, se
     * notifica al modelo del juego. En caso contrario, se pasa al siguiente
     * manejador.
     *
     * @param json evento en formato JSON
     */
    @Override
    public void procesar(JsonObject json) {
        TipoEvento tipo = TipoEvento.valueOf(json.get("tipoEvento").getAsString());

        if (EVENTO_MAP.containsKey(tipo)) {
            procesarEvento(json, tipo);
        } else if (next != null) {
            next.procesar(json);
        }
    }

    /**
     * Convierte el evento en un objeto y lo envía al modelo del juego.
     *
     * @param json evento en formato JSON
     */
    private void procesarEvento(JsonObject json, TipoEvento tipo) {
        Class<? extends Evento> claseEvento = EVENTO_MAP.get(tipo);

        if (claseEvento != null) {
            Evento evento = gson.fromJson(json, claseEvento);

            System.out.println("[ManejadorEventoJuego] Notificando evento: " + evento);
            // Notificar al modelo de juego
            Peer.getInstance().notify(evento);
        }
    }
}
