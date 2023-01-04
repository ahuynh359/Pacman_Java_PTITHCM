package pacman.entities.ghosts;

import pacman.GameManager;
import pacman.GamePlayPanel;

public class BlinkyGhost extends Ghost{
    public BlinkyGhost(int xPos, int yPos) {
        super(xPos, yPos, "blinky.png",0);
    }


    //Di theo con pacman
    @Override
    public int[] getChaseTargetPosition() {
        int[] position = new int[2];
        position[0] = GameManager.getPacman().getxPos();
        position[1] = GameManager.getPacman().getyPos();
        return position;
    }

    //o goc cung ben trai

    @Override
    public int[] getScatterTargetPosition() {
        int[] position = new int[2];
        position[0] = GamePlayPanel.width;
        position[1] = 0;
        return position;
    }
}
