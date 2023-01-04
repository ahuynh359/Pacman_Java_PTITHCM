package pacman.itf;

import pacman.entities.PacGum;
import pacman.entities.SuperPacGum;
import pacman.entities.ghosts.Ghost;

public interface Observer {
     void updatePacGumEaten(PacGum pg);
     void updateSuperPacGumEaten(SuperPacGum spg);
     void updateGhostCollision(Ghost gh);
}
