package de.fh.stud.finalPacman.comparators;

import de.fh.stud.finalPacman.Coordinates;

import java.util.Comparator;

public class CompareCoordinatesByDepth implements Comparator<Coordinates> {

    public int compare(Coordinates lhs, Coordinates rhs) {

        return lhs.getDepth() - rhs.getDepth();
    }
}
