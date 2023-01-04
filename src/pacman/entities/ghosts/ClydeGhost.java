package pacman.entities.ghosts;

import pacman.GameManager;
import pacman.GamePlayPanel;
import pacman.itf.Observer;
import pacman.ultis.Utils;

public class ClydeGhost extends Ghost{
    public ClydeGhost(int xPos, int yPos) {
        super(xPos, yPos, "clyde.png",3);
    }

    @Override
    public int[] getChaseTargetPosition() {
        if (Utils.getDistance(this.getxPos(), this.getyPos(), GameManager.getPacman().getxPos(), GameManager.getPacman().getyPos()) >= 256) {
            int[] position = new int[2];
            position[0] = GameManager.getPacman().getxPos();
            position[1] = GameManager.getPacman().getyPos();
            return position;
        }else{
            return getScatterTargetPosition();
        }
    }

    @Override
    public int[] getScatterTargetPosition() {
        int[] position = new int[2];
        position[0] = 0;
        position[1] = GamePlayPanel.height;
        return position;
    }


}
