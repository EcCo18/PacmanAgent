package de.fh.stud.finalPacman.ghosts;

import de.fh.kiServer.util.Util;
import de.fh.pacman.enums.PacmanTileType;
import de.fh.stud.finalPacman.Coordinates;
import de.fh.stud.finalPacman.pacman.Pacman;

import java.util.ArrayList;

public class GhostBusterClass {

    private final Pacman pacman;
    private final PacmanTileType[][] currentWorld;
    private int startX;
    private int startY;
    private ArrayList<GhostReport> reportList;

    // 2 blocks in each direction
    private final int searchRadius = 3;

    public GhostBusterClass(Pacman pacman, PacmanTileType[][] currentWorld) {

        this.pacman = pacman;
        this.currentWorld = currentWorld;
    }

    public ArrayList<GhostReport> searchForGhosts() {

        reportList = new ArrayList<>();

        PacmanTileType[][] worldSnapshot = createWorldSnapshot();

        for (int i = 0; i < worldSnapshot.length; i++) {

            for (int p = 0; p < worldSnapshot[0].length; p++) {
                if (worldSnapshot[i][p] == PacmanTileType.GHOST ||
                        worldSnapshot[i][p] == PacmanTileType.GHOST_AND_DOT ||
                        worldSnapshot[i][p] == PacmanTileType.GHOST_AND_POWERPILL) {

                    reportList.add(new GhostReport(new Coordinates(startX + i, startY + p),
                            new Coordinates(pacman.getCurrentCoordinates().getPosX(),
                                            pacman.getCurrentCoordinates().getPosY())));
                }
            }
        }

        return reportList;
    }

    protected PacmanTileType[][] createWorldSnapshot() {

        int xLengthLeft = calcLengthLeft();
        int xLengthRight = calcLengthRight();

        int yLengthUp = calcLengthUp();
        int yLengthDown = calcLengthDown();

        PacmanTileType[][] worldSnapshot = new PacmanTileType[calcSnapSizeWidth(xLengthLeft, xLengthRight)][calcSnapSizeHeight(yLengthUp, yLengthDown)];

        this.startX = (pacman.getCurrentCoordinates().getPosX() - xLengthLeft);
        this.startY = (pacman.getCurrentCoordinates().getPosY() - yLengthDown);
        // copy data from currentWorld in snapshot
        for (int i = startX; i < startX + worldSnapshot.length; i++) {

            for (int p = startY; p < startY + worldSnapshot[0].length; p++) {

                worldSnapshot[i-startX][p-startY] = currentWorld[i][p];
            }
        }

        return worldSnapshot;
    }

    protected int calcLengthRight() {

        int calcLengthRight = pacman.getCurrentCoordinates().getPosX() - (currentWorld.length - 1);
        if (calcLengthRight < 0) {

            calcLengthRight = calcLengthRight * (-1);
            return Math.min(calcLengthRight, searchRadius);
        } else {
            return calcLengthRight;
        }
    }

    protected int calcLengthLeft() {

        int calcLengthLeft = pacman.getCurrentCoordinates().getPosX();
        return Math.min(calcLengthLeft, searchRadius);
    }

    protected int calcLengthUp() {

        int calcLengthUp = pacman.getCurrentCoordinates().getPosY() - (currentWorld[0].length - 1);
        if (calcLengthUp < 0) {

            calcLengthUp = calcLengthUp * (-1);

            return Math.min(calcLengthUp, searchRadius);
        } else {
            return calcLengthUp;
        }
    }

    protected int calcLengthDown() {

        int calcLengthDown = pacman.getCurrentCoordinates().getPosY();
        return Math.min(calcLengthDown, searchRadius);
    }

    protected int calcSnapSizeHeight(int yLengthUp, int yLengthDown) {

        yLengthUp += 1;

        return yLengthUp + yLengthDown;
    }

    protected int calcSnapSizeWidth(int xLengthLeft, int xLengthRight) {

        xLengthRight += 1;

        return xLengthLeft + xLengthRight;
    }

    public Pacman getPacman() {
        return pacman;
    }

    public boolean isGhostInRange() {

        if(this.reportList == null) {

            return false;
        } else {

            return reportList.size() > 0;
        }
    }

    public double getClosestGhostInRange() {

        double minimumDistance = 0;

        if(isGhostInRange()) {

            for (GhostReport ghostReport: reportList) {

                minimumDistance = Math.min(ghostReport.getDistanceToGhost(), minimumDistance);
            }
        }

        return minimumDistance;
    }
}
