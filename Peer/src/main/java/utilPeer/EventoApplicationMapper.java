package utilPeer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import eventos.Evento;

/**
 * Mapper que convierte JsonObject a instancias correctas de evento de aplicación
 * usando reflection y naming conventions, sin necesidad de conocer los tipos específicos.
 * 
 * Retorna Object pero casteado a la clase correcta en tiempo de ejecución.
 * Patrón: INICIAR_PARTIDA → EventoIniciarPartida
 */
public class EventoApplicationMapper {
    
    private static final Gson gson = new Gson();
    private static final String EVENTOS_PACKAGE = "eventos.eventos_aplicacion.";
    
    /**
     * Deserializa un JsonObject a la clase correcta de evento de aplicación
     * basándose en el campo tipoEvento.
     * 
     * @param json JsonObject con el evento
     * @return Instancia casteada al tipo correcto de Evento, o null si no es un evento de aplicación
     */
    public static Evento mapearEvento(JsonObject json) {
        try {
            // Verificar si tiene tipoEvento
            if (!json.has("tipoEvento")) {
                return null;
            }
            
            String tipoEvento = json.get("tipoEvento").getAsString();
            
            // Derivar el nombre de clase a partir del tipoEvento
            String nombreClase = derivarNombreClase(tipoEvento);
            String fqn = EVENTOS_PACKAGE + nombreClase;
            
            // Cargar la clase dinámicamente como Class<? extends Evento>
            @SuppressWarnings("unchecked")
            Class<? extends Evento> claseEvento = (Class<? extends Evento>) Class.forName(fqn);
            
            // Deserializar y retornar casteado a Evento
            return gson.fromJson(json, claseEvento);
            
        } catch (ClassNotFoundException e) {
            System.err.println("[EventoApplicationMapper] Clase no encontrada para tipoEvento: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("[EventoApplicationMapper] Error mapeando evento: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Convierte un tipoEvento en formato ENUM (ej: INICIAR_PARTIDA)
     * a un nombre de clase en formato CamelCase (ej: EventoIniciarPartida).
     * 
     * Patrón: INICIAR_PARTIDA → Iniciar + Partida → IniciarPartida → EventoIniciarPartida
     */
    private static String derivarNombreClase(String tipoEvento) {
        String[] partes = tipoEvento.split("_");
        StringBuilder sb = new StringBuilder();
        
        for (String parte : partes) {
            sb.append(parte.substring(0, 1).toUpperCase())
              .append(parte.substring(1).toLowerCase());
        }
        
        return "Evento" + sb.toString();
    }
}

