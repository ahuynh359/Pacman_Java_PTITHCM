package pacman;

import pacman.entities.*;
import pacman.entities.ghosts.*;
import pacman.entities.ghosts.ghostState.EatenMode;
import pacman.entities.ghosts.ghostState.FrightenedMode;
import pacman.itf.Observer;
import pacman.ultis.Audio.IntroAudio;
import pacman.ultis.Audio.PacGumEatenSound;
import pacman.ultis.Audio.PacManDead;
import pacman.ultis.Audio.Win;
import pacman.ultis.KeyHandler;
import pacman.ultis.LoadFont;
import pacman.ultis.ReadFile;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class GameManager implements Observer {

    private static List<Entity> entities;
    private static List<Ghost> ghosts;

    private List<List<String>> map;
    private static List<Wall> walls;
    private static Pacman pacman = null;
    private static PacGumEatenSound pacGumEatenSound;
    private static PacManDead pacManDeadSound;
    private static Win winSound;
    private static Ghost pinkyGhost, inkyGhost, clydeGhost, blinkyGhost;

    private static boolean firstInput = false;
    public static int startTime = 0;
    private boolean win = false;
    private boolean lose = false;
    private int totalFood = 0;

    public GameManager() {
        init();
    }

    public void init() {
        map = new ArrayList<>();
        entities = new ArrayList<>();
        walls = new ArrayList<>();
        ghosts = new ArrayList<>();
        pacGumEatenSound = new PacGumEatenSound();
        pacManDeadSound = new PacManDead();
        winSound = new Win();


        try {
            map = ReadFile.readMap(Objects.requireNonNull(getClass().getClassLoader().getResource("res/level/level.csv")).toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        int cellsPerRow = map.get(0).size();
        int cellsPerCol = map.size();


        int cellSize = 8;

        for (int i = 0; i < cellsPerCol; i++) {
            for (int j = 0; j < cellsPerRow; j++) {
                if (map.get(i).get(j).equals(".")) {
                    entities.add(new PacGum(j * cellSize, i * cellSize));
                    totalFood += 1;

                } else if (map.get(i).get(j).equals("o")) {
                    entities.add(new SuperPacGum(j * cellSize, i * cellSize));
                    totalFood += 1;
                } else if (map.get(i).get(j).equals("P")) {
                    pacman = new Pacman(j * cellSize, i * cellSize);


                    pacman.registerObserver(this);
                    pacman.registerObserver(Main.getTaskbarPanel());

                } else if (map.get(i).get(j).equals("x")) {
                    entities.add(new Wall(j * cellSize, i * cellSize));


                } else if (map.get(i).get(j).equals("-")) {
                    entities.add(new GhostHouse(j * cellSize, i * cellSize));
                } else if (map.get(i).get(j).equals("b")) {
                    blinkyGhost = new BlinkyGhost(j * cellSize, i * cellSize);


                } else if (map.get(i).get(j).equals("p")) {
                    pinkyGhost = new PinkyGhost(j * cellSize, i * cellSize);


                } else if (map.get(i).get(j).equals("i")) {
                    inkyGhost = new InkyGhost(j * cellSize, i * cellSize);


                } else if (map.get(i).get(j).equals("c")) {
                    clydeGhost = new ClydeGhost(j * cellSize, i * cellSize);


                }

            }
        }
        entities.add(pacman);
        entities.add(blinkyGhost);
        entities.add(pinkyGhost);
        entities.add(inkyGhost);
        entities.add(clydeGhost);
        for (Entity e : entities) {

            if (e instanceof Wall) {
                walls.add((Wall) e);
            } else if (e instanceof Ghost) {
                ghosts.add((Ghost) e);
            }
        }


    }

    public void input(KeyHandler k) {
        pacman.input(k);
    }

    public void update() {
        if (lose) {
            pacGumEatenSound.stop();
            pacManDeadSound.play();
        }
        if (totalFood == 0) {
            win = true;


        }
        if (win) {
            pacGumEatenSound.stop();
            winSound.play();
        }

        if (!win && !lose) {
            for (Entity e : entities) {
                if (!e.isDestroy()) e.update();


            }


        }
    }


    public void draw(Graphics2D g) {
        Font f = LoadFont.loadFont();
        f.deriveFont(10f);
        g.setFont(f);
        if (!win && !lose) {
            for (Entity e : entities) {
                if (!e.isDestroy()) e.draw(g);


            }


            if (!firstInput && (startTime % 20 < 10)) {


                g.setColor(Color.YELLOW);
                g.setStroke(new BasicStroke((float) 1.5));
                Rectangle2D r = g.getFontMetrics().getStringBounds("READY!", g);
                g.drawString("READY", (GamePlayPanel.width - (int) r.getWidth()) / 2, (GamePlayPanel.height - (int) r.getHeight()) / 2 + 37);
            }
        } else if (lose) {
            g.setColor(Color.YELLOW);
            g.setStroke(new BasicStroke((float) 1.5));
            Rectangle2D r = g.getFontMetrics().getStringBounds("LOSE!", g);
            g.drawString("LOSE", (GamePlayPanel.width - (int) r.getWidth()) / 2, (GamePlayPanel.height - (int) r.getHeight()) / 2 + 37);


        } else if (win) {
            g.setColor(Color.YELLOW);
            g.setStroke(new BasicStroke((float) 1.5));
            Rectangle2D r = g.getFontMetrics().getStringBounds("LOSE!", g);
            g.drawString("WIN", (GamePlayPanel.width - (int) r.getWidth()) / 2, (GamePlayPanel.height - (int) r.getHeight()) / 2 + 37);

        }


    }

    public static Pacman getPacman() {
        return pacman;
    }

    public static List<Wall> getWalls() {
        return walls;
    }

    public static List<Entity> getEntities() {
        return entities;
    }

    @Override
    public void updatePacGumEaten(PacGum pg) {
        pg.setDestroy();
        totalFood -= 1;
        try {
            Thread.sleep(3);
            pacGumEatenSound.play();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

    //Can bo sung them ne
    @Override
    public void updateSuperPacGumEaten(SuperPacGum spg) {
        spg.setDestroy();
        totalFood -= 1;

        //Khi an super pacgum trang thai con ma thay doi
        for (Ghost gh : ghosts) {
            gh.getState().superPacGumEaten();

        }

    }

    @Override
    public void updateGhostCollision(Ghost gh) {
        if (gh.getState() instanceof FrightenedMode) {
            gh.getState().eaten();
        } else if (!(gh.getState() instanceof EatenMode)) {

            Main.getTaskbarPanel().setLives();
            pacman.reset();
            for (Ghost g : ghosts) {
                g.reset();

            }
            if (Main.getTaskbarPanel().getLives() == 0) {
                lose = true;

            }


        }


    }


    public static Ghost getBlinkyGhost() {
        return blinkyGhost;
    }

    public static void setFirstInput(boolean b) {
        firstInput = b;
    }

    public static boolean getFirstInput() {
        return firstInput;
    }

    public static int getLives() {
        return pacman.getLives();

    }

    public static void waitIn3Sec() {

        try {

            IntroAudio i = new IntroAudio();

            i.play();
            Thread.sleep(4800);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }


}
