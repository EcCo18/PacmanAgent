package de.fh.stud.finalPacman;

import de.fh.pacman.enums.PacmanAction;
import de.fh.pacman.enums.PacmanTileType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Node implements Comparable<Node>
{

    private final PacmanTileType[][] currentWorld;
    private final Coordinates pacPos;
    private int priority;
    private final ArrayList<Node> walkedPath;
    private int remainingDots;

    public Node(PacmanTileType[][] currentWorld, Coordinates pacPos)
    {
        this.currentWorld = currentWorld;
        this.priority = 0;
        this.pacPos = pacPos;
        this.walkedPath = new ArrayList<>();
        this.remainingDots = -1;
    }

    public Node(PacmanTileType[][] currentWorld, Coordinates pacPos, int priority)
    {
        this(currentWorld, pacPos);
        this.priority = priority;
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

        if(calculatedWorld[newCoordinates.getPosX()][newCoordinates.getPosY()] != PacmanTileType.WALL) {

            calculatedWorld[pacPos.getPosX()][pacPos.getPosY()] = PacmanTileType.EMPTY;
            calculatedWorld[newCoordinates.getPosX()][newCoordinates.getPosY()] = PacmanTileType.PACMAN;
        }

        Node newNode = new Node(calculatedWorld, newCoordinates, this.priority);
        newNode.setWalkedPath(this.walkedPath);

        return newNode;
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

    private boolean isWall(Node node) {

        PacmanTileType tile = currentWorld[node.getPacPos().getPosX()][node.getPacPos().getPosY()];
        node.remainingDots = tile == PacmanTileType.DOT ? this.remainingDots -1 : this.remainingDots;

        return  tile == PacmanTileType.WALL;
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

    public int getPriority()
    {
        return priority;
    }

    public void setPriority(int priority)
    {
        this.priority = priority;
    }

    public ArrayList<Node> getWalkedPath() {

        return walkedPath;
    }

    public void addToWalkedPath(Node node) {

        this.walkedPath.add(node);
    }

    public void setWalkedPath(ArrayList<Node> walkedPath) {

        this.walkedPath.addAll(walkedPath);
    }

    public int getRemainingDots() {

        return remainingDots;
    }

    public void setRemainingDots(int remainingDots) {

        this.remainingDots = remainingDots;
    }

    @Override
    public int compareTo(Node n) {

        return this.priority - n.getPriority();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        // return node.hashCode() == this.hashCode();
        return Arrays.deepEquals(currentWorld, node.currentWorld) && pacPos.equals(node.pacPos);
    }

    @Override
    public int hashCode()
    {
        int result = Objects.hash(pacPos);
        result = 31 * result + Arrays.deepHashCode(currentWorld);
        return result;
    }
}
