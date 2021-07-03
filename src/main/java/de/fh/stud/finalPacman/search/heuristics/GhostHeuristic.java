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
    private Pacman pacman;

    public GhostHeuristic(Pacman pacman, PacmanTileType[][] currentWorld) {

        ghostBusterClass = new GhostBusterClass(pacman, currentWorld);
        ghostList = ghostBusterClass.searchForGhosts();

        this.pacman = ghostBusterClass.getPacman();
    }

    // ToDo remove souts
    @Override
    public double getHeuristicValue(Coordinates fieldPos) {

        double distanceToGhostValue = evaluateDistanceToGhost(fieldPos);
        // System.out.println("Field, x: " + fieldPos.getPosX() + "; y: " + fieldPos.getPosY());
        // System.out.println("dist to ghost value: " + distanceToGhostValue);
        double directionValue = evaluateDirectionToGhost(fieldPos);
        // System.out.println("direction value: " + directionValue);

        return distanceToGhostValue + directionValue;
    }

    @Override
    public void refresh() {

        ghostList = ghostBusterClass.searchForGhosts();
        // System.out.println(ghostList.size());
    }

    protected double evaluateDistanceToGhost(Coordinates fieldPos) {

        double erg = 0;

        for (GhostReport ghostReport : ghostList) {

            double distanceToGhost = Math.sqrt(
                    Math.pow(ghostReport.getCurrentCoordinates().getPosX() - fieldPos.getPosX(), 2) +
                            Math.pow(ghostReport.getCurrentCoordinates().getPosY() - fieldPos.getPosY(), 2));

            if (distanceToGhost <= 2) {

                erg = erg - 5;
            } else if (distanceToGhost == 3) {

                erg = erg + 0.5;
            } else if (distanceToGhost > 3 && distanceToGhost <= 5) {

                erg = erg + 1;
            }
        }
        return erg;
    }

    protected double evaluateDirectionToGhost(Coordinates fieldPos) {

        double erg = 0;
        ArrayList<Directions> directionsFromPacmanToField = calcDirectionFromPosToAnotherPos(pacman, fieldPos);

        for(GhostReport ghostReport : ghostList) {

            ArrayList<Directions> directionsFromPacmanToGhost = calcDirectionFromPosToAnotherPos(pacman, ghostReport);
            for(Directions pacmanToGhostDirection : directionsFromPacmanToGhost) {

                for(Directions pacmanToFieldDirection : directionsFromPacmanToField) {

                    if(pacmanToGhostDirection == pacmanToFieldDirection) {

                        erg -= 2;
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
