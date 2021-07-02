package de.fh.stud.finalPacman.ghosts;

import de.fh.stud.finalPacman.Coordinates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GhostReportTest {

    private GhostReport  ghostReport;

    @BeforeEach
    public void setUp() {

        Coordinates pacmanCoordinates = new Coordinates(1,1);
        Coordinates ghostCoordinates = new Coordinates(2, 4);

        ghostReport = new GhostReport(ghostCoordinates, pacmanCoordinates);
    }

    @Test
    public void shouldCalculateDistance() {

        double expectedRes = Math.sqrt(Math.pow(ghostReport.getPacmanCoordinates().getPosY() - ghostReport.getGhostCoordinates().getPosY(), 2) +
                Math.pow(ghostReport.getPacmanCoordinates().getPosX() - ghostReport.getGhostCoordinates().getPosX(), 2));

        assertEquals(expectedRes, ghostReport.getDistanceToGhost());
    }
}