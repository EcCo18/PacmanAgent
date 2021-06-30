package de.fh.stud.finalPacman.search;

import de.fh.pacman.enums.PacmanAction;
import de.fh.pacman.enums.PacmanTileType;
import de.fh.stud.finalPacman.Coordinates;
import de.fh.stud.finalPacman.comparators.CompareCoordinatesByDepth;
import de.fh.stud.finalPacman.exceptions.NotFoundException;
import de.fh.stud.finalPacman.exceptions.InvalidCoordinatesException;
import de.fh.stud.finalPacman.pacman.Pacman;

import java.util.ArrayList;
import java.util.PriorityQueue;

public abstract class Search {

    private PacmanTileType[][] currentWorld;
    private final boolean [][] wallMap;
    private Pacman pacman;

    public Search(PacmanTileType[][] currentWorld, Pacman pacman) {

        this.pacman = pacman;
        this.currentWorld = currentWorld;
        wallMap = buildWallMap(currentWorld);
    }

    /**
     * finds next step for reaching target.
     *
     * @param coordinates
     * @return PacmanAction
     */
    public abstract PacmanAction findNextStepTo(Coordinates coordinates);

    public Coordinates find(PacmanTileType tileType) throws NotFoundException {

        PriorityQueue<Coordinates> openList = new PriorityQueue<>(new CompareCoordinatesByDepth());
        openList.add(pacman.getCurrentCoordinates());

        while (openList.size() > 0) {

            Coordinates currentCoordinates = openList.poll();

            if(getTileTypeAt(currentCoordinates) == tileType) {

                return currentCoordinates;
            } else {

                openList.addAll(getNextCoordinates(currentCoordinates));
            }
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

    public void ateDotAt(Coordinates coordinates) {

        if(currentWorld[coordinates.getPosX()][coordinates.getPosY()] == PacmanTileType.DOT) {

            this.currentWorld[coordinates.getPosX()][coordinates.getPosY()] = PacmanTileType.EMPTY;
        }
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

            case GO_EAST -> resultCoordinates = new Coordinates(coordinates.getPosX()+1, coordinates.getPosY(), coordinates.getDepth()+1);
            case GO_WEST -> resultCoordinates = new Coordinates(coordinates.getPosX()-1, coordinates.getPosY(), coordinates.getDepth()+1);
            case GO_NORTH -> resultCoordinates = new Coordinates(coordinates.getPosX(), coordinates.getPosY()+1, coordinates.getDepth()+1);
            case GO_SOUTH -> resultCoordinates = new Coordinates(coordinates.getPosX(), coordinates.getPosY()-1, coordinates.getDepth()+1);
            default -> throw new InvalidCoordinatesException();
        }

        if(isWallAt(resultCoordinates) || resultCoordinates.getPosX() < 1 || resultCoordinates.getPosY() < 1) {

            throw new InvalidCoordinatesException();
        }

        return resultCoordinates;
    }

    protected boolean isWallAt(Coordinates coordinates) {

        return wallMap[coordinates.getPosX()][coordinates.getPosY()];
    }

    protected PacmanTileType getTileTypeAt (Coordinates coordinates) {

        return currentWorld[coordinates.getPosX()][coordinates.getPosY()];
    }

    public PacmanTileType[][] getCurrentWorld() { return currentWorld; }

    public void setCurrentWorld(PacmanTileType[][] currentWorld) { this.currentWorld = currentWorld; }

    public boolean[][] getWallMap() { return wallMap; }

    public Pacman getPacman () { return this.pacman; }
}
