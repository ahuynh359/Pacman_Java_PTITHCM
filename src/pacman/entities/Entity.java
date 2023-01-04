package pacman.entities;

import java.awt.*;

public abstract class Entity {
    protected int xPos;
    protected int yPos;
    protected int size;
    protected boolean isDestroy = false;


    public Entity(int xPos, int yPos, int size) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.size = size;


    }

    public void draw(Graphics2D g){

    }
    public void update(){

    }
    public void setDestroy(){
        this.xPos = -32;
        this.yPos = -32;
        isDestroy = true;
    }

    public boolean isDestroy() {
        return isDestroy;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public int getSize() {
        return size;
    }

    public abstract Rectangle getRect();
}
