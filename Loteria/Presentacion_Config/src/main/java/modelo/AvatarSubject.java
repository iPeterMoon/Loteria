package modelo;

import util.Subject;

/**
 *
 * @author Alici
 */
public class AvatarSubject extends Subject {

    private int numeroAvatar = 1;
    private final int MAX_AVATARES = 10;

    public void moverIzquierda() {
        if (numeroAvatar - 1 >= 1) {
            numeroAvatar -= 1;
        } else {
            numeroAvatar = MAX_AVATARES;
        }
        this.notifyAllObservers();
    }

    public void moverDerecha() {
        if (numeroAvatar + 1 <= MAX_AVATARES) {
            numeroAvatar += 1;
        } else {
            numeroAvatar = 1;
        }
        this.notifyAllObservers();
    }

    public int getNumeroAvatar() {
        return numeroAvatar;
    }
}
