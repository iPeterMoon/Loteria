package mappers;

import dtos.JugadorDTO;
import dtos.TarjetaDTO;
import java.awt.Image;
import java.awt.Point;
import java.util.Map;
import javax.swing.ImageIcon;
import modelo.Tarjeta;

import modelo.Jugador;

/**
 * Clase utilitaria (Mapper) encargada de la conversión de objetos entre el
 * modelo de juego (Jugador, Tarjeta) y los objetos de transferencia de datos o
 * sujetos de vista (JugadorDTO, JugadorSubject). * Se encarga de la lógica
 * específica de conversión, como la carga de la imagen de perfil a partir de su
 * ruta.
 *
 * @author Alici
 */
public class JugadorMapperModelo {

    /**
     * Convierte un objeto del Modelo de Juego (Jugador) a un Objeto de
     * Transferencia de Datos (JugadorDTO).
     *
     * @param jugador El objeto Jugador del modelo a mapear.
     * @param esJugadorPrincipal booleano que indica si el jugador dado es el principal
     * @return Una nueva instancia de JugadorDTO con los datos mapeados.
     */
    public static JugadorDTO toDTO(Jugador jugador, boolean esJugadorPrincipal) {
        TarjetaDTO tarjetaDTO = new TarjetaDTO(jugador.getTarjeta().getCartas(), jugador.getTarjeta().getFichas());
        ImageIcon imagenIcono = new ImageIcon(jugador.getFotoPerfil());
        Image imagenPerfil = imagenIcono.getImage();

        return new JugadorDTO(jugador.getNickname(), imagenPerfil, jugador.getPuntos(), tarjetaDTO, esJugadorPrincipal);
    }
    
    /**
     * Convierte un Objeto de Transferencia de Datos (JugadorDTO) a un
     * objeto del Modelo de Juego (Jugador).
     * * NOTA: La ruta de la foto de perfil (String) se pierde en el DTO. 
     * Aquí la inicializamos con una cadena vacía o nula, ya que el DTO 
     * solo contiene el objeto Image.
     *
     * @param jugadorDTO El objeto JugadorDTO a mapear.
     * @return Una nueva instancia de Jugador con los datos mapeados.
     */
    public static Jugador toJugador(JugadorDTO jugadorDTO){
        // Mapea TarjetaDTO a Tarjeta
        Tarjeta tarjeta = toTarjeta(jugadorDTO.getTarjeta());
        
        // La ruta de la fotoPerfil debe ser un String. Como el DTO tiene Image, 
        // asumimos que el DTO viene de un jugador remoto y no necesitamos la ruta de archivo local.
        // Usamos una cadena vacía "" o nula para evitar errores.
        String rutaFotoPerfil = ""; 

        return new Jugador(
            jugadorDTO.getNickname(), 
            rutaFotoPerfil, 
            jugadorDTO.getPuntos(), 
            tarjeta
        );
    }
    
    /**
     * Convierte un Objeto de Transferencia de Datos de Tarjeta (TarjetaDTO) a 
     * un objeto del Modelo de Juego (Tarjeta).
     *
     * @param tarjetaDTO El objeto TarjetaDTO a mapear.
     * @return Una nueva instancia de Tarjeta.
     */
    public static Tarjeta toTarjeta(TarjetaDTO tarjetaDTO) {
        // Asegúrate de que Tarjeta tenga un constructor que acepte Map<Point, Integer> y Map<Point, Boolean>
        Map<Point, Integer> cartas = tarjetaDTO.getCartas();
        Map<Point, Boolean> fichas = tarjetaDTO.getFichas();
        
        // Asumiendo que Tarjeta tiene un constructor para inicializar la tarjeta y las fichas.
        return new Tarjeta(cartas); 
    }
}
