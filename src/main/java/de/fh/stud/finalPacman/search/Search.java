package de.fh.stud.finalPacman.search;

import de.fh.pacman.PacmanPercept;
import de.fh.pacman.enums.PacmanAction;
import de.fh.pacman.enums.PacmanTileType;
import de.fh.stud.finalPacman.Coordinates;
import de.fh.stud.finalPacman.comparators.CompareCoordinatesByDepth;
import de.fh.stud.finalPacman.comparators.IHeuristicComparator;
import de.fh.stud.finalPacman.exceptions.NotFoundException;
import de.fh.stud.finalPacman.exceptions.InvalidCoordinatesException;
import de.fh.stud.finalPacman.pacman.Pacman;
import de.fh.stud.finalPacman.search.heuristics.DistanceHeuristic;

import java.util.*;

public abstract class Search {

    private PacmanTileType[][] currentWorld;
    private final boolean [][] wallMap;
    private Pacman pacman;
    private Stack<PacmanAction> savedMoves;
    private final IHeuristicComparator coordinatesComparator;
    private DistanceHeuristic distanceHeuristic;
    private final IHeuristic additionalHeuristic;


    public Search(PacmanTileType[][] currentWorld, Pacman pacman, IHeuristicComparator coordinatesComparator) {

        this.pacman = pacman;
        this.currentWorld = currentWorld;
        wallMap = buildWallMap(currentWorld);
        this.savedMoves = new Stack<>();
        this.coordinatesComparator = coordinatesComparator;
        this.distanceHeuristic = null;
        this.additionalHeuristic = coordinatesComparator.getHeuristic();
    }

    public void calculateNextSteps(Coordinates coordinates) throws NotFoundException {

        this.savedMoves = findPathTo(coordinates);
    }
    /**
     * finds next step for reaching target.
     * @return PacmanAction
     */
    public PacmanAction getNextMove() throws NotFoundException {

        if (savedMoves.isEmpty())
            throw new NotFoundException();

        return savedMoves.pop();
    }

    protected Stack<PacmanAction> fillMovesStack (Coordinates coordinates) {

        Stack<PacmanAction> returnStack = new Stack<>();
        Coordinates currentCoordinates = coordinates;

        while (currentCoordinates.getMoveBefore() != null) {

            returnStack.push(currentCoordinates.getMoveBefore());
            currentCoordinates = currentCoordinates.getPreviousCoordinates();
        }

        return returnStack;
    }

    public Stack<PacmanAction> findPathTo (Coordinates coordinates) throws NotFoundException {

        this.distanceHeuristic = new DistanceHeuristic(coordinates);

        PriorityQueue<Coordinates> openList = new PriorityQueue<>(this.coordinatesComparator);
        HashMap<Integer, Coordinates> closedList = new HashMap<>();

        boolean found = false;
        Coordinates currentCoordinates = null;
        openList.add(getPacman().getCurrentCoordinates());

        while (openList.size() > 0 && !found) {

            currentCoordinates = openList.poll();
            found = currentCoordinates.equals(coordinates);

            if(!closedList.containsValue(currentCoordinates) && !found) {

                closedList.put(currentCoordinates.hashCode(), currentCoordinates);

                for(Coordinates coords : getNextCoordinates(currentCoordinates)) {

                    coords.setDistance(distanceHeuristic.getHeuristicValue(coords));
                    openList.add(coords);
                }
            }
        }

        if(!found)
            throw new NotFoundException();

        return fillMovesStack(currentCoordinates);
    }

    public Coordinates find(PacmanTileType tileType) throws NotFoundException {

        PriorityQueue<Coordinates> openList = new PriorityQueue<>(new CompareCoordinatesByDepth());
        openList.add(pacman.getCurrentCoordinates());

        while (openList.size() > 0) {

            Coordinates currentCoordinates = openList.poll();

            if(getTileTypeAt(currentCoordinates) == tileType)
                return currentCoordinates;
            else
                openList.addAll(getNextCoordinates(currentCoordinates));

        }
        throw new NotFoundException();
    }

    protected boolean[][] buildWallMap(PacmanTileType[][] currentWorld) {

        boolean[][] tempWallMap = new boolean[currentWorld.length][currentWorld[0].length];

        for(int i=0; i<currentWorld.length; i++) {

            for(int p=0; p<currentWorld[0].length; p++) {

                tempWallMap[i][p] = currentWorld[i][p] == PacmanTileType.WALL;
            }
        }

        return tempWallMap;
    }

