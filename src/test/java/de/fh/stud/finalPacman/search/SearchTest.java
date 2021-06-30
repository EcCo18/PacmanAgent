package de.fh.stud.finalPacman.search;

import de.fh.pacman.enums.PacmanAction;
import de.fh.pacman.enums.PacmanTileType;
import de.fh.stud.finalPacman.Coordinates;
import de.fh.stud.finalPacman.exceptions.InvalidCoordinatesException;
import de.fh.stud.finalPacman.exceptions.NotFoundException;
import de.fh.stud.finalPacman.pacman.Pacman;
import de.fh.stud.finalPacman.search.searchTypes.DeepSearch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SearchTest {

    private PacmanTileType[][] startWorld;
    private Pacman pacman;
    private DeepSearch deepSearch;

    @BeforeEach
    public void setUp() {

        startWorld = new PacmanTileType[4][4];
        pacman = new Pacman(new Coordinates(1, 1));

        //set up world
        //first line walls y=0 - - - -
        startWorld[0][0] = PacmanTileType.WALL;
        startWorld[1][0] = PacmanTileType.WALL;
        startWorld[2][0] = PacmanTileType.WALL;
        startWorld[3][0] = PacmanTileType.WALL;
        //second lane walls x=0
        //-
        //-
        //-
        //- - - -
        startWorld[0][1] = PacmanTileType.WALL;
        startWorld[0][2] = PacmanTileType.WALL;
        startWorld[0][3] = PacmanTileType.WALL;
        //third lane walls x=3
        //-     -
        //-     -
        //-     -
        //- - - -
        startWorld[3][1] = PacmanTileType.WALL;
        startWorld[3][2] = PacmanTileType.WALL;
        startWorld[3][3] = PacmanTileType.WALL;
        //fourth lane y=3
        //- - - -
        //-     -
        //-     -
        //- - - -
        startWorld[1][3] = PacmanTileType.WALL;
        startWorld[2][3] = PacmanTileType.WALL;
        //pacman
        //- - - -
        //-     -
        //- O   -
        //- - - -
        startWorld[1][1] = PacmanTileType.PACMAN;
        //place dots
        //- - - -
        //- * * -
        //- O * -
        //- - - -
        startWorld[2][1] = PacmanTileType.DOT;
        startWorld[1][2] = PacmanTileType.DOT;
        startWorld[2][2] = PacmanTileType.DOT;

        deepSearch = new DeepSearch(startWorld, pacman);
    }

    @Test
    public void shouldBuildWallMap() {

        boolean[][] wallMap = new boolean[4][4];

        //first line walls y=0
        wallMap[0][0] = true;
        wallMap[1][0] = true;
        wallMap[2][0] = true;
        wallMap[3][0] = true;
        //second lane walls x=0
        wallMap[0][1] = true;
        wallMap[0][2] = true;
        wallMap[0][3] = true;
        //third lane walls x=3
        wallMap[3][1] = true;
        wallMap[3][2] = true;
        wallMap[3][3] = true;
        //fourth lane y=3
        wallMap[1][3] = true;
        wallMap[2][3] = true;

        assertArrayEquals(wallMap, deepSearch.getWallMap());
    }

    @Test
    public void shouldFindNextDot() throws NotFoundException {

        startWorld[1][2] = PacmanTileType.EMPTY;
        startWorld[2][1] = PacmanTileType.EMPTY;

        deepSearch.setCurrentWorld(startWorld);

        Coordinates coordinatesDot = new Coordinates(2,2);

        assertTrue(coordinatesDot.equals(deepSearch.find(PacmanTileType.DOT)));
    }

    @Test
    public void shouldMoveToNorth() throws InvalidCoordinatesException {

        Coordinates coordinates = new Coordinates(1,1);
        Coordinates expectedCoordinates = new Coordinates(1, 2);

        PacmanAction pacmanAction = PacmanAction.GO_NORTH;

        assertTrue(expectedCoordinates.equals(deepSearch.getCoordinatesAfterMove(coordinates, pacmanAction)));
    }

    @Test
    public void shouldMoveToEast() throws InvalidCoordinatesException {

        Coordinates coordinates = new Coordinates(1,1);
        Coordinates expectedCoordinates = new Coordinates(2, 1);

        PacmanAction pacmanAction = PacmanAction.GO_EAST;

        assertTrue(expectedCoordinates.equals(deepSearch.getCoordinatesAfterMove(coordinates, pacmanAction)));
    }

    @Test
    public void shouldMoveToSouth() throws InvalidCoordinatesException {

        deepSearch.getPacman().setCurrentCoordinates(new Coordinates(2, 2));
        startWorld[1][1] = PacmanTileType.DOT;
        startWorld[2][2] = PacmanTileType.PACMAN;
        deepSearch.setCurrentWorld(startWorld);

        Coordinates coordinates = new Coordinates(2,2);
        Coordinates expectedCoordinates = new Coordinates(2, 1);

        PacmanAction pacmanAction = PacmanAction.GO_SOUTH;

        assertTrue(expectedCoordinates.equals(deepSearch.getCoordinatesAfterMove(coordinates, pacmanAction)));
    }

    @Test
    public void shouldMoveToWest() throws InvalidCoordinatesException {

        deepSearch.getPacman().setCurrentCoordinates(new Coordinates(1, 2));
        startWorld[1][1] = PacmanTileType.DOT;
        startWorld[1][2] = PacmanTileType.PACMAN;
        deepSearch.setCurrentWorld(startWorld);

        Coordinates coordinates = new Coordinates(2,2);
        Coordinates expectedCoordinates = new Coordinates(1, 2);

        PacmanAction pacmanAction = PacmanAction.GO_WEST;

        assertTrue(expectedCoordinates.equals(deepSearch.getCoordinatesAfterMove(coordinates, pacmanAction)));
    }

    @Test
    public void shouldFindWall() {

        assertEquals(true, deepSearch.isWallAt(new Coordinates(0, 0)));
    }

    @Test
    public void shouldNotFindWall() {

        assertEquals(false, deepSearch.isWallAt(new Coordinates(1, 1)));
    }

    @Test
    public void shouldExpandCoordinates() {

        Coordinates coordinate = new Coordinates(1, 1);
        ArrayList<Coordinates> expendedCoordinates = deepSearch.getNextCoordinates(coordinate);

        ArrayList<Coordinates> expectedCoordinates = new ArrayList<>(Arrays.asList(
                new Coordinates(1,2),
                new Coordinates(2, 1)
        ));

        assertEquals(expectedCoordinates, expendedCoordinates);
    }
}