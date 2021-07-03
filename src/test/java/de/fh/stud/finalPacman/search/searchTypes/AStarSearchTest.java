package de.fh.stud.finalPacman.search.searchTypes;

import de.fh.pacman.enums.PacmanTileType;
import de.fh.stud.finalPacman.Coordinates;
import de.fh.stud.finalPacman.exceptions.NotFoundException;
import de.fh.stud.finalPacman.pacman.Pacman;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AStarSearchTest {

    private AStarSearch aStarSearch;
    private PacmanTileType[][] startWorld;
    private Pacman pacman;

    @BeforeEach
    public void setUp() {

        startWorld = new PacmanTileType[5][4];
        pacman = new Pacman(new Coordinates(1, 1));

        //set up world
        //first line walls y=0 - - - - -
        startWorld[0][0] = PacmanTileType.WALL;
        startWorld[1][0] = PacmanTileType.WALL;
        startWorld[2][0] = PacmanTileType.WALL;
        startWorld[3][0] = PacmanTileType.WALL;
        startWorld[4][0] = PacmanTileType.WALL;
        //second lane walls x=0
        //-
        //-
        //-
        //- - - - -
        startWorld[0][1] = PacmanTileType.WALL;
        startWorld[0][2] = PacmanTileType.WALL;
        startWorld[0][3] = PacmanTileType.WALL;
        //third lane walls x=4
        //-       -
        //-       -
        //-       -
        //- - - - -
        startWorld[4][1] = PacmanTileType.WALL;
        startWorld[4][2] = PacmanTileType.WALL;
        startWorld[4][3] = PacmanTileType.WALL;
        //fourth lane y=3
        //- - - - -
        //-       -
        //-       -
        //- - - - -
        startWorld[1][3] = PacmanTileType.WALL;
        startWorld[2][3] = PacmanTileType.WALL;
        startWorld[3][3] = PacmanTileType.WALL;
        //pacman
        //- - - - -
        //-       -
        //- O     -
        //- - - - -
        startWorld[1][1] = PacmanTileType.PACMAN;
        //place dots
        //- - - - -
        //-       -
        //- O     -
        //- - - - -
        startWorld[2][1] = PacmanTileType.DOT;
        startWorld[3][1] = PacmanTileType.DOT;
        startWorld[1][2] = PacmanTileType.DOT;
        startWorld[2][2] = PacmanTileType.DOT;
        startWorld[3][2] = PacmanTileType.DOT;

        aStarSearch = new AStarSearch(startWorld, pacman);
    }

    @Test
    public void shouldThrowNotFoundException() {

        Coordinates coordinatesDestination = new Coordinates(0, 0);

        assertThrows(NotFoundException.class, () -> aStarSearch.findPathTo(coordinatesDestination));
    }
}