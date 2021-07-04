package de.fh.stud.finalPacman.comparators;

import de.fh.stud.finalPacman.Coordinates;
import de.fh.stud.finalPacman.search.IHeuristic;

public class CompareCoordinatesByDepth implements IHeuristicComparator {

    public int compare(Coordinates lhs, Coordinates rhs) {

        return lhs.getDepth() - rhs.getDepth();
    }

    @Override
    public IHeuristic getHeuristic() {
        return null;
    }
}
