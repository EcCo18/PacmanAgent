package de.fh.stud.finalPacman.ghosts;

import de.fh.pacman.enums.PacmanTileType;
import de.fh.stud.finalPacman.pacman.Pacman;

import java.util.ArrayList;

public class GhostBusterClass {

    private final Pacman pacman;
    private final PacmanTileType[][] currentWorld;

    // 2 blocks in each direction
    private final int searchRadius = 2;

    public GhostBusterClass(Pacman pacman, PacmanTileType[][] currentWorld) {

        this.pacman = pacman;
        this.currentWorld = currentWorld;
    }

    protected ArrayList<GhostReport> searchForGhosts() {

        ArrayList<GhostReport> reportList = new ArrayList<>();


        return reportList;
    }

    protected PacmanTileType[][] createWorldSnapshot() {

        int xLength;
        int xLengthLeft;
        int xLengthRight;

        if((currentWorld.length-1) - pacman.getCurrentCoordinates().getPosX() > 2) {

        }
        return null;
    }

}
