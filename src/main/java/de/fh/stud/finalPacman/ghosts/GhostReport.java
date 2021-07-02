package de.fh.stud.finalPacman.ghosts;

import de.fh.stud.finalPacman.Coordinates;

public class GhostReport {

    private final double distanceToGhost;
    private final Coordinates ghostCoordinates;
    private final Coordinates pacmanCoordinates;

    public GhostReport(Coordinates ghostCoordinates,
                       Coordinates pacmanCoordinates) {

        this.ghostCoordinates = ghostCoordinates;
        this.pacmanCoordinates = pacmanCoordinates;
        this.distanceToGhost = calculateDistanceToGhost(pacmanCoordinates, ghostCoordinates);
    }

    private double calculateDistanceToGhost(Coordinates pacmanCoordinates,
                                                      Coordinates ghostCoordinates) {

        return Math.sqrt(Math.pow(pacmanCoordinates.getPosX() - ghostCoordinates.getPosX(), 2) +
                Math.pow(pacmanCoordinates.getPosY() - ghostCoordinates.getPosY(), 2));
    }

    public double getDistanceToGhost() {
        return distanceToGhost;
    }

    public Coordinates getGhostCoordinates() {
        return ghostCoordinates;
    }

    public Coordinates getPacmanCoordinates() {
        return pacmanCoordinates;
    }
}
