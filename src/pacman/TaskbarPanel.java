package pacman;

import pacman.entities.PacGum;
import pacman.entities.SuperPacGum;
import pacman.entities.ghosts.Ghost;
import pacman.entities.ghosts.ghostState.FrightenedMode;
import pacman.itf.Observer;
import pacman.ultis.LoadFont;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class TaskbarPanel extends JPanel implements Observer {
    private int width;
    private int height;
    private Font font;
    private static int score = 0;
    private BufferedImage pacman;
    private int lives = 3;


    public TaskbarPanel(int width, int height) {

        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width, height));

        font = LoadFont.loadFont();
        try {
            pacman = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("res/img/pacman.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        lives = 3;


    }


    public void updateScore(int s) {
        score += s;
    }

    @Override
    public void paint(Graphics g) {

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);


        int size = 20;
        for (int i = 0; i < lives; i++) {
            g.drawImage(pacman.getSubimage(32, 0, 32, 32), 300 + +(i * 30), (height - size) / 2, size, size, null);
        }


        g.setColor(Color.WHITE);
        g.setFont(font);


        //Get height of string
        Rectangle2D r = g.getFontMetrics().getStringBounds("SCORE", g);
        g.drawString("Score: " + score, 10, (height + (int) r.getHeight()) / 2);
        repaint();


    }


    @Override
    public void updatePacGumEaten(PacGum pg) {
        updateScore(10);
    }

    @Override
    public void updateSuperPacGumEaten(SuperPacGum spg) {
        updateScore(100);

    }

    @Override
    public void updateGhostCollision(Ghost gh) {
        if (gh.getState() instanceof FrightenedMode){
            updateScore(300);

        }


    }

    public static int getScore() {
        return score;
    }

    public void setLives() {
        if (lives > 0){
            lives -= 1;
        }
    }
    public int getLives(){
        return lives;
    }
}
