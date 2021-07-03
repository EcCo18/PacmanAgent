package de.fh.stud.finalPacman.search.searchTypes;

import de.fh.pacman.enums.PacmanTileType;
import de.fh.stud.finalPacman.comparators.CompareCoordinatesByDistance;
import de.fh.stud.finalPacman.pacman.Pacman;
import de.fh.stud.finalPacman.search.Search;

public class GreedySearch extends Search {

    public GreedySearch(PacmanTileType[][] currentWorld, Pacman pacman) {

        super(currentWorld, pacman, new CompareCoordinatesByDistance());
    }
}
