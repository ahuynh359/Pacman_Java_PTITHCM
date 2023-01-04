package pacman.entities.ghosts.ghostState;

import pacman.entities.ghosts.Ghost;
import pacman.ultis.CheckCollision;
import pacman.ultis.Utils;

public abstract class GhostState {
    protected Ghost ghost;

    public GhostState(Ghost ghost) {
        this.ghost = ghost;
    }

    public void superPacGumEaten() {
    }

    public void timerModeOver() {
    }

    public void timerFrightenedModeOver() {
    }

    public void eaten() {
    }

    public void outsideHouse() {
    }

    public void insideHouse() {
    }

    public int[] getTargetPosition() {
        return new int[2];
    }

    public abstract void computeNextDir();


}
