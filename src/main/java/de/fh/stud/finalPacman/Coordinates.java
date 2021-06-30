package de.fh.stud.finalPacman;

public class Coordinates {

    public Coordinates(int posX, int posY) {

        this(posX, posY, 0);
    }

    public Coordinates(int posX, int posY, int depth) {

        this.posX = posX;
        this.posY = posY;
        this.depth = depth;
    }

    private int posX, posY, depth;

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
}
