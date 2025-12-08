package dtos.aplicacion;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.annotations.SerializedName;

/**
 * Objeto de Transferencia de Datos que representa el estado de una tarjeta de
 * juego.
 * * SOLUCIÓN IMPLEMENTADA:
 * Se utilizan mapas auxiliares con claves String para la serialización JSON,
 * evitando problemas con objetos complejos (Point) como claves en Gson.
 *
 * @author Alici
 */
public class TarjetaDTO {

    /**
     * Mapa lógico para uso de la aplicación (Ignorado por Gson gracias a 'transient').
     */
    private transient Map<Point, Integer> cartas;

    /**
     * Mapa lógico para uso de la aplicación (Ignorado por Gson gracias a 'transient').
     */
    private transient Map<Point, Boolean> fichas;

    // -------------------------------------------------------------------------
    // CAMPOS PARA SERIALIZACIÓN (GSON)
    // -------------------------------------------------------------------------

    /**
     * Representación JSON de las cartas.
     * La clave es un String "x,y" y el valor el ID de la carta.
     * Mapeado al campo "cartas" del JSON.
     */
    @SerializedName("cartas")
    private Map<String, Integer> cartasJson;

    /**
     * Representación JSON de las fichas.
     * La clave es un String "x,y" y el valor el estado.
     * Mapeado al campo "fichas" del JSON.
     */
    @SerializedName("fichas")
    private Map<String, Boolean> fichasJson;

    /**
     * Constructor por defecto.
     */
    public TarjetaDTO() {
    }

    /**
     * Constructor para inicializar con mapas de Points.
     * Automáticamente genera la versión String para JSON.
     */
    public TarjetaDTO(Map<Point, Integer> cartas, Map<Point, Boolean> fichas) {
        setCartas(cartas);
        setFichas(fichas);
    }

    // -------------------------------------------------------------------------
    // GETTERS Y SETTERS (CON LÓGICA DE CONVERSIÓN)
    // -------------------------------------------------------------------------

    public Map<Point, Integer> getCartas() {
        // Sincronización Lazy: Si cartas es null pero tenemos datos del JSON, convertimos.
        if (this.cartas == null && this.cartasJson != null) {
            this.cartas = new HashMap<>();
            for (Map.Entry<String, Integer> entry : this.cartasJson.entrySet()) {
                this.cartas.put(stringToPoint(entry.getKey()), entry.getValue());
            }
        }
        return cartas;
    }

    public void setCartas(Map<Point, Integer> cartas) {
        this.cartas = cartas;
        // Sincronización inversa: Actualizamos el mapa JSON para futuras serializaciones
        if (cartas != null) {
            this.cartasJson = new HashMap<>();
            for (Map.Entry<Point, Integer> entry : cartas.entrySet()) {
                this.cartasJson.put(pointToString(entry.getKey()), entry.getValue());
            }
        }
    }

    public Map<Point, Boolean> getFichas() {
        // Sincronización Lazy
        if (this.fichas == null && this.fichasJson != null) {
            this.fichas = new HashMap<>();
            for (Map.Entry<String, Boolean> entry : this.fichasJson.entrySet()) {
                this.fichas.put(stringToPoint(entry.getKey()), entry.getValue());
            }
        }
        return fichas;
    }

    public void setFichas(Map<Point, Boolean> fichas) {
        this.fichas = fichas;
        // Sincronización inversa
        if (fichas != null) {
            this.fichasJson = new HashMap<>();
            for (Map.Entry<Point, Boolean> entry : fichas.entrySet()) {
                this.fichasJson.put(pointToString(entry.getKey()), entry.getValue());
            }
        }
    }

    // -------------------------------------------------------------------------
    // MÉTODOS UTILITARIOS DE CONVERSIÓN
    // -------------------------------------------------------------------------

    /**
     * Convierte un Point a formato "x,y"
     */
    private String pointToString(Point p) {
        return p.x + "," + p.y;
    }

    /**
     * Convierte un formato "x,y" a Point
     */
    private Point stringToPoint(String s) {
        try {
            String[] parts = s.split(",");
            return new Point(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
        } catch (Exception e) {
            System.err.println("Error parseando Point desde String: " + s);
            return new Point(0, 0); // Fallback seguro
        }
    }
}