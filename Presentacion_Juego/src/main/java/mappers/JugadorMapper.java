package mappers;

import dtos.JugadorDTO;
import dtos.TarjetaDTO;
import java.awt.Image;
import javax.swing.ImageIcon;
import modelo.JugadorSubject;
import modelo.ModeloTarjeta;
import modeloJuego.Jugador;

/**
 * Clase utilitaria (Mapper) encargada de la conversión de objetos entre el
 * modelo de juego (Jugador, Tarjeta) y los objetos de transferencia de datos o
 * sujetos de vista (JugadorDTO, JugadorSubject). * Se encarga de la lógica
 * específica de conversión, como la carga de la imagen de perfil a partir de su
 * ruta.
 *
 * @author Alici
 */
public class JugadorMapper {

    /**
     * Convierte un Objeto de Transferencia de Datos (JugadorDTO) a un objeto
     * del Patrón Observer (JugadorSubject).
     *
     * @param jugadorDTO Objeto DTO con la información del jugador.
     * @return Una nueva instancia de JugadorSubject.
     */
    public static JugadorSubject toJugadorSubject(JugadorDTO jugadorDTO) {
        ModeloTarjeta tarjetaJugador = new ModeloTarjeta(jugadorDTO.getTarjeta().getCartas());
        return new JugadorSubject(jugadorDTO.getNickname(), jugadorDTO.getPuntos(), jugadorDTO.getFotoPerfil(), tarjetaJugador, false);
    }

    /**
     * Convierte un objeto del Modelo de Juego (Jugador) a un Objeto de
     * Transferencia de Datos (JugadorDTO).
     *
     * @param jugador El objeto Jugador del modelo a mapear.
     * @return Una nueva instancia de JugadorDTO con los datos mapeados.
     */
    public static JugadorDTO toDTO(Jugador jugador) {
        TarjetaDTO tarjetaDTO = new TarjetaDTO(jugador.getTarjeta().getCartas(), jugador.getTarjeta().getFichas());
        ImageIcon imagenIcono = new ImageIcon(jugador.getFotoPerfil());
        Image imagenPerfil = imagenIcono.getImage();
        return new JugadorDTO(jugador.getNickname(), imagenPerfil, jugador.getPuntos(), tarjetaDTO);
    }
}
