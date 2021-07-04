package de.fh.stud.finalPacman.pacman;

import de.fh.pacman.PacmanPercept;
import de.fh.stud.finalPacman.Coordinates;
import de.fh.stud.finalPacman.search.ICoordinates;

public class Pacman implements ICoordinates {

    private Coordinates currentCoordinates;

    public Pacman(Coordinates currentCoordinates) {

        this.currentCoordinates = currentCoordinates;
    }

    public Pacman(PacmanPercept percept) {

        setCurrentCoordinates(percept);
    }

    public Coordinates getCurrentCoordinates() {
        return currentCoordinates;
    }

    public void setCurrentCoordinates(Coordinates currentCoordinates) {
        this.currentCoordinates = currentCoordinates;
    }

    public void setCurrentCoordinates(PacmanPercept percept) {

        this.currentCoordinates = new Coordinates(percept.getPosX(), percept.getPosY());
    }
}
