package pacman.ultis.Audio;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Objects;

public abstract class Audio {
    private Clip clip;
    private AudioInputStream sound;


    public Audio(String name) {
        try {
            try {
                sound = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("res/sound/"+name).toURI().toURL());
                clip = AudioSystem.getClip();
                clip.open(sound);


                //System.out.println(duration);
                //We need to delay it otherwise function will return
                //duration is in seconds we are converting it to milliseconds


            } catch (LineUnavailableException | URISyntaxException e) {
                throw new RuntimeException(e);
            }

        } catch (UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }
    }



    public void play() {
        clip.start();

    }



    public void stop() {
        try {
            sound.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        clip.close();
        clip.stop();
    }
}
