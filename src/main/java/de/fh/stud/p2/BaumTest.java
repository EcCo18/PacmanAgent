package de.fh.stud.p2;

import de.fh.kiServer.util.Util;
import de.fh.pacman.enums.PacmanTileType;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BaumTest
{

    public static void main(String[] args) throws Exception
    {
        //Anfangszustand nach Aufgabe
        PacmanTileType[][] view = {
                {PacmanTileType.WALL, PacmanTileType.WALL, PacmanTileType.WALL, PacmanTileType.WALL},
                {PacmanTileType.WALL, PacmanTileType.EMPTY, PacmanTileType.DOT, PacmanTileType.WALL},
                {PacmanTileType.WALL, PacmanTileType.DOT, PacmanTileType.WALL, PacmanTileType.WALL},
                {PacmanTileType.WALL, PacmanTileType.WALL, PacmanTileType.WALL, PacmanTileType.WALL}
        };
        //Startposition des Pacman
        int posX = 1, posY = 1;
        Coordinates pacManCoordinates = new Coordinates(posX, posY);


        view[posX][posY] = PacmanTileType.PACMAN;
        Node startNode = new Node(view, pacManCoordinates);

        Stack<Node> openList = new Stack<>();
        openList.add(startNode);

        ArrayList<Node> savedNodes = new ArrayList<>();
        savedNodes.add(startNode);

        int counter = 0;
        while (counter < 3 && !openList.empty())
        {
            List<Node> foundNodes = new ArrayList<>();
            while (!openList.isEmpty())
            {
                Node node = openList.pop();
                foundNodes.addAll(node.expand());
            }

            savedNodes.addAll(foundNodes);
            openList.addAll(foundNodes);
            counter++;
        }


        for (Node node : savedNodes)
        {
            Util.printView(node.getCurrentWorld());
        }
    }
}