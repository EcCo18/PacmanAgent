package de.fh.stud.finalPacman;

import de.fh.pacman.enums.PacmanTileType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class Suche
{

    PriorityQueue<Node> queue;
    HashMap<Integer, Node> alreadyExpandedNodes = new HashMap<>();
    private final String search;

    public Suche(Node startNode, String search)
    {
        this.queue = new PriorityQueue<>();
        this.queue.add(startNode);
        this.search = search;
        startNode.setRemainingDots(remainingDots(startNode.getCurrentWorld()));
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

            expandNodeWithSearchPriority(currentNode);

             if(isFinalState(currentNode))
                 finalNode = currentNode;

        }

        if(finalNode != null)
        {
            System.out.println("OpenList: " + queue.size() + " ClosedList: " + alreadyExpandedNodes.size() + " Länge Lösung: " + (finalNode.getWalkedPath().size() - 1));
        } else {

            System.out.println("No way to success...!");
        }
        return finalNode;
    }

    private void expandNodeWithSearchPriority (Node nodeToExpand) throws Exception {

        ArrayList<Node> expandedNodes = new ArrayList<>(nodeToExpand.expand());

        //removeNodeIfCycle(expandedNodes);
        removeNodeIfAlreadyExpanded(expandedNodes);

        for (Node expandedNode : expandedNodes)
        {
            expandedNode.addToWalkedPath(expandedNode);

            switch (search) {

                case "deep" -> expandedNode.setPriority(getDeepSearchPriority(expandedNode));
                case "width" -> expandedNode.setPriority(getWidthSearchPriority(expandedNode));
                case "greedy" -> expandedNode.setPriority(getGreedySearchPriority(expandedNode));
                case "ucs" -> expandedNode.setPriority(getUCSSearchPriority(expandedNode));
                case "as" -> expandedNode.setPriority(getAStarSearchPriority(expandedNode));
            }
        }

        queue.addAll(expandedNodes);
    }

    public int getDeepSearchPriority(Node nodeToExpand) {

        return nodeToExpand.getPriority() - 1;
    }

    public int getWidthSearchPriority(Node nodeToExpand) {

        return nodeToExpand.getPriority() + 1;
    }

    public int getGreedySearchPriority(Node nodeToExpand) {

        return nodeToExpand.getRemainingDots();
    }

    public int getUCSSearchPriority(Node nodeToExpand) {

        return nodeToExpand.getPriority() + calculateCost(nodeToExpand);
    }

    private int getAStarSearchPriority (Node nodeToExpand) {

        int currentPriority = nodeToExpand.getPriority();
        int nextMoveCosts = calculateCost(nodeToExpand);
        int remainingDots = nodeToExpand.getRemainingDots();

        List<Node> path = nodeToExpand.getWalkedPath();
        PacmanTileType [][] previousWorldState = path.get(path.size()-2).getCurrentWorld();
        int previousRemainingDots = remainingDots(previousWorldState);

        return currentPriority + nextMoveCosts +remainingDots - previousRemainingDots;
    }

    private int calculateCost(Node expandedNode) {

        List <Node> path = expandedNode.getWalkedPath();
        PacmanTileType [][] previousWorldState = path.get(path.size()-2).getCurrentWorld();

        Coordinates nextTileCoordinates = expandedNode.getPacPos();

        return previousWorldState [nextTileCoordinates.getPosX()][nextTileCoordinates.getPosY()] == PacmanTileType.DOT ? 1 : 2;
    }

    protected int remainingDots (PacmanTileType[][] currentWorld) {

        int counter = 0;

        for (PacmanTileType[] first : currentWorld) {

            for (PacmanTileType tile : first) {

                if(tile == PacmanTileType.DOT)
                    counter++;
            }
        }

        return counter;
    }

    protected void removeNodeIfCycle(ArrayList<Node> expandedNodes) {

        expandedNodes.removeIf(node -> node.getWalkedPath().contains(node));
    }

    protected void removeNodeIfAlreadyExpanded (ArrayList<Node> expandedNodes) {

        expandedNodes.removeIf(this::nodeIsAlreadyExpanded);
    }

    protected boolean nodeIsAlreadyExpanded (Node expandedNode) {

        if(alreadyExpandedNodes.get(expandedNode.hashCode()) != null) {

            return true;
        } else {

            alreadyExpandedNodes.put(expandedNode.hashCode(), expandedNode);
            return false;
        }
    }

    protected boolean isFinalState(Node node) {

        return node.getRemainingDots() == 0;
    }
}