    public void runRoundChecks(PacmanPercept pacmanPercept) {

        this.pacman.setCurrentCoordinates(pacmanPercept);
        Coordinates coordinates = this.pacman.getCurrentCoordinates();

        for(int i=0; i<currentWorld.length; i++) {

            for(int p=0; p<currentWorld[0].length; p++) {

                currentWorld[i][p] = pacmanPercept.getView()[i][p];
            }
        }

        if(currentWorld[coordinates.getPosX()][coordinates.getPosY()] == PacmanTileType.DOT) {

            this.currentWorld[coordinates.getPosX()][coordinates.getPosY()] = PacmanTileType.EMPTY;
            this.pacman.ateDot();
        }

        if(this.additionalHeuristic != null)
            additionalHeuristic.refresh();
    }

    public ArrayList<Coordinates> getNextCoordinates (Coordinates coordinates) {

        ArrayList<Coordinates> expandedCoordinates = new ArrayList<>();

        addCoordsToArrayList(expandedCoordinates, coordinates, PacmanAction.GO_NORTH);
        addCoordsToArrayList(expandedCoordinates, coordinates, PacmanAction.GO_SOUTH);
        addCoordsToArrayList(expandedCoordinates, coordinates, PacmanAction.GO_EAST);
        addCoordsToArrayList(expandedCoordinates, coordinates, PacmanAction.GO_WEST);

        return expandedCoordinates;
    }

    protected void addCoordsToArrayList (ArrayList<Coordinates> expandedCoordinates, Coordinates coordinates, PacmanAction pacmanAction) {

        try {

            Coordinates expandedCoords = getCoordinatesAfterMove(coordinates, pacmanAction);
            expandedCoordinates.add(expandedCoords);
        } catch (InvalidCoordinatesException ignored) { }
    }

    protected Coordinates getCoordinatesAfterMove (Coordinates coordinates, PacmanAction pacmanAction) throws InvalidCoordinatesException {

        Coordinates resultCoordinates;

        switch (pacmanAction) {

            case GO_EAST -> resultCoordinates = generateNextCoordinates(coordinates.getPosX()+1, coordinates.getPosY(), coordinates, PacmanAction.GO_EAST);
            case GO_WEST -> resultCoordinates = generateNextCoordinates(coordinates.getPosX()-1, coordinates.getPosY(), coordinates, PacmanAction.GO_WEST);
            case GO_NORTH -> resultCoordinates = generateNextCoordinates(coordinates.getPosX(), coordinates.getPosY()-1, coordinates, PacmanAction.GO_NORTH);
            case GO_SOUTH -> resultCoordinates = generateNextCoordinates(coordinates.getPosX(), coordinates.getPosY()+1, coordinates, PacmanAction.GO_SOUTH);
            default -> throw new InvalidCoordinatesException();
        }

        if(isWallAt(resultCoordinates) || isGhostAt(resultCoordinates) ) {

            throw new InvalidCoordinatesException();
        }

        return resultCoordinates;
    }

    protected Coordinates generateNextCoordinates (int posX, int posY, Coordinates coordinates, PacmanAction move) {

        Coordinates newCoordinates = new Coordinates(posX, posY, coordinates.getDepth()+1, coordinates.getDistance());

        newCoordinates.setPreviousCoordinates(coordinates);
        newCoordinates.setMoveBefore(move);

        return newCoordinates;
    }

    protected boolean isWallAt(Coordinates coordinates) {

        return wallMap[coordinates.getPosX()][coordinates.getPosY()];
    }

    protected boolean isGhostAt(Coordinates coordinates) {

        PacmanTileType tile = currentWorld[coordinates.getPosX()][coordinates.getPosY()];

        return tile == PacmanTileType.GHOST || tile == PacmanTileType.GHOST_AND_DOT || tile == PacmanTileType.GHOST_AND_POWERPILL;
    }

    protected PacmanTileType getTileTypeAt (Coordinates coordinates) {

        return currentWorld[coordinates.getPosX()][coordinates.getPosY()];
    }

    public PacmanTileType[][] getCurrentWorld() { return currentWorld; }
    public void setCurrentWorld(PacmanTileType[][] currentWorld) { this.currentWorld = currentWorld; }

    public boolean[][] getWallMap() { return wallMap; }

    public Pacman getPacman () { return this.pacman; }
}
