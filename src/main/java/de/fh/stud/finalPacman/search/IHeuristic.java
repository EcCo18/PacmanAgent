package de.fh.stud.finalPacman.search;

import de.fh.stud.finalPacman.Coordinates;

public interface IHeuristic {

    double getHeuristicValue(Coordinates currentPosition, Coordinates targetPosition);
}
