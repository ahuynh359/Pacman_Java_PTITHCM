package pacman.entities;

import pacman.GameManager;
import pacman.GamePlayPanel;
import pacman.Main;
import pacman.itf.Observer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public abstract class MovingEntity extends Entity {
    protected int xSpeed = 0;
    protected int ySpeed = 0;
    //0 R 1 L 2 U 3 D
    protected int direction = 0;
    protected int speed;
    protected float imgSpeed = 0.3f;
    protected int imagePerCycle;
    protected float subImg;
    protected BufferedImage sprite;
    protected int startxPos, startyPos;


    public MovingEntity(int xPos, int yPos, int size, int speed, String spriteName, int imagePerCycle, float imgSpeed) {
        super(xPos, yPos, size);
        this.speed = speed;
        this.imagePerCycle = imagePerCycle;
        this.imgSpeed = imgSpeed;
        this.subImg = 0;
        this.startxPos = xPos;
        this.startyPos = yPos;
        try {
            this.sprite = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("res/img/" + spriteName)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void update() {

        updatePos();


    }

    public void updatePos() {

        if (!(xPos == 0 && yPos == 0)) {
            xPos += xSpeed;
            yPos += ySpeed;

            if (xSpeed > 0) direction = 0;
            else if (xSpeed < 0) direction = 1;
            else if (ySpeed < 0) direction = 2;
            else if (ySpeed > 0) direction = 3;

            subImg += imgSpeed;
            if (subImg >= imagePerCycle) {
                subImg = 0;
            }
        }


        if (xPos > GamePlayPanel.width) {
            xPos = -size + speed;
        }

        if (xPos < -size + speed) {
            xPos = GamePlayPanel.width;
        }

        if (yPos < -size + speed) {
            yPos = GamePlayPanel.height;
        }
        if (yPos > GamePlayPanel.height)
            yPos = -size + speed;


    }

    @Override
    public void draw(Graphics2D g) {

        g.drawImage(sprite.getSubimage(direction * imagePerCycle * size + size * (int) subImg, 0, size, size), xPos, yPos, null);
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    public boolean onTheGrid() {
        return (xPos % 8 == 0 && yPos % 8 == 0);
    }

    public boolean onGameplayWindow() {
        return xPos > 0 && xPos < GamePlayPanel.width && yPos > 0 && yPos < GamePlayPanel.height;
    }

    public int getxSpeed() {
        return xSpeed;
    }

    public int getySpeed() {
        return ySpeed;
    }

    public int getDirection() {
        return direction;
    }

    public int getSpeed() {
        return speed;
    }

    public void setxSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public void setySpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }

    @Override
    public Rectangle getRect() {
        return new Rectangle(xPos, yPos, size, size);
    }


    public void reset() {
        this.xPos = startxPos;
        this.yPos = startyPos;
        this.direction = 0;
        this.xSpeed = 0;
        this.ySpeed = 0;
    }
}
