package de.fh.stud.p3;

import de.fh.kiServer.util.Util;
import de.fh.pacman.enums.PacmanTileType;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Suche
{

    PriorityQueue<Node> queue;

    /*
     * TODO Praktikum 4 [1]: Erweitern Sie diese Klasse um weitere Suchstrategien (siehe Aufgabenblatt)
     * zu unterstützen.
     */

    public Suche(Node startNode)
    {

        this.queue = new PriorityQueue<>();
        this.queue.add(startNode);
    }

    public Node start() throws Exception
    {

        Node currentNode;
        Node finalNode = null;

        boolean firstNode = true;

        while (queue.peek() != null && finalNode == null)
        {

            currentNode = queue.poll();
            assert currentNode != null;

            if(firstNode) {

                currentNode.addToWalkedPath(currentNode);
                firstNode = false;
            }

            expandNodeWithDeepSearchPriority(currentNode);

             if(isFinalState(currentNode))
                 finalNode = currentNode;
        }

        return finalNode;
    }

    public void expandNodeWithDeepSearchPriority(Node nodeToExpand) throws Exception
    {
        ArrayList<Node> expandedNodes = new ArrayList<>(nodeToExpand.expand());

        removeNodeIfCycle(expandedNodes);

        for (Node expandedNode : expandedNodes)
        {
            expandedNode.addToWalkedPath(expandedNode);

            expandedNode.setPriority(nodeToExpand.getPriority() - 1);
        }

        queue.addAll(expandedNodes);
    }

    public void expandNodeWithWidthSearchPriority(Node nodeToExpand) throws Exception
    {
        ArrayList<Node> expandedNodes = new ArrayList<>(nodeToExpand.expand());

        removeNodeIfCycle(expandedNodes);

        for (Node expandedNode : expandedNodes)
        {
            expandedNode.addToWalkedPath(expandedNode);

            expandedNode.setPriority(nodeToExpand.getPriority() + 1);
        }

        queue.addAll(expandedNodes);
    }

    private void removeNodeIfCycle(ArrayList<Node> expandedNodes) {

        expandedNodes.removeIf(node -> node.getWalkedPath().contains(node));
    }

    private boolean isFinalState(Node node)
    {

        PacmanTileType[][] world = node.getCurrentWorld();

        for (PacmanTileType[] x : world)
        {
            for (PacmanTileType y : x)
            {
                if (y == PacmanTileType.DOT)
                    return false;
            }
        }

        return true;
    }
}
