/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package procesadorEventos;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import static enums.TipoEvento.FICHA;
import eventos.Evento;
import eventos.EventoFicha;
import peer.Peer;

/**
 * Manejador que procesa los eventos de juego.
 * Si el evento no corresponde, lo pasa al siguiente manejador.
 * 
 * @author rocha
 */
public class ManejadorEventoJuego extends ManejadorMensajes {

    private final Gson gson = new Gson();

    /**
     * Procesa el evento recibido. Si corresponde a algún evento del juego, se notifica al modelo
     * del juego. En caso contrario, se pasa al siguiente manejador.
     *
     * @param json evento en formato JSON
     */
    @Override
    public void procesar(JsonObject json) {
        String tipo = json.get("tipoEvento").getAsString();

        if (FICHA.name().equals(tipo)) { // Agregar otros tipos de eventos del juego
            procesarEvento(json);
        } else if (next != null) {
            next.procesar(json);
        }
    }

    /**
     * Convierte el evento en un objeto y lo envía al modelo del juego.
     * 
     * @param json evento en formato JSON
     */
    private void procesarEvento(JsonObject json) {
        // Notificar al modelo de juego
    }
}
