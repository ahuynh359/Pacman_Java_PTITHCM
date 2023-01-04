package pacman.entities.ghosts.ghostState;

import pacman.entities.ghosts.Ghost;
import pacman.ultis.CheckCollision;
import pacman.ultis.Utils;

public class ChaseMode extends GhostState {
    public ChaseMode(Ghost ghost) {
        super(ghost);
    }

    @Override
    public void superPacGumEaten() {
        ghost.switchFrightenedMode();
    }

    @Override
    public void timerModeOver() {
        ghost.switchScatterMode();
    }

    @Override
    public int[] getTargetPosition() {
        return ghost.getChaseTargetPosition();
    }

    @Override
    public void computeNextDir() {
        //Vi tri cua nha
        int[] position = getTargetPosition();


        int newXSpeed = 0;
        int newYSpeed = 0;

        if (!ghost.onTheGrid()) return;
        if (!ghost.onGameplayWindow()) return;

        double minDist = Double.MAX_VALUE;

        if (ghost.getxSpeed() <= 0 && !CheckCollision.checkWallCollision(ghost, -ghost.getSpeed(), 0)) {
            double distance = Utils.getDistance(ghost.getxPos() - ghost.getSpeed(), ghost.getyPos(), position[0], position[1]);
            if (distance < minDist) {
                newXSpeed = -ghost.getSpeed();
                newYSpeed = 0;
                minDist = distance;

            }
        }
        if (ghost.getxSpeed() >= 0 && !CheckCollision.checkWallCollision(ghost, ghost.getSpeed(), 0)) {
            double distance = Utils.getDistance(ghost.getxPos() + ghost.getSpeed(), ghost.getyPos(), position[0], position[1]);
            if (distance < minDist) {
                newXSpeed = ghost.getSpeed();
                newYSpeed = 0;
                minDist = distance;

            }
        }
        if (ghost.getySpeed() <= 0 && !CheckCollision.checkWallCollision(ghost, 0, -ghost.getSpeed())) {
            double distance = Utils.getDistance(ghost.getxPos(), ghost.getyPos() - ghost.getSpeed(), position[0], position[1]);
            if (distance < minDist) {
                newXSpeed = 0;
                newYSpeed = -ghost.getSpeed();
                minDist = distance;

            }
        }
        if (ghost.getySpeed() >= 0 && !CheckCollision.checkWallCollision(ghost, 0, ghost.getSpeed())) {
            double distance = Utils.getDistance(ghost.getxPos(), ghost.getyPos() + ghost.getSpeed(), position[0], position[1]);
            if (distance < minDist) {
                newXSpeed = 0;
                newYSpeed = ghost.getSpeed();
                minDist = distance;

            }
        }

        if (newXSpeed == 0 && newYSpeed == 0) return;


        if (Math.abs(newXSpeed) != Math.abs(newYSpeed)) {
            ghost.setxSpeed(newXSpeed);
            ghost.setySpeed(newYSpeed);
        } else {
            if (newXSpeed != 0) {
                ghost.setxSpeed(0);
                ghost.setySpeed(newYSpeed);
            } else {
                ghost.setxSpeed(newXSpeed);
                ghost.setySpeed(0);
            }
        }


    }
}
