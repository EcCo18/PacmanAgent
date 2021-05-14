package de.fh.stud.p3;

import de.fh.kiServer.util.Util;
import de.fh.pacman.enums.PacmanTileType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SucheTest
{
    private Suche suche;
    private Node startNode;
    private PacmanTileType[][] startWorld;
    private Coordinates startPos;

    @BeforeEach
    public void setUp() {
        startWorld = new PacmanTileType[4][4];
        startPos = new Coordinates(1, 1);
        startNode = new Node(startWorld, startPos);
        suche = new Suche(startNode, "");

        //set up world
        //first line walls y=0
        startWorld[0][0] = PacmanTileType.WALL;
        startWorld[1][0] = PacmanTileType.WALL;
        startWorld[2][0] = PacmanTileType.WALL;
        startWorld[3][0] = PacmanTileType.WALL;
        //second lane walls x=0
        startWorld[0][1] = PacmanTileType.WALL;
        startWorld[0][2] = PacmanTileType.WALL;
        startWorld[0][3] = PacmanTileType.WALL;
        //third lane walls x=3
        startWorld[3][1] = PacmanTileType.WALL;
        startWorld[3][2] = PacmanTileType.WALL;
        startWorld[3][3] = PacmanTileType.WALL;
        //fourth lane y=3
        startWorld[1][3] = PacmanTileType.WALL;
        startWorld[2][3] = PacmanTileType.WALL;
        //pacman
        startWorld[1][1] = PacmanTileType.PACMAN;
        //place dots
        startWorld[2][1] = PacmanTileType.DOT;
        startWorld[1][2] = PacmanTileType.DOT;
        startWorld[2][2] = PacmanTileType.DOT;
    }

    @Test
    public void printStartWorld() {
        Util.printView(startWorld);
    }

    @Test
    public void expect_get_remainingDots() {
        assertEquals(3, suche.remainingDots(startWorld));
    }

    @Test
    public void expect_startPos_to_be_set_in_node() {
        Coordinates expectedCoordinates = new Coordinates(1, 1);
        assertEquals(expectedCoordinates, startNode.getPacPos());
    }

    @Test
    public void expect_cycle_to_be_removed() {
        //add self to path
        startNode.addToWalkedPath(startNode);
        ArrayList<Node> arrayList = new ArrayList<>();
        arrayList.add(startNode);
        suche.removeNodeIfCycle(arrayList);
        assertEquals(0, arrayList.size());
    }

    @Test
    public void expect_is_final_state() {
        PacmanTileType[][] world = startWorld;

        world[2][1] = PacmanTileType.EMPTY;
        world[1][2] = PacmanTileType.EMPTY;
        world[2][2] = PacmanTileType.EMPTY;

        Node testNode = new Node(world, null);

        assertTrue(suche.isFinalState(testNode));
    }

    @Test
    public void expect_is_not_final_state() {
        assertFalse(suche.isFinalState(startNode));
    }

    @Test
    public void expects_node_to_be_removed_when_already_expanded() {
        ArrayList<Node> expandedNodes = new ArrayList<>();
        expandedNodes.add(startNode);
        suche.removeNodeIfAlreadyExpanded(expandedNodes);
    }
}