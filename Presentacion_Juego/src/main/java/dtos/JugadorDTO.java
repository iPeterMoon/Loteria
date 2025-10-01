package dtos;

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
    private Image fotoPerfil;

    /**
     * La puntuación o puntos actuales del jugador.
     */
    private int puntos;

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
     */
    public JugadorDTO(String nickname, Image fotoPerfil, int puntos) {
        this.nickname = nickname;
        this.fotoPerfil = fotoPerfil;
        this.puntos = puntos;
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

}
