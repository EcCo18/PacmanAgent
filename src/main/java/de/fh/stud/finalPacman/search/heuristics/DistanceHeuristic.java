package de.fh.stud.finalPacman.search.heuristics;

import de.fh.stud.finalPacman.Coordinates;
import de.fh.stud.finalPacman.search.IHeuristic;

public class DistanceHeuristic implements IHeuristic {

    private Coordinates targetPosition;

    public DistanceHeuristic (Coordinates targetPosition) {

        this.targetPosition = targetPosition;
    }

    @Override
    public double getHeuristicValue(Coordinates currentPosition) {

        int height = currentPosition.getPosY() - targetPosition.getPosY();
        int width = currentPosition.getPosX() - targetPosition.getPosX();

        return  Math.sqrt(height*height + width*width);
    }
}
