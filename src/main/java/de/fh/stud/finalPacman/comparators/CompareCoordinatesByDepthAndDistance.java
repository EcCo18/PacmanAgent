package de.fh.stud.finalPacman.comparators;

import de.fh.stud.finalPacman.Coordinates;
import de.fh.stud.finalPacman.search.IHeuristic;

public class CompareCoordinatesByDepthAndDistance implements IHeuristicComparator {

    public int compare(Coordinates lhs, Coordinates rhs) {

        return (int) (lhs.getDepth() - rhs.getDepth() + Math.floor(lhs.getDistance() - rhs.getDistance()));
    }

    @Override
    public IHeuristic getHeuristic() {
        return null;
    }
}
