package modelo;

/**
 * Clase que representa un jugador del juego
 *
 * @author Alici
 */
public class Jugador {

    /**
     * Nombre del jugador
     */
    private String nickname;
    /**
     * Referencia (Ruta) a la foto de perfil del jugador
     */
    private String fotoPerfil;
    /**
     * Puntos ganados del jugador
     */
    private int puntos;
    /**
     * tarjeta que pertenece al jugador
     */
    private Tarjeta tarjeta;

    /**
     * Constructor vacio
     */
    public Jugador() {
    }

    /**
     * Constructor del objeto con todos los atributos del jugador
     *
     * @param nickname Nombre del jugador
     * @param fotoPerfil Foto de perfil del jugador
     * @param puntos Puntos del jugador
     * @param tarjeta Tarjeta del jugador
     */
    public Jugador(String nickname, String fotoPerfil, int puntos, Tarjeta tarjeta) {
        this.nickname = nickname;
        this.fotoPerfil = fotoPerfil;
        this.puntos = puntos;
        this.tarjeta = tarjeta;
    }

    /**
     * Método para obtener el nombre del jugador
     *
     * @return Cadena de texto con el nombre del jugador
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Método para asignar el nombre al jugador
     *
     * @param nickname Cadena de texto del nombre del jugador
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Método para obtener la ruta de la foto de perfil del jugador
     *
     * @return Cadena de texto con la ruta de la foto de perfil del jugador
     */
    public String getFotoPerfil() {
        return fotoPerfil;
    }

    /**
     * Método para asignar la ruta de la foto de perfil del jugador
     *
     * @param fotoPerfil Cadena de texto con la ruta de la foto de perfil del
     * jugador
     */
    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    /**
     * Método para obtener los puntos del jugador
     *
     * @return Entero de puntos del jugador
     */
    public int getPuntos() {
        return puntos;
    }

    /**
     * Método para asignar los puntos del jugador
     *
     * @param puntos Entero de cantidad de puntos del jugador
     */
    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    /**
     * Método para obtener la tarjeta asociada al jugador
     *
     * @return Tarjeta asociada al jugador
     */
    public Tarjeta getTarjeta() {
        return tarjeta;
    }

    /**
     * Método para asignar la tarjeta asociada al jugador
     *
     * @param tarjeta Tarjeta asociada al jugador
     */
    public void setTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }

}
