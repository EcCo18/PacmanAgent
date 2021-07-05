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

        ghostBusterClass = new GhostBusterClass(pacman, startWorld);
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

        assertEquals(1, resList.size());
    }

    @Test
    public void shouldNotDetectGhost() {

        startWorld[3][2] = PacmanTileType.DOT;
        ArrayList<GhostReport> resList = ghostBusterClass.searchForGhosts();

        assertEquals(0, resList.size());
    }

    @Test
    public void shouldSaveCoordinatesForGhost() {

        ArrayList<GhostReport> resList = ghostBusterClass.searchForGhosts();

        assertEquals(new Coordinates(3, 2), resList.get(0).getCurrentCoordinates());
    }

    @Test
    public void shouldSaveDistanceToGhost() {

        ArrayList<GhostReport> resList = ghostBusterClass.searchForGhosts();
        GhostReport ghostReport = resList.get(0);

        double expectedDistance = Math.sqrt(
                Math.pow(pacman.getCurrentCoordinates().getPosX() - ghostReport.getCurrentCoordinates().getPosX(), 2) +
                Math.pow(pacman.getCurrentCoordinates().getPosY() - ghostReport.getCurrentCoordinates().getPosY(), 2));

        assertEquals(expectedDistance, ghostReport.getDistanceToGhost());
    }

    @Test
    public void shouldCalcLengthRightForSnapshot() {

        assertEquals(3, ghostBusterClass.calcLengthRight());
    }

    @Test
    public void shouldCalcLengthLeftForSnapshot() {

        assertEquals(1, ghostBusterClass.calcLengthLeft());
    }

    @Test
    public void shouldCalcLengthUpForSnapshot() {

        assertEquals(2, ghostBusterClass.calcLengthUp());
    }

    @Test
    public void shouldCalcLengthDownForSnapshot() {

        assertEquals(1, ghostBusterClass.calcLengthDown());
    }

    @Test
    public void shouldCalcSnapshotHeight() {

        assertEquals(4, ghostBusterClass.calcSnapSizeHeight(
                ghostBusterClass.calcLengthUp(), ghostBusterClass.calcLengthDown()
        ));
    }

    @Test
    public void shouldCalcSnapshotWidth() {

        assertEquals(5, ghostBusterClass.calcSnapSizeWidth(
                ghostBusterClass.calcLengthLeft(), ghostBusterClass.calcLengthRight()
        ));
    }
    
    @Test
    public void shouldCreateWorldSnapshot() {

        PacmanTileType[][] expectedSnapshot = new PacmanTileType[5][4];
        //set up world
        //first line walls y=0 - - - -
        expectedSnapshot[0][0] = PacmanTileType.WALL;
        expectedSnapshot[1][0] = PacmanTileType.WALL;
        expectedSnapshot[2][0] = PacmanTileType.WALL;
        expectedSnapshot[3][0] = PacmanTileType.WALL;
        //second lane walls x=0
        //-
        //-
        //-
        //- - - -
        expectedSnapshot[0][1] = PacmanTileType.WALL;
        expectedSnapshot[0][2] = PacmanTileType.WALL;
        expectedSnapshot[0][3] = PacmanTileType.WALL;
        //fourth lane y=3
        //- - - -
        //-
        //-
        //- - - -
        expectedSnapshot[1][3] = PacmanTileType.WALL;
        expectedSnapshot[2][3] = PacmanTileType.WALL;
        expectedSnapshot[3][3] = PacmanTileType.WALL;
        //pacman
        //- - - -
        //-
        //- O
        //- - - -
        expectedSnapshot[1][1] = PacmanTileType.PACMAN;
        //place dots
        //- - - -
        //-     X
        //- O
        //- - - -
        expectedSnapshot[2][1] = PacmanTileType.DOT;
        expectedSnapshot[3][1] = PacmanTileType.DOT;
        expectedSnapshot[1][2] = PacmanTileType.DOT;
        expectedSnapshot[2][2] = PacmanTileType.DOT;
        expectedSnapshot[3][2] = PacmanTileType.GHOST;
        //third lane walls x=4
        //-       -
        //-       -
        //-       -
        //- - - - -
        expectedSnapshot[4][0] = PacmanTileType.WALL;
        expectedSnapshot[4][1] = PacmanTileType.WALL;
        expectedSnapshot[4][2] = PacmanTileType.WALL;
        expectedSnapshot[4][3] = PacmanTileType.WALL;

        // Util.printView(expectedSnapshot);

        assertArrayEquals(expectedSnapshot, ghostBusterClass.createWorldSnapshot());
    }
}