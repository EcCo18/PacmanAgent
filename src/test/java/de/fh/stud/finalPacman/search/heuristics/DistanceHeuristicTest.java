package de.fh.stud.finalPacman.search.heuristics;

import de.fh.stud.finalPacman.Coordinates;
import de.fh.stud.finalPacman.search.IHeuristic;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DistanceHeuristicTest {

    @Test
    public void shouldCalculateDistanceToGoal() {

        Coordinates coordinatesStart = new Coordinates(1, 1);
        Coordinates coordinatesDestination = new Coordinates(2, 4);
        IHeuristic distanceHeuristic = new DistanceHeuristic();

        double expectedRes = Math.sqrt(Math.pow(coordinatesStart.getPosY() - coordinatesDestination.getPosY(), 2) +
                Math.pow(coordinatesStart.getPosX() - coordinatesDestination.getPosX(), 2));

        assertEquals(expectedRes, distanceHeuristic.getHeuristicValue(coordinatesStart, coordinatesDestination));
    }
}