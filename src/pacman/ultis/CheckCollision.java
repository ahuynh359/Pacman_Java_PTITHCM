package pacman.ultis;

import pacman.GameManager;
import pacman.entities.Entity;
import pacman.entities.GhostHouse;
import pacman.entities.Wall;

import java.awt.*;

public class CheckCollision {
    public static boolean checkWallCollision(Entity obj, int dx, int dy) {
        Rectangle r = new Rectangle(obj.getxPos() + dx, obj.getyPos() + dy, obj.getSize(), obj.getSize());
        for (Wall w : GameManager.getWalls()) {
            if (w.getRect().intersects(r)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkWallCollisionIgnoreGhostHouse(Entity obj, int dx, int dy) {
        Rectangle r = new Rectangle(obj.getxPos() + dx, obj.getyPos() + dy, obj.getSize(), obj.getSize());
        for (Wall w : GameManager.getWalls()) {
            if (!(w instanceof GhostHouse) && w.getRect().intersects(r)) {
                return true;
            }
        }
        return false;
    }

    //Check collsion with point
    public static Entity checkCollision(Entity obj,Class<?extends Entity>collisionCheck){
        for(Entity e : GameManager.getEntities()){
            if(!e.isDestroy() && collisionCheck.isInstance(e) && e.getRect().contains(obj.getxPos() + obj.getSize()/2 , obj.getyPos() + obj.getSize()/2))
                return e;
        }
        return  null;
    }
}
