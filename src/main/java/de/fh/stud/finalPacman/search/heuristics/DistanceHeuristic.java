package de.fh.stud.finalPacman.search.heuristics;

import de.fh.stud.finalPacman.Coordinates;
import de.fh.stud.finalPacman.search.IHeuristic;

public class DistanceHeuristic implements IHeuristic {

    @Override
    public double getHeuristicValue(Coordinates currentPosition, Coordinates targetPosition) {

        int height = currentPosition.getPosY() - targetPosition.getPosY();
        int width = currentPosition.getPosX() - targetPosition.getPosX();

        return  Math.sqrt(height*height + width*width);
    }
}
