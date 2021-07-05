package de.fh.stud.finalPacman.search.searchTypes;

import de.fh.pacman.enums.PacmanTileType;
import de.fh.stud.finalPacman.comparators.CompareCoordinatesWithGhostHeuristic;
import de.fh.stud.finalPacman.pacman.Pacman;
import de.fh.stud.finalPacman.search.Search;
import de.fh.stud.finalPacman.search.heuristics.GhostHeuristic;

public class GhostSearch extends Search {

    public GhostSearch(PacmanTileType[][] currentWorld, Pacman pacman) {
        super(currentWorld, pacman, new CompareCoordinatesWithGhostHeuristic(new GhostHeuristic(pacman, currentWorld)));
    }
}
