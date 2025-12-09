package mappers;

import dtos.aplicacion.JugadorDTO;
import dtos.aplicacion.JugadorSalaEsperaDTO;
import dtos.aplicacion.TarjetaDTO;
import java.awt.Point;
import java.util.Map;
import modelo.Tarjeta;

import modelo.Jugador;
import modelo.Sala;

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
     * @param esJugadorPrincipal booleano que indica si el jugador dado es el
     * principal
     * @return Una nueva instancia de JugadorDTO con los datos mapeados.
     */
    public static JugadorDTO toDTO(Jugador jugador, boolean esJugadorPrincipal) {
        TarjetaDTO tarjetaDTO = null;
        if (jugador.getTarjeta() != null) {
            tarjetaDTO = new TarjetaDTO(jugador.getTarjeta().getCartas(), jugador.getTarjeta().getFichas());
        }
//        ImageIcon imagenIcono = new ImageIcon(jugador.getFotoPerfil());
//        Image imagenPerfil = imagenIcono.getImage();

        return new JugadorDTO(jugador.getNickname(), jugador.getFotoPerfil(), jugador.getPuntos(), tarjetaDTO, esJugadorPrincipal);
    }

    /**
     * Convierte un Objeto de Transferencia de Datos (JugadorDTO) a un objeto
     * del Modelo de Juego (Jugador). * NOTA: La ruta de la foto de perfil
     * (String) se pierde en el DTO. Aquí la inicializamos con una cadena vacía
     * o nula, ya que el DTO solo contiene el objeto Image.
     *
     * @param jugadorDTO El objeto JugadorDTO a mapear.
     * @return Una nueva instancia de Jugador con los datos mapeados.
     */
    public static Jugador toJugador(JugadorDTO jugadorDTO) {
        // Mapea TarjetaDTO a Tarjeta
        Tarjeta tarjeta = null;
        if (jugadorDTO.getTarjeta() != null) {
            tarjeta = toTarjeta(jugadorDTO.getTarjeta());
        }

        // La ruta de la fotoPerfil debe ser un String. Como el DTO tiene Image, 
        // asumimos que el DTO viene de un jugador remoto y no necesitamos la ruta de archivo local.
        // Usamos una cadena vacía "" o nula para evitar errores.
//        String rutaFotoPerfil = "";
        return new Jugador(
                jugadorDTO.getNickname(),
                jugadorDTO.getFotoPerfil(),
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
        Map<Point, Integer> cartas = tarjetaDTO.getCartas();
        Map<Point, Boolean> fichas = tarjetaDTO.getFichas();

        Tarjeta tarjeta = new Tarjeta(cartas);
        tarjeta.setFichas(fichas);

        return tarjeta;
    }

    /**
     * Convierte un JugadorDTO a un JugadorSalaEsperaDTO.
     *
     * @param jugadorDTO el objeto JugadorDTO a convertir.
     * @return una nueva instancia de JugadorSalaEsperaDTO con los datos
     * mapeados.
     */
    public static JugadorSalaEsperaDTO toSalaEsperaDTO(JugadorDTO jugadorDTO) {
        Sala sala = Sala.getInstance();

        boolean esHost = false;
        if (sala.getHost() != null) {
            esHost = sala.getHost().equals(jugadorDTO.getNickname());
        }

        Jugador jugadorReal = null;
        if (sala.getJugadorPrincipal() != null && sala.getJugadorPrincipal().getNickname().equals(jugadorDTO.getNickname())) {
            jugadorReal = sala.getJugadorPrincipal();
        } else {
            if (sala.getJugadoresSecundario() != null) {
                for (Jugador j : sala.getJugadoresSecundario()) {
                    if (j.getNickname().equals(jugadorDTO.getNickname())) {
                        jugadorReal = j;
                        break;
                    }
                }
            }
        }

        int puntos = jugadorReal != null ? jugadorReal.getPuntos() : 0;

        return new JugadorSalaEsperaDTO(
                jugadorDTO.getNickname(),
                jugadorDTO.getFotoPerfil(),
                jugadorDTO.isJugadorPrincipal(),
                puntos
        );
    }

}
