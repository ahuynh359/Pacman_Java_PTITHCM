package pacman.itf;

import pacman.entities.PacGum;
import pacman.entities.SuperPacGum;
import pacman.entities.ghosts.Ghost;


public interface Notify {
    void registerObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObserverPacGumEaten(PacGum pg);

    void notifyObserverSuperPacGumEaten(SuperPacGum spg);

    void notifyObserverGhostCollision(Ghost gh);
}
