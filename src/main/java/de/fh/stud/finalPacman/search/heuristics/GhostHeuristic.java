package de.fh.stud.finalPacman.search.heuristics;

import de.fh.stud.finalPacman.Coordinates;
import de.fh.stud.finalPacman.ghosts.GhostBusterClass;
import de.fh.stud.finalPacman.ghosts.GhostReport;
import de.fh.stud.finalPacman.pacman.Pacman;
import de.fh.stud.finalPacman.search.ICoordinates;
import de.fh.stud.finalPacman.search.IHeuristic;
import de.fh.pacman.enums.PacmanTileType;

import java.util.ArrayList;

public class GhostHeuristic implements IHeuristic {

    private final GhostBusterClass ghostBusterClass;
    private ArrayList<GhostReport> ghostList;
    private final Pacman pacman;
    private final PacmanTileType[][] currentWorld;

    public GhostHeuristic(Pacman pacman, PacmanTileType[][] currentWorld) {

        ghostBusterClass = new GhostBusterClass(pacman, currentWorld);
        ghostList = ghostBusterClass.searchForGhosts();

        this.currentWorld = currentWorld;

        this.pacman = ghostBusterClass.getPacman();
    }

    @Override
    public double getHeuristicValue(Coordinates fieldPos) {

        double distanceToGhostValue = evaluateDistanceToGhost(fieldPos);
        double directionValue = evaluateDirectionToGhost(fieldPos);
        double powerpillValue = checkForPowerpill(fieldPos);

        return distanceToGhostValue * 3 + directionValue * 1.5 + powerpillValue;
    }

    @Override
    public void refresh() {

        ghostList = ghostBusterClass.searchForGhosts();
        // System.out.println(ghostList.size());
    }

    /**
     * checks if field contains powerpill and returns a high value only when in panic mode.
     * @param fieldPos
     * @return double (heuristic value)
     */
    protected double checkForPowerpill(Coordinates fieldPos) {

        double res = 0;
        if(!ghostList.isEmpty()) {

            if(this.currentWorld[fieldPos.getPosX()][fieldPos.getPosY()] == PacmanTileType.POWERPILL) {

                res += 15;
            }
        }

        return res;
    }

    protected double evaluateDistanceToGhost(Coordinates fieldPos) {

        double res = 0;

        for (GhostReport ghostReport : ghostList) {

            double distanceToGhost = Math.sqrt(
                    Math.pow(ghostReport.getCurrentCoordinates().getPosX() - fieldPos.getPosX(), 2) +
                            Math.pow(ghostReport.getCurrentCoordinates().getPosY() - fieldPos.getPosY(), 2));

            if (distanceToGhost <= 2) {

                res = res - 5;
            } else if (distanceToGhost == 3) {

                res = res + 0.5;
            } else if (distanceToGhost > 3 && distanceToGhost <= 5) {

                res = res + 1;
            }
        }
        return res;
    }

    protected double evaluateDirectionToGhost(Coordinates fieldPos) {

        double erg = 0;
        ArrayList<Directions> directionsFromPacmanToField = calcDirectionFromPosToAnotherPos(pacman, fieldPos);

        for(GhostReport ghostReport : ghostList) {

            ArrayList<Directions> directionsFromPacmanToGhost = calcDirectionFromPosToAnotherPos(pacman, ghostReport);
            for(Directions pacmanToGhostDirection : directionsFromPacmanToGhost) {

                for(Directions pacmanToFieldDirection : directionsFromPacmanToField) {

                    if(pacmanToGhostDirection == pacmanToFieldDirection) {

                        //2
                        erg -= 5;
                    }
                }
            }
        }

        return erg;
    }

    protected ArrayList<Directions> calcDirectionFromPosToAnotherPos(ICoordinates firstPos, ICoordinates otherPos) {

        ArrayList<Directions> directions  = new ArrayList<>();

        // check for x
        if(firstPos.getCurrentCoordinates().getPosX() - otherPos.getCurrentCoordinates().getPosX() > 0) {

            directions.add(Directions.EAST);
        } else if(firstPos.getCurrentCoordinates().getPosX() - otherPos.getCurrentCoordinates().getPosX() < 0) {

            directions.add(Directions.WEST);
        }

        // check for y
        if(firstPos.getCurrentCoordinates().getPosY() - otherPos.getCurrentCoordinates().getPosY() > 0) {

            directions.add(Directions.SOUTH);
        } else if(firstPos.getCurrentCoordinates().getPosY() - otherPos.getCurrentCoordinates().getPosY() < 0) {

            directions.add(Directions.NORTH);
        }

        return directions;
    }

    public GhostBusterClass getGhostBusterClass() {
        return ghostBusterClass;
    }
}
