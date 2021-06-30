package de.fh.stud.finalPacman.search.searchTypes;

import de.fh.pacman.enums.PacmanAction;
import de.fh.pacman.enums.PacmanTileType;
import de.fh.stud.finalPacman.Coordinates;
import de.fh.stud.finalPacman.search.Search;
import de.fh.stud.finalPacman.pacman.Pacman;

public class WidthSearch extends Search {

    private int maximumDepth;

    public WidthSearch(PacmanTileType[][] currentWorld, Pacman pacman) {

        this(-1, currentWorld, pacman);
    }

    public WidthSearch(int maximumDepth, PacmanTileType[][] currentWorld, Pacman pacman) {

        super(currentWorld, pacman);
        this.maximumDepth = maximumDepth;
    }

    @Override
    public PacmanAction findNextStepTo(Coordinates coordinates) {
        return null;
    }


}
