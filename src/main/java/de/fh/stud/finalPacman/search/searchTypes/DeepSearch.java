package de.fh.stud.finalPacman.search.searchTypes;

import de.fh.pacman.enums.PacmanAction;
import de.fh.pacman.enums.PacmanTileType;
import de.fh.stud.finalPacman.Coordinates;
import de.fh.stud.finalPacman.search.Search;
import de.fh.stud.finalPacman.pacman.Pacman;

public class DeepSearch extends Search {

    private int maximumDepth;

    public DeepSearch (PacmanTileType[][] currentWorld, Pacman pacman) {

        this(-1, currentWorld, pacman);
    }

    public DeepSearch (int maximumDepth, PacmanTileType[][] currentWorld, Pacman pacman) {

        //TODO cordsComparator
        super(currentWorld, pacman, null);
        this.maximumDepth = maximumDepth;
    }

    @Override
    public void calculateNextSteps(Coordinates coordinates) { }
}
