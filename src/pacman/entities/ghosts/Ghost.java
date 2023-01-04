package pacman.entities.ghosts;

import pacman.GameManager;
import pacman.entities.MovingEntity;
import pacman.entities.ghosts.ghostState.*;
import pacman.itf.Observer;
import pacman.ultis.CheckCollision;
import pacman.ultis.Utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

public abstract class Ghost extends MovingEntity {


    protected final GhostState chaseMode;
    protected final GhostState scatterMode;
    protected final GhostState frightenedMode;
    protected final GhostState eatenMode;
    protected final GhostState houseMode;

    ;
    protected GhostState state;
    protected boolean isChasing = false;

    protected BufferedImage eatenSprite, frightenSprite1, frightenSprite2;
    protected int frightenedTimer = 0;
    protected int modeTimer = 0;
    protected int time;
    protected int count = 0;

    public Ghost(int xPos, int yPos, String spriteName, int time) {
        super(xPos, yPos, 32, 2, spriteName, 2, 0.1f);
        chaseMode = new ChaseMode(this);
        eatenMode = new EatenMode(this);
        scatterMode = new ScatterMode(this);
        frightenedMode = new FrightenedMode(this);
        houseMode = new HouseMode(this);
        state = houseMode;
        this.time = time;

        try {
            eatenSprite = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("res/img/ghost_eaten.png")));
            frightenSprite1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("res/img/ghost_frightened.png")));
            frightenSprite2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("res/img/ghost_frightened_2.png")));


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void draw(Graphics2D g) {
        if (state == frightenedMode) {
            //31 51 71
            if (frightenedTimer <= (60 * 5) || (frightenedTimer % 20 > 10)) {
                g.drawImage(frightenSprite1.getSubimage((int) subImg * size, 0, size, size), this.xPos, this.yPos, null);

            } else {
                g.drawImage(frightenSprite2.getSubimage((int) subImg * size, 0, size, size), this.xPos, this.yPos, null);

            }
        } else if (state == eatenMode) {
            g.drawImage(eatenSprite.getSubimage(direction * size, 0, size, size), this.xPos, this.yPos, null);
        } else {
            g.drawImage(sprite.getSubimage((int) subImg * size + direction * size * imagePerCycle, 0, size, size), this.xPos, this.yPos, null);
        }
    }


    public abstract int[] getChaseTargetPosition();

    public abstract int[] getScatterTargetPosition();

    public void switchChaseMode() {
        state = chaseMode;
    }

    public void switchScatterMode() {
        state = scatterMode;
    }

    public void switchFrightenedMode() {
        frightenedTimer = 0;
        state = frightenedMode;

    }

    public void switchEatenMode() {
        state = eatenMode;
    }

    public void switchHouseMode() {
        state = houseMode;
    }

    public void switchChaseModeOrScatterMode() {
        if (isChasing) {
            switchChaseMode();
        } else {
            switchScatterMode();
        }
    }


    @Override
    public void update() {
        if (count < (60 * time)) {
            count += 1;
        } else {
            if (!GameManager.getFirstInput()) {

                return;
            }
            if (state == frightenedMode) {
                frightenedTimer++;
                //Neu het time thi quay lai scatter hoac frighten mode
                if (frightenedTimer >= (60 * 10)) {
                    state.timerFrightenedModeOver();
                    frightenedTimer = 0;

                }
            }


            if (state == chaseMode || state == scatterMode) {
                modeTimer++;
                if ((isChasing && modeTimer >= (60 * 20)) || (!isChasing && modeTimer >= (60 * 5))) {
                    state.timerModeOver();
                    isChasing = !isChasing;
                    modeTimer = 0;
                }


            }
            //O ngay ghost house
            if (xPos == 208 && yPos == 168) {
                state.outsideHouse();


            }
            //O trong house
            if (xPos == 208 && yPos == 200) {
                state.insideHouse();

            }


            state.computeNextDir();
            updatePos();
        }
    }

    public void setState(GhostState state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return state.toString();
    }

    public GhostState getState() {
        return state;
    }

    @Override
    public void reset() {
        super.reset();
        state = houseMode;
        isChasing = false;
    }
}
