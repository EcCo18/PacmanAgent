package de.fh.stud.finalPacman;

import de.fh.pacman.enums.PacmanAction;
import de.fh.pacman.enums.PacmanTileType;

import java.util.Objects;

public class Coordinates {

    public Coordinates(int posX, int posY) {

        this(posX, posY, 0, 0);
    }

    public Coordinates(int posX, int posY, int depth) {

        this(posX, posY, depth, 0);
    }

    public Coordinates(int posX, int posY, int depth, double distance) {

        this.posX = posX;
        this.posY = posY;
        this.depth = depth;
        this.moveBefore = null;
        this.previousCoordinates = null;
    }

    private int posX;
    private int posY;
    private int depth;
    private double distance;

    private PacmanAction moveBefore;
    private Coordinates previousCoordinates;

    public PacmanAction getMoveBefore() {
        return moveBefore;
    }
    public void setMoveBefore(PacmanAction moveBefore) {
        this.moveBefore = moveBefore;
    }

    public Coordinates getPreviousCoordinates() {
        return previousCoordinates;
    }
    public void setPreviousCoordinates(Coordinates previousCoordinates) {
        this.previousCoordinates = previousCoordinates;
    }

    public double getDistance() {
        return distance;
    }
    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getPosX()
    {
        return posX;
    }
    public void setPosX(int posX)
    {
        this.posX = posX;
    }

    public int getPosY()
    {
        return posY;
    }
    public void setPosY(int posY)
    {
        this.posY = posY;
    }

    public int getDepth()
    {
        return depth;
    }
    public void setDepth(int depth)
    {
        this.depth = depth;
    }

    public boolean equals (Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates coordinates = (Coordinates) o;
        return (this.posX == coordinates.getPosX()) && (this.posY == coordinates.getPosY());
    }

    @Override
    public int hashCode() {
        return Objects.hash(posX, posY);
    }
}
