package modelo;

import dtos.aplicacion.MensajeDTO;
import enums.TipoMensajePantalla;
import util.Subject;

/**
 * Clase que actúa como Sujeto (Subject) en el patrón Observador, responsable de
 * gestionar el estado del mensaje actual que debe ser mostrado en la interfaz
 * de usuario (UI).
 *
 * Almacena los datos del mensaje (título, cuerpo, éxito y tipo) y notifica a
 * los observadores (normalmente un componente de diálogo o notificación) para
 * que muestren el mensaje actualizado.
 *
 * @author Alici
 */
public class MensajeSubject extends Subject {

    /**
     * Título del mensaje que se mostrará al usuario.
     */
    private String titulo;

    /**
     * Cuerpo detallado del mensaje.
     */
    private String mensaje;

    /**
     * Indica si la operación que generó el mensaje fue exitosa (true) o si fue
     * un error/validación (false).
     */
    private boolean fueExitoso;

    /**
     * Tipo de mensaje, utilizado para categorizar el mensaje y posiblemente
     * determinar dónde y cómo se debe mostrar en la UI.
     */
    private TipoMensajePantalla tipoMensaje;

    /**
     * Obtiene el cuerpo detallado del mensaje.
     *
     * @return El cuerpo del mensaje.
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Verifica si la operación asociada al mensaje fue exitosa.
     *
     * @return true si fue exitosa, false en caso contrario.
     */
    public boolean isExitoso() {
        return fueExitoso;
    }

    /**
     * Obtiene el título del mensaje.
     *
     * @return El título del mensaje.
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Obtiene el tipo de mensaje.
     *
     * @return El TipoMensajePantalla.
     */
    public TipoMensajePantalla getTipoMensaje() {
        return tipoMensaje;
    }

    /**
     * Actualiza todos los campos del mensaje con los datos proporcionados por
     * un objeto MensajeDTO y notifica a todos los observadores registrados para
     * que procesen y muestren el nuevo mensaje.
     *
     * @param mensajeDTO El DTO que contiene el nuevo estado del mensaje.
     */
    public void actualizarMensaje(MensajeDTO mensajeDTO) {
        titulo = mensajeDTO.getTitulo();
        mensaje = mensajeDTO.getMensaje();
        fueExitoso = mensajeDTO.isFueExitoso();
        tipoMensaje = mensajeDTO.getTipoMensaje();
        notifyAllObservers();
    }
}
