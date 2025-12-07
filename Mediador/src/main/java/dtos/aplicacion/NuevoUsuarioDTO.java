package dtos.aplicacion;

import java.awt.Image;

/**
 * Objeto para transferir datos que representa la información de un jugador en
 * la sala de espera. Esta clase se utiliza para transferir datos del jugador
 * entre diferentes capas de la aplicación.
 *
 * @author norma
 */
public class NuevoUsuarioDTO {

    /**
     * El nombre de usuario o apodo único del jugador.
     */
    private String nickname;

    /**
     * Id que referencia la imagen de perfil en el repo de avatars.
     */
    private int idAvatarSeleccionado;

    /**
     * Valor para identificar si es quien creó la sala o no.
     */
    private boolean jugadorPrincipal;

    /**
     * Constructor vacío.
     */
    public NuevoUsuarioDTO() {
    }

    /**
     * Constructor para inicializar un JugadorSalaEsperaDTO con todas sus
     * propiedades.
     *
     * @param nickname El nombre de usuario o apodo único del jugador.
     * @param idAvatarSeleccionado La imagen de perfil asociada al jugador.
     */
    public NuevoUsuarioDTO(String nickname, int idAvatarSeleccionado) {
        this.nickname = nickname;
        this.idAvatarSeleccionado = idAvatarSeleccionado;
    }

    /**
     * Obtiene si el jugador es un jugador principal o no.
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
     * Obtiene el ID numérico del avatar seleccionado.
     *
     * @return La imagen de perfil del usuario.
     */
    public int getIdAvatarSeleccionado() {
        return idAvatarSeleccionado;
    }

    /**
     * Establece el ID numérico del avatar seleccionado.
     *
     * @param idAvatarSeleccionado La nueva imagen de perfil del usuario.
     */
    public void setIdAvatarSeleccionado(int idAvatarSeleccionado) {
        this.idAvatarSeleccionado = idAvatarSeleccionado;
    }

}
