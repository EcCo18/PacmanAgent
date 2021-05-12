package de.fh.stud.p3;

import java.util.Arrays;

public class Coordinates
{
    public Coordinates(int posX, int posY)
    {
        this.posX = posX;
        this.posY = posY;
    }

    private int posX, posY;

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

    public boolean equals (Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates coordinates = (Coordinates) o;
        return (this.posX == coordinates.getPosX()) && (this.posY == coordinates.getPosY());
    }
}
