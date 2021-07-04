package de.fh.stud.finalPacman.search.heuristics;

import de.fh.pacman.enums.PacmanTileType;
import de.fh.stud.finalPacman.Coordinates;
import de.fh.stud.finalPacman.deadEnd.DeadEndDetector;
import de.fh.stud.finalPacman.pacman.Pacman;
import de.fh.stud.finalPacman.search.IHeuristic;

public class DeadEndHeuristic implements IHeuristic {

    DeadEndDetector deadEndDetector;

    public DeadEndHeuristic (PacmanTileType[][] currentWorld, Pacman pacman) {

        this.deadEndDetector = new DeadEndDetector(currentWorld, pacman);
    }

    @Override
    public double getHeuristicValue(Coordinates fieldPos) {

        return deadEndDetector.checkForDeadEnd(fieldPos, 1).isDeadEnd() ? 1 : 0;
    }

    @Override
    public void refresh() { }
}
