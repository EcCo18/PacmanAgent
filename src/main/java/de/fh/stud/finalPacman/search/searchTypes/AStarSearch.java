package de.fh.stud.finalPacman.search.searchTypes;

import de.fh.pacman.enums.PacmanTileType;
import de.fh.stud.finalPacman.comparators.CompareCoordinatesByDepthAndDistance;
import de.fh.stud.finalPacman.search.Search;
import de.fh.stud.finalPacman.pacman.Pacman;

public class AStarSearch extends Search {

    public AStarSearch(PacmanTileType[][] currentWorld, Pacman pacman) {

        super(currentWorld, pacman, new CompareCoordinatesByDepthAndDistance());
    }
}
