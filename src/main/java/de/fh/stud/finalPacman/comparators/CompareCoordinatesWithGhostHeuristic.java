package de.fh.stud.finalPacman.comparators;

import de.fh.stud.finalPacman.Coordinates;
import de.fh.stud.finalPacman.ghosts.GhostBusterClass;
import de.fh.stud.finalPacman.search.IHeuristic;
import de.fh.stud.finalPacman.search.heuristics.GhostHeuristic;

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

        int lhsValue = lhsDepth + lhsGhostHeuristic * 8;
        int rhsValue = rhsDepth + rhsGhostHeuristic * 8;

        return lhsValue - rhsValue;
    }

    public IHeuristic getHeuristic() {
        return ghostHeuristic;
    }

    @Override
    public GhostBusterClass getGhostBusterClass() {
        return ghostHeuristic.getGhostBusterClass();
    }
}
