package de.fh.stud.finalPacman.comparators;

import de.fh.stud.finalPacman.Coordinates;
import de.fh.stud.finalPacman.ghosts.GhostBusterClass;
import de.fh.stud.finalPacman.search.IHeuristic;
import de.fh.stud.finalPacman.search.heuristics.GhostHeuristic;

import java.util.Comparator;

public class CompareCoordinatesWithGhostHeuristic implements IHeuristicComparator {

    private final GhostHeuristic ghostHeuristic;

    public CompareCoordinatesWithGhostHeuristic (GhostHeuristic ghostHeuristic) {

        this.ghostHeuristic = ghostHeuristic;
    }

    @Override
    public int compare(Coordinates lhs, Coordinates rhs) {
        int lhsDepth = lhs.getDepth();
        int rhsDepth = rhs.getDepth();
        int lhsGhostHeuristic = (int) Math.floor(ghostHeuristic.getHeuristicValue(lhs)) * (-1);
        int rhsGhostHeuristic = (int) Math.floor(ghostHeuristic.getHeuristicValue(rhs)) * (-1);

        int ghostValue = (lhsGhostHeuristic - rhsGhostHeuristic) * 200;
        int distanceValue = lhsDepth - rhsDepth;

        int lhsValue = lhsDepth + lhsGhostHeuristic * 180;
        int rhsValue = rhsDepth + rhsGhostHeuristic * 180;

        return lhsValue - rhsValue;

        //int returnValue = ghostValue > 0 ? ghostValue * 200 + distanceValue : distanceValue;
    }

    public IHeuristic getHeuristic() {
        return ghostHeuristic;
    }

    @Override
    public GhostBusterClass getGhostBusterClass() {
        return ghostHeuristic.getGhostBusterClass();
    }
}
