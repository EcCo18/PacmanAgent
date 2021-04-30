package de.fh.stud.p2;

import de.fh.pacman.enums.PacmanAction;
import de.fh.pacman.enums.PacmanTileType;

import java.util.ArrayList;
import java.util.List;

public class Node
{

    private final PacmanTileType[][] currentWorld;
    private final Coordinates pacPos;

    public Node(PacmanTileType[][] currentWorld, Coordinates pacPos)
    {
        this.currentWorld = currentWorld;

        this.pacPos = pacPos;
    }

    public List<Node> expand() throws Exception
    {
        List<Node> expandedList = new ArrayList<>();

        expandedList.add(getNextNode(PacmanAction.GO_EAST));
        expandedList.add(getNextNode(PacmanAction.GO_WEST));
        expandedList.add(getNextNode(PacmanAction.GO_NORTH));
        expandedList.add(getNextNode(PacmanAction.GO_SOUTH));

        expandedList.removeIf(this::isWall);

        return expandedList;
    }

    private Node getNextNode(PacmanAction action) throws Exception
    {

        PacmanTileType[][] calculatedWorld = copyCurrentWorld();
        Coordinates newCoordinates = calculateNextCord(action);

        if(calculatedWorld[newCoordinates.getPosX()][newCoordinates.getPosY()] != PacmanTileType.WALL)
        {
            calculatedWorld[pacPos.getPosX()][pacPos.getPosY()] = PacmanTileType.EMPTY;
            calculatedWorld[newCoordinates.getPosX()][newCoordinates.getPosY()] = PacmanTileType.PACMAN;
        }

        return new Node(calculatedWorld, newCoordinates);
    }

    private Coordinates calculateNextCord(PacmanAction action) throws Exception
    {
        return switch (action)
                {
                    case GO_EAST -> new Coordinates(pacPos.getPosX() + 1, pacPos.getPosY());
                    case GO_NORTH -> new Coordinates(pacPos.getPosX(), pacPos.getPosY() + 1);
                    case GO_SOUTH -> new Coordinates(pacPos.getPosX(), pacPos.getPosY() - 1);
                    case GO_WEST -> new Coordinates(pacPos.getPosX() - 1, pacPos.getPosY());
                    default -> throw new Exception("Cant match enum");
                };
    }

    private boolean isWall(Node node)
    {
        return currentWorld[node.getPacPos().getPosX()][node.getPacPos().getPosY()] == PacmanTileType.WALL;
    }

    public PacmanTileType[][] getCurrentWorld()
    {
        return currentWorld;
    }

    public Coordinates getPacPos()
    {
        return pacPos;
    }

    public PacmanTileType[][] copyCurrentWorld()
    {
        PacmanTileType [][] copiedWorld = new PacmanTileType[this.currentWorld.length][this.currentWorld[0].length];

        for(int i=0; i<this.currentWorld.length; i++)
        {
            System.arraycopy(this.currentWorld[i], 0, copiedWorld[i], 0, this.currentWorld[i].length);
        }
        return copiedWorld;
    }
}
