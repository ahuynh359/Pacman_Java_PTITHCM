package pacman;

import pacman.ultis.Audio.Audio;
import pacman.ultis.KeyHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class GamePlayPanel extends JPanel implements Runnable {

    public static int width;
    public static int height;

    private Thread thread;
    private Image backgroundImg;
    private static GameManager gameManager;
    private BufferedImage bufferedImage;
    private Graphics2D g;

    private KeyHandler k;
    private static boolean running;
    private boolean start = false;

    public GamePlayPanel(int width, int height) {
        GamePlayPanel.width = width;
        GamePlayPanel.height = height;
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        requestFocus();




        /*
        try {
            audio.setFile(Objects.requireNonNull(getClass().getClassLoader().getResource("res/sound/doremon.wav")).toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        audio.play();
        */
        try {
            backgroundImg = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("res/img/background.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void update() {
        gameManager.update();
    }

    public void init() {
        running = true;

        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g = (Graphics2D) bufferedImage.getGraphics();
        gameManager = new GameManager();

        k = new KeyHandler(this);

    }

    @Override
    public void addNotify() {
        super.addNotify();
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }


    public void draw() {


        Graphics g2 = this.getGraphics();
        g2.drawImage(bufferedImage, 0, 0, width, height, null);
        g2.dispose();


    }

    public void render() {
        if (g != null) {
            g.drawImage(backgroundImg, 0, 0, width, height, null);
            gameManager.draw(g);


        }
    }

    public void input() {
        gameManager.input(k);
    }


    @Override
    public void run() {


        init();
        final double FPS = 60.0;
        final double TBU = 1e9 / FPS; //Time before update
        final int MUBR = 5; // Must update before render
        double startTime = System.nanoTime();

        while (running) {
            k.kLeft.keyState();
            double now = System.nanoTime();
            int updateCount = 0;
            while ((now - startTime > TBU) && (updateCount < MUBR)) {
                input();
                update();
                startTime += TBU;
                updateCount++;
            }
            if (now - startTime > TBU) {
                startTime = now - TBU;
            }

            render();
            draw();
            try {


                Thread.sleep(1);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

    }

    public static void setRunning(boolean running) {
        GamePlayPanel.running = running;
    }
}