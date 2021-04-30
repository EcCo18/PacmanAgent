package de.fh.stud.p1;
import de.fh.pacman.PacmanPercept;
import de.fh.pacman.enums.PacmanAction;
import de.fh.pacman.enums.PacmanActionEffect;

enum ViewDirection {
    NORTH,
    WEST,
    EAST,
    SOUTH
}

public class PacmanController {

    private ViewDirection currentViewDirection;

    public PacmanController() {

    }

    public PacmanAction evaluateNextStep(PacmanPercept percept, PacmanActionEffect actionEffect) {
        PacmanAction nextAction = PacmanAction.WAIT;
        if(actionEffect != PacmanActionEffect.BUMPED_INTO_WALL) {
            nextAction = PacmanAction.GO_EAST;
            currentViewDirection = ViewDirection.EAST;
        }
        if(actionEffect == PacmanActionEffect.BUMPED_INTO_WALL) {
            nextAction = turnLeft();
        }
        return nextAction;
    }

    private PacmanAction turnLeft() {
        return null;
    }
}
