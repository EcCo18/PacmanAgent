package de.fh.stud.finalPacman.comparators;

import de.fh.stud.finalPacman.Coordinates;
import de.fh.stud.finalPacman.search.IHeuristic;
import de.fh.stud.finalPacman.search.heuristics.GhostHeuristic;

import java.util.Comparator;

public class CompareCoordinatesWithGhostHeuristic implements IHeuristicComparator {

    private GhostHeuristic ghostHeuristic;

    public CompareCoordinatesWithGhostHeuristic (GhostHeuristic ghostHeuristic) {

        this.ghostHeuristic = ghostHeuristic;
    }

    public int compare(Coordinates lhs, Coordinates rhs) {

        return (int) (lhs.getDepth() - rhs.getDepth() + Math.floor(lhs.getDistance() - rhs.getDistance())) + (int) Math.floor(ghostHeuristic.getHeuristicValue(lhs) - ghostHeuristic.getHeuristicValue(rhs));
    }

    public IHeuristic getHeuristic() {
        return ghostHeuristic;
    }
}
