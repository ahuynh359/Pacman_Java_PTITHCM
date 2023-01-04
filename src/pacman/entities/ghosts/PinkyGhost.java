package pacman.entities.ghosts;

import pacman.GameManager;
import pacman.ultis.Utils;

public class PinkyGhost extends Ghost{
    public PinkyGhost(int xPos, int yPos) {
        super(xPos, yPos, "pinky.png",1);
    }


    //2 o ke tu o cua pacman
    @Override
    public int[] getChaseTargetPosition() {
        int[] position = new int[2];
        int[] pacmanFacingPosition = Utils.getPointDistanceDirection(GameManager.getPacman().getxPos(), GameManager.getPacman().getyPos(), 64,
                Utils.directionConverter(GameManager.getPacman().getDirection()));
        position[0] = pacmanFacingPosition[0];
        position[1] = pacmanFacingPosition[1];
        return position;
    }


    //O tren cung ben trai
    @Override
    public int[] getScatterTargetPosition() {
        int[] position = new int[2];
        position[0] = 0;
        position[1] = 0;
        return position;
    }
}
