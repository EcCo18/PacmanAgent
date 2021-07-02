package de.fh.stud.finalPacman.ghosts;

import de.fh.pacman.enums.PacmanTileType;
import de.fh.stud.finalPacman.Coordinates;
import de.fh.stud.finalPacman.pacman.Pacman;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class GhostBusterClassTest {

    private GhostBusterClass ghostBusterClass;
    private PacmanTileType[][] startWorld;
    private Pacman pacman;

    @BeforeEach
    public void setUp() {

        startWorld = setUpWorld();
        pacman = new Pacman(new Coordinates(1, 1));
    }

    public static PacmanTileType[][] setUpWorld() {

        PacmanTileType[][] generatedWorld = new PacmanTileType[5][4];

        //set up world
        //first line walls y=0 - - - - -
        generatedWorld[0][0] = PacmanTileType.WALL;
        generatedWorld[1][0] = PacmanTileType.WALL;
        generatedWorld[2][0] = PacmanTileType.WALL;
        generatedWorld[3][0] = PacmanTileType.WALL;
        generatedWorld[4][0] = PacmanTileType.WALL;
        //second lane walls x=0
        //-
        //-
        //-
        //- - - - -
        generatedWorld[0][1] = PacmanTileType.WALL;
        generatedWorld[0][2] = PacmanTileType.WALL;
        generatedWorld[0][3] = PacmanTileType.WALL;
        //third lane walls x=4
        //-       -
        //-       -
        //-       -
        //- - - - -
        generatedWorld[4][1] = PacmanTileType.WALL;
        generatedWorld[4][2] = PacmanTileType.WALL;
        generatedWorld[4][3] = PacmanTileType.WALL;
        //fourth lane y=3
        //- - - - -
        //-       -
        //-       -
        //- - - - -
        generatedWorld[1][3] = PacmanTileType.WALL;
        generatedWorld[2][3] = PacmanTileType.WALL;
        generatedWorld[3][3] = PacmanTileType.WALL;
        //pacman
        //- - - - -
        //-       -
        //- O     -
        //- - - - -
        generatedWorld[1][1] = PacmanTileType.PACMAN;
        //place dots
        //- - - - -
        //-     X -
        //- O     -
        //- - - - -
        generatedWorld[2][1] = PacmanTileType.DOT;
        generatedWorld[3][1] = PacmanTileType.DOT;
        generatedWorld[1][2] = PacmanTileType.DOT;
        generatedWorld[2][2] = PacmanTileType.DOT;
        generatedWorld[3][2] = PacmanTileType.GHOST;

        return generatedWorld;
    }

    @Test
    public void shouldDetectGhost()  {

        ArrayList<GhostReport> resList = ghostBusterClass.searchForGhosts();

        assertNotEquals(0, resList.size());
    }

    @Test
    public void shouldNotDetectGhost() {

        //startWorld[][]
    }
}