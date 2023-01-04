package pacman.entities.ghosts;

import pacman.GameManager;
import pacman.GamePlayPanel;
import pacman.ultis.Utils;

public class InkyGhost extends Ghost{
    public InkyGhost(int xPos, int yPos) {
        super(xPos, yPos, "inky.png",2);
    }

    @Override
    public int[] getChaseTargetPosition() {
        int[] position = new int[2];
        int[] pacmanFacingPosition = Utils.getPointDistanceDirection(GameManager.getPacman().getxPos(), GameManager.getPacman().getyPos(), 32d, Utils.directionConverter(GameManager.getPacman().getDirection()));
        double distanceOtherGhost = Utils.getDistance(pacmanFacingPosition[0], pacmanFacingPosition[1], GameManager.getBlinkyGhost().getxPos(), GameManager.getBlinkyGhost().getyPos());
        double directionOtherGhost = Utils.getDirection(GameManager.getBlinkyGhost().getxPos(), GameManager.getBlinkyGhost().getyPos(), pacmanFacingPosition[0], pacmanFacingPosition[1]);
        int[] blinkyVectorPosition = Utils.getPointDistanceDirection(pacmanFacingPosition[0], pacmanFacingPosition[1], distanceOtherGhost, directionOtherGhost);
        position[0] = blinkyVectorPosition[0];
        position[1] = blinkyVectorPosition[1];
        return position;
    }

    @Override
    public int[] getScatterTargetPosition() {
        int[] position = new int[2];
        position[0] = GamePlayPanel.width;
        position[1] = GamePlayPanel.height;
        return position;
    }
}
