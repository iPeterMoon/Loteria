package dtos.aplicacion;

import java.awt.Image;

/**
 * Objeto para transferir datos que representa la información de un jugador.
 * Esta clase se utiliza para transferir datos del jugador entre diferentes
 * capas de la aplicación.
 *
 * @author Alici
 */
public class JugadorDTO {

    /**
     * El nombre de usuario o apodo único del jugador.
     */
    private String nickname;

    /**
     * La imagen de perfil asociada al jugador.
     */
    private int fotoPerfil;

    /**
     * La puntuación o puntos actuales del jugador.
     */
    private int puntos;

    /**
     * Objeto de Transferencia de Datos de la Tarjeta asociada al jugador.
     */
    private TarjetaDTO tarjeta;

    private boolean jugadorPrincipal;

    /**
     * Constructor por defecto para JugadorDTO.
     */
    public JugadorDTO() {
    }

    /**
     * Constructor para inicializar un JugadorDTO con todas sus propiedades.
     *
     * @param nickname El nombre de usuario o apodo único del jugador.
     * @param fotoPerfil La imagen de perfil asociada al jugador.
     * @param puntos La puntuación o puntos actuales del jugador.
     * @param tarjeta El objeto TarjetaDTO asociado al jugador.
     * @param jugadorPrincipal Boolean que indica si el jugador es un jugador
     * principal o no
     */
    public JugadorDTO(String nickname, int fotoPerfil, int puntos, TarjetaDTO tarjeta, boolean jugadorPrincipal) {
        this.nickname = nickname;
        this.fotoPerfil = fotoPerfil;
        this.puntos = puntos;
        this.tarjeta = tarjeta;
        this.jugadorPrincipal = jugadorPrincipal;
    }

    /**
     * Obtiene si el jugador es un jugador principal o no
     *
     * @return boolean indicando si el jugador es principal o no
     */
    public boolean isJugadorPrincipal() {
        return jugadorPrincipal;
    }

    /**
     * Asigna si el jugador es un jugador principal o no.
     *
     * @param esJugadorPrincipal Boolean indicando si el jugador es principal o
     * no
     */
    public void setJugadorPrincipal(boolean esJugadorPrincipal) {
        this.jugadorPrincipal = esJugadorPrincipal;
    }

    /**
     * Obtiene el apodo del jugador.
     *
     * @return El apodo.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Establece el apodo del jugador.
     *
     * @param nickname El nuevo apodo a establecer.
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Obtiene la imagen de perfil del jugador.
     *
     * @return La imagen de perfil (objeto Image).
     */
    public int getFotoPerfil() {
        return fotoPerfil;
    }

    /**
     * Establece la imagen de perfil del jugador.
     *
     * @param fotoPerfil La nueva imagen de perfil a establecer.
     */
    public void setFotoPerfil(int fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    /**
     * Obtiene los puntos/puntuación del jugador.
     *
     * @return Los puntos actuales.
     */
    public int getPuntos() {
        return puntos;
    }

    /**
     * Establece los puntos/puntuación del jugador.
     *
     * @param puntos El nuevo valor de puntos a establecer.
     */
    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    /**
     * Obtiene el objeto TarjetaDTO asociado al jugador.
     *
     * @return El objeto TarjetaDTO.
     */
    public TarjetaDTO getTarjeta() {
        return tarjeta;
    }

    /**
     * Establece el objeto TarjetaDTO asociado al jugador.
     *
     * @param tarjeta El nuevo objeto TarjetaDTO a establecer.
     */
    public void setTarjeta(TarjetaDTO tarjeta) {
        this.tarjeta = tarjeta;
    }

}
