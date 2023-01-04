package pacman.entities;

import pacman.GameManager;
import pacman.Main;
import pacman.entities.ghosts.Ghost;
import pacman.itf.Notify;

import pacman.itf.Observer;
import pacman.ultis.KeyHandler;
import pacman.ultis.CheckCollision;

import java.util.ArrayList;
import java.util.List;


public class Pacman extends MovingEntity implements Notify {
    private final List<Observer> observerCollection = new ArrayList<>();
    private int lives = 3;

    public Pacman(int xPos, int yPos) {
        super(xPos, yPos, 32, 2, "pacman.png", 4, 0.2f);
    }

    public void input(KeyHandler k) {
        int newXSpeed = 0;
        int newYSpeed = 0;
        if (!onTheGrid()) return;
        if (!onGameplayWindow()) return;
        if (k.kLeft.isPressed && xSpeed >= 0 && !CheckCollision.checkWallCollision(this, -speed, 0)) {
            newXSpeed = -speed;
        }
        if (k.kRight.isPressed && xSpeed <= 0 && !CheckCollision.checkWallCollision(this, speed, 0)) {
            newXSpeed = speed;
        }
        if (k.kUp.isPressed && ySpeed >= 0 && !CheckCollision.checkWallCollision(this, 0, -speed)) {
            newYSpeed = -speed;
        }
        if (k.kDown.isPressed && ySpeed <= 0 && !CheckCollision.checkWallCollision(this, 0, speed)) {
            newYSpeed = speed;
        }


        if (newXSpeed == 0 && newYSpeed == 0) return;


        //Check duong cheo

        if (Math.abs(newXSpeed) != Math.abs(newYSpeed)) {
            xSpeed = newXSpeed;
            ySpeed = newYSpeed;
        } else {
            if (xSpeed != 0) {
                xSpeed = 0;
                ySpeed = newYSpeed;
            } else {
                xSpeed = newXSpeed;
                ySpeed = 0;
            }
        }


    }

    @Override
    public void update() {

        if (!GameManager.getFirstInput()) {
            GameManager.waitIn3Sec();
            GameManager.setFirstInput(true);

        }

        if (!CheckCollision.checkWallCollision(this, xSpeed, ySpeed)) {
            updatePos();
        }

        PacGum pg = (PacGum) CheckCollision.checkCollision(this, PacGum.class);
        if (pg != null) {
            notifyObserverPacGumEaten(pg);

        }
        SuperPacGum spg = (SuperPacGum) CheckCollision.checkCollision(this, SuperPacGum.class);
        if (spg != null) {
            notifyObserverSuperPacGumEaten(spg);
        }
        Ghost gh = (Ghost) CheckCollision.checkCollision(this, Ghost.class);

        if (gh != null) {
            notifyObserverGhostCollision(gh);
        }

    }

    @Override
    public void registerObserver(Observer observer) {
        observerCollection.add(observer);

    }

    @Override
    public void removeObserver(Observer observer) {
        observerCollection.remove(observer);

    }

    @Override
    public void notifyObserverPacGumEaten(PacGum pg) {
        for (Observer o : observerCollection) {
            o.updatePacGumEaten(pg);
        }
    }

    @Override
    public void notifyObserverSuperPacGumEaten(SuperPacGum spg) {
        for (Observer o : observerCollection) {
            o.updateSuperPacGumEaten(spg);
        }
    }

    @Override
    public void notifyObserverGhostCollision(Ghost gh) {
        for (Observer o : observerCollection) {
            o.updateGhostCollision(gh);
        }
    }

    public void setLives() {
        if (lives > 1)
            lives -= 1;
    }

    public int getLives() {
        return lives;
    }

    @Override
    public void reset() {
        super.reset();

    }

}
