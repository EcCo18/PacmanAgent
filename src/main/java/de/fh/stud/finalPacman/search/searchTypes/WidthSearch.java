package de.fh.stud.finalPacman.search.searchTypes;

import de.fh.pacman.enums.PacmanTileType;
import de.fh.stud.finalPacman.comparators.CompareCoordinatesByDepth;
import de.fh.stud.finalPacman.search.Search;
import de.fh.stud.finalPacman.pacman.Pacman;

public class WidthSearch extends Search {

    public WidthSearch(PacmanTileType[][] currentWorld, Pacman pacman) {

        super(currentWorld, pacman, new CompareCoordinatesByDepth());
    }
}
