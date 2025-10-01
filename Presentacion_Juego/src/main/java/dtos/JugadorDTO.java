package dtos;

import java.awt.Image;
import java.io.Serializable;

/**
 * Objeto para transferir datos que representa la información de un jugador.
 * Esta clase se utiliza para transferir datos del jugador entre diferentes
 * capas de la aplicación.
 *
 * @author Alici
 */
public class JugadorDTO implements Serializable{
    private static final long serialVersionUID = 1L; // Importante para la serialización


    /**
     * El nombre de usuario o apodo único del jugador.
     */
    private String nickname;

    /**
     * La imagen de perfil asociada al jugador.
     */
    private Image fotoPerfil;

    /**
     * La puntuación o puntos actuales del jugador.
     */
    private int puntos;

    /**
     * Objeto de Transferencia de Datos de la Tarjeta asociada al jugador.
     */
    private TarjetaDTO tarjeta;

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
     */
    public JugadorDTO(String nickname, Image fotoPerfil, int puntos, TarjetaDTO tarjeta) {
        this.nickname = nickname;
        this.fotoPerfil = fotoPerfil;
        this.puntos = puntos;
        this.tarjeta = tarjeta;
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
    public Image getFotoPerfil() {
        return fotoPerfil;
    }

    /**
     * Establece la imagen de perfil del jugador.
     *
     * @param fotoPerfil La nueva imagen de perfil a establecer.
     */
    public void setFotoPerfil(Image fotoPerfil) {
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

    @Override
    public String toString() {
        return "JugadorDTO{" + "nickname=" + nickname + ", fotoPerfil=" + fotoPerfil + ", puntos=" + puntos + ", tarjeta=" + tarjeta + '}';
    }

    
}
