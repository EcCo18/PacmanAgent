package de.fh.stud.finalPacman.comparators;

import de.fh.stud.finalPacman.search.IHeuristic;
import de.fh.stud.finalPacman.Coordinates;

import java.util.Comparator;

public interface IHeuristicComparator extends Comparator<Coordinates> {

    IHeuristic getHeuristic();
}
