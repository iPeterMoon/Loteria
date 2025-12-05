package repos;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;

/**
 * Todos los avatars disponibles.
 *
 * @author norma
 */
public class RepoAvatars {

    private static final Map<Integer, Image> AVATARES_MAP = new HashMap<>();

    public static final int MAX_AVATAR_ID = 5;

    static {
        try {
            //no hay nada en esa ruta (aún)
            AVATARES_MAP.put(1, new ImageIcon("avatars/avatar1.png").getImage());
            AVATARES_MAP.put(2, new ImageIcon("avatars/avatar2.png").getImage());
            AVATARES_MAP.put(3, new ImageIcon("avatars/avatar3.png").getImage());
            AVATARES_MAP.put(4, new ImageIcon("avatars/avatar4.png").getImage());
            AVATARES_MAP.put(5, new ImageIcon("avatars/avatar5.png").getImage());
        } catch (Exception e) {
            System.err.println("Error al cargar avatares: " + e.getMessage());
        }
    }

    /**
     * Obtiene el objeto Image real a partir de su ID numérico.
     *
     * @param id El ID del avatar seleccionado (1-5).
     * @return El objeto Image correspondiente, o null si el ID no existe.
     */
    public static Image getAvatar(int id) {
        return AVATARES_MAP.get(id);
    }
}
