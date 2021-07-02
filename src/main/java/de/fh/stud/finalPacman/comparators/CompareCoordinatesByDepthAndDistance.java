package de.fh.stud.finalPacman.comparators;

import de.fh.stud.finalPacman.Coordinates;

import java.util.Comparator;

public class CompareCoordinatesByDepthAndDistance implements Comparator<Coordinates> {

    public int compare(Coordinates lhs, Coordinates rhs) {

        return (int) (lhs.getDepth() - rhs.getDepth() + Math.floor(lhs.getDistance() - rhs.getDistance()));
    }
}
