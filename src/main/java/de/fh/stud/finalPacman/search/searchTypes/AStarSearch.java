package de.fh.stud.finalPacman.search.searchTypes;

import de.fh.pacman.enums.PacmanAction;
import de.fh.pacman.enums.PacmanTileType;
import de.fh.stud.finalPacman.Coordinates;
import de.fh.stud.finalPacman.comparators.CompareCoordinatesByDepthAndDistance;
import de.fh.stud.finalPacman.exceptions.NotFoundException;
import de.fh.stud.finalPacman.search.Search;
import de.fh.stud.finalPacman.pacman.Pacman;

import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

public class AStarSearch extends Search {

    private Stack<PacmanAction> savedMoves;

    public AStarSearch(PacmanTileType[][] currentWorld, Pacman pacman) {

        super(currentWorld, pacman);
        this.savedMoves = null;
    }

    @Override
    public PacmanAction findNextStepTo(Coordinates coordinates) throws NotFoundException {

        if(savedMoves == null)
            savedMoves = findPathTo(coordinates);

        return savedMoves.pop();
    }

    public Stack<PacmanAction> findPathTo (Coordinates coordinates) throws NotFoundException {

        PriorityQueue<Coordinates> openList = new PriorityQueue<>(new CompareCoordinatesByDepthAndDistance());
        HashMap<Integer, Coordinates> closedList = new HashMap<>();

        boolean found = false;
        Coordinates currentCoordinates = null;
        openList.add(coordinates);

        while (openList.size() > 0 && !found) {

            currentCoordinates = openList.poll();
            found = currentCoordinates == coordinates;

            if(!closedList.containsValue(currentCoordinates) && !found) {

                closedList.put(currentCoordinates.hashCode(), currentCoordinates);

                for(Coordinates coords : super.getNextCoordinates(currentCoordinates)) {

                    coords.setDistance(distanceToGoal(coords, coordinates));
                }
            }
        }

        if(!found || currentCoordinates == null)
            throw new NotFoundException();

        Stack<PacmanAction> returnStack = new Stack<>();

        while (currentCoordinates.getPreviousCoordinates() != null) {

            returnStack.push(currentCoordinates.getMoveBefore());
            currentCoordinates = currentCoordinates.getPreviousCoordinates();
        }

        return returnStack;
    }

    protected double distanceToGoal (Coordinates currentPosition, Coordinates goalPosition) {

        int height = currentPosition.getPosY() - goalPosition.getPosY();
        int width = currentPosition.getPosX() - goalPosition.getPosX();

        return Math.sqrt(height*height + width*width);
    }
}
