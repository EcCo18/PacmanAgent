package de.fh.stud.finalPacman.pacman;

import de.fh.stud.finalPacman.Coordinates;

public class Pacman {

    private Coordinates currentCoordinates;
    private int dotsRemaining;

    public Pacman(Coordinates currentCoordinates) {

        this.currentCoordinates = currentCoordinates;
    }

    public Coordinates getCurrentCoordinates() {
        return currentCoordinates;
    }

    public void setCurrentCoordinates(Coordinates currentCoordinates) {
        this.currentCoordinates = currentCoordinates;
    }
}
