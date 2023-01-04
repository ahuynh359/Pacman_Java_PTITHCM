package pacman.ultis.Audio;

public class PacGumEatenSound extends Audio{
    public PacGumEatenSound() {
        super("doremon.wav");
    }

    @Override
    public void play() {
        super.play();
        System.out.println("Play ne");
    }
}
