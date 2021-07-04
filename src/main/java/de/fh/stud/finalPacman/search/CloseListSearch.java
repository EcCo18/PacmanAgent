package de.fh.stud.finalPacman.search;

import de.fh.pacman.enums.PacmanTileType;
import de.fh.stud.finalPacman.Coordinates;
import de.fh.stud.finalPacman.comparators.IHeuristicComparator;
import de.fh.stud.finalPacman.exceptions.NotFoundException;

import java.util.HashMap;
import java.util.PriorityQueue;

public class CloseListSearch <T> {

    private final Search search;
    private final PriorityQueue<Coordinates> openList;
    private final HashMap<Integer, Coordinates> closedList;

    public CloseListSearch (IHeuristicComparator tComparator, Search search) {

        this.search = search;
        this.openList = new PriorityQueue<>(tComparator);
        this.closedList = new HashMap<>();
    }

    public Coordinates startSearch (T searchObject, Coordinates pacmanCoordinates) throws NotFoundException {



        boolean found = false;
        Coordinates currentCoordinates = null;
        openList.add(pacmanCoordinates);

        while (openList.size() > 0 && !found) {

            currentCoordinates = openList.poll();

            found = compare(searchObject, currentCoordinates);

            if(!found)
                expandCoordinates(currentCoordinates);
        }

        if(!found)
            throw new NotFoundException();

        return currentCoordinates;
    }

    private boolean compare(T searchObject, Coordinates currentCoordinates) {

        boolean found = false;

        if(searchObject.getClass() == Coordinates.class)
            found = currentCoordinates.equals(searchObject);
        else if (searchObject.getClass() == PacmanTileType.class)
            found = search.getTileTypeAt(currentCoordinates) == searchObject;

        return found;
    }

    private void expandCoordinates (Coordinates currentCoordinates) {

        if(!closedList.containsValue(currentCoordinates)) {

            closedList.put(currentCoordinates.hashCode(), currentCoordinates);
            openList.addAll(search.getNextCoordinates(currentCoordinates));
        }
    }
}
