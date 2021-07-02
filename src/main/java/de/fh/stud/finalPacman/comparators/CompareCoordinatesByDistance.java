package de.fh.stud.finalPacman.comparators;

import de.fh.stud.finalPacman.Coordinates;

import java.util.Comparator;

public class CompareCoordinatesByDistance implements Comparator<Coordinates> {

    public int compare(Coordinates lhs, Coordinates rhs) {

        return (int) Math.floor(lhs.getDistance() - rhs.getDistance());
    }
}
