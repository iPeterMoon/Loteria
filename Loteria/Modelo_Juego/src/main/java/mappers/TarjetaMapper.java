package mappers;

import dtos.aplicacion.TarjetaDTO;
import java.awt.Point;
import java.util.Map;
import modelo.Tarjeta;

/**
 * Clase utilitaria (Mapper) encargada de la conversión de objetos entre la
 * entidad de modelo (Tarjeta) y el objeto de transferencia de datos (TarjetaDTO).
 *
 * @author Peter
 */
public class TarjetaMapper {

    /**
     * Convierte una entidad Tarjeta del modelo a un objeto de transferencia TarjetaDTO.
     * 
     * Este método extrae los mapas de cartas y fichas de la tarjeta del modelo
     * y los coloca en una instancia de TarjetaDTO lista para ser serializada
     * y transmitida por red.
     *
     * @param tarjeta La entidad Tarjeta del modelo a convertir.
     * @return Una nueva instancia de TarjetaDTO con los datos mapeados.
     *         Si tarjeta es null, retorna null.
     */
    public static TarjetaDTO toDTO(Tarjeta tarjeta) {
        if (tarjeta == null) {
            return null;
        }

        Map<Point, Integer> cartas = tarjeta.getCartas();
        Map<Point, Boolean> fichas = tarjeta.getFichas();

        return new TarjetaDTO(cartas, fichas);
    }

    /**
     * Convierte un objeto de transferencia TarjetaDTO a una entidad Tarjeta del modelo.
     * 
     * Este método crea una nueva instancia de Tarjeta a partir de los datos
     * contenidos en el TarjetaDTO recibido (típicamente desde red/distribución).
     * Las fichas se reconstruyen automáticamente en el constructor de Tarjeta.
     *
     * @param tarjetaDTO El objeto TarjetaDTO a convertir.
     * @return Una nueva instancia de Tarjeta con los datos mapeados.
     *         Si tarjetaDTO es null, retorna null.
     */
    public static Tarjeta toTarjeta(TarjetaDTO tarjetaDTO) {
        if (tarjetaDTO == null) {
            return null;
        }

        // Obtener el mapa de cartas del DTO
        Map<Point, Integer> cartas = tarjetaDTO.getCartas();

        // Crear una nueva Tarjeta con las cartas
        // El constructor de Tarjeta se encargará de reinicializar las fichas
        Tarjeta tarjeta = new Tarjeta(cartas);

        // Si el DTO tiene fichas definidas (no null), las asignamos
        if (tarjetaDTO.getFichas() != null) {
            tarjeta.setFichas(tarjetaDTO.getFichas());
        }

        return tarjeta;
    }
}
