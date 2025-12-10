/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.awt.Component;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;

/**
 *
 * @author petermoon
 */
public class AudioPlayer {

    public static void reproducirAudio(Component parent, String ruta) {
        try {
            URL url = parent.getClass().getResource(ruta);
            if (url != null) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(url);
                Clip clip = AudioSystem.getClip();
                
                clip.addLineListener(event -> {
                    if(event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                    }
                });
                clip.open(audioStream);
                clip.start();
            }
        } catch (Exception e) {
            System.out.println("[CANTADOR] Error al reproducir el audio");
            System.out.println(e.getMessage());
        }

    }
}
