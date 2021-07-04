package de.fh.stud.finalPacman.deadEnd;

import de.fh.pacman.enums.PacmanTileType;
import de.fh.stud.finalPacman.Coordinates;
import de.fh.stud.finalPacman.pacman.Pacman;

import java.util.ArrayList;

public class DeadEndDetector {

    private final PacmanTileType[][] currentWorld;
    private final int iterationMax = 20;
    private ArrayList<Coordinates> closedList;
    private final Pacman pacman;

    public DeadEndDetector(PacmanTileType[][] currentWorld , Pacman pacman) {

        this.currentWorld = currentWorld;
        closedList = new ArrayList<>();
        this.pacman = pacman;
    }

    /**
     * tests if field is the start of an dead end
     *
     * @param fieldPos the fieldPos to be checked
     * @param iteration has to be 1 on first iteration
     * @return DeadEndReport contains the result
     */
    public DeadEndReport checkForDeadEnd(Coordinates fieldPos, int iteration) {

        // add pac pos to closed list
        closedList.add(pacman.getCurrentCoordinates());
        ArrayList<Coordinates> nextPossibleSteps = getNextPossibleSteps(fieldPos);

        // more options than one to move
        if(nextPossibleSteps.size() > 1) {

            resetClosedList();
            return new DeadEndReport(-1, DeadEndType.NO_DEAD_END);
        }
        // is in the end of an dead end
        else if(nextPossibleSteps.size() == 0) {

            resetClosedList();
            System.out.println("--> found dead end");
            return new DeadEndReport(iteration - 1, DeadEndType.DEAD_END);
        }
        // go deeper in tunnel
        else {

            closedList.add(nextPossibleSteps.get(0));
            if(iteration <= iterationMax) {

                //System.out.println("iteration: " + iteration);
                return checkForDeadEnd(nextPossibleSteps.get(0), ++iteration);
            } else {

                resetClosedList();
                return  new DeadEndReport( -1, DeadEndType.CANCELED);
            }
        }
    }

    protected ArrayList<Coordinates> getNextPossibleSteps(Coordinates fieldPos) {

        ArrayList<Coordinates> nextPossibleSteps = new ArrayList<>();
        Coordinates[] nextStepsToCheck = new Coordinates[4];

        // north
        nextStepsToCheck[0] = new Coordinates(fieldPos.getPosX(), fieldPos.getPosY() - 1);
        // east
        nextStepsToCheck[1] = new Coordinates(fieldPos.getPosX() + 1, fieldPos.getPosY());
        // south
        nextStepsToCheck[2] = new Coordinates(fieldPos.getPosX(), fieldPos.getPosY() + 1);
        // west
        nextStepsToCheck[3] = new Coordinates(fieldPos.getPosX() - 1, fieldPos.getPosY());

        for (Coordinates coordinates : nextStepsToCheck) {

            if (coordinates.getPosX() > 0 && coordinates.getPosY() > 0) {

                PacmanTileType tileTypeOnField = currentWorld[coordinates.getPosX()][coordinates.getPosY()];

                if (!checkIfInClosedList(coordinates) &&
                        tileTypeOnField == PacmanTileType.DOT ||
                        tileTypeOnField == PacmanTileType.EMPTY ||
                        tileTypeOnField == PacmanTileType.POWERPILL) {

                    //System.out.println("Added (" + coordinates.getPosX() + "/" + coordinates + ")");
                    nextPossibleSteps.add(coordinates);
                }
            }
        }

        //System.out.println("Found " + nextPossibleSteps.size() + " possible steps for (" +
        //        fieldPos.getPosX() + "/" + fieldPos.getPosY() + ")");
        return nextPossibleSteps;
    }

    protected boolean checkIfInClosedList(Coordinates coordinates) {

        for (Coordinates coordinatesInList : closedList) {

            if (coordinatesInList.getPosX() == coordinates.getPosX() &&
                    coordinatesInList.getPosY() == coordinates.getPosY()) {

                return true;
            }
        }
        return false;
    }

    protected void resetClosedList() {

        closedList = new ArrayList<>();
    }
}
