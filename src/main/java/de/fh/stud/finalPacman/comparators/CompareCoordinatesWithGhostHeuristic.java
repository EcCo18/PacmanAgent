package de.fh.stud.finalPacman.comparators;

import de.fh.stud.finalPacman.Coordinates;
import de.fh.stud.finalPacman.search.IHeuristic;

import java.util.Comparator;

public class CompareCoordinatesWithGhostHeuristic implements Comparator<Coordinates> {

    private IHeuristic ghostHeuristic;

    public CompareCoordinatesWithGhostHeuristic (IHeuristic ghostHeuristic) {

        this.ghostHeuristic = ghostHeuristic;
    }

    public int compare(Coordinates lhs, Coordinates rhs) {

        return (int) Math.floor(ghostHeuristic.getHeuristicValue(lhs) - ghostHeuristic.getHeuristicValue(rhs));
    }
}
