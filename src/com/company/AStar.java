package com.company;

import java.awt.*;
import java.util.ArrayList;

/*
    * AStar implementation finds the optimum path from a start point to end point.
*/

public class AStar {
    // traversable nodes that have been checked
    private ArrayList<Node> checked;
    private ArrayList<Node> nodes;

    // traversable nodes
    private ArrayList<Node> openList;
    // nodes already check or wall nodes
    private ArrayList<Node> closedList;

    private Node current;

    // start and end points
    private Point start;
    private Point end;

    public AStar(ArrayList<Node> nodes) {
        checked = new ArrayList<Node>();
        this.nodes = nodes;

        closedList = new ArrayList<Node>();
        initialisePath();

        if (start == null || end == null) {
            start = nodes.get(0).getCoordinate();
            end = nodes.get(nodes.size() - 1).getCoordinate();
        }

        openList = new ArrayList<Node>();
        openList.add(nodes.get(linearSearch(nodes, start)));
    }

    // finds optimum path from one node to next
    public boolean modifiedPathFind() {
        if (!openList.isEmpty()) {
            // gets the node with lowest F value
            current = getLowestF();

            // test if node is the end point
            if (current.equals(end)) {
                reconstructPath(current);
                return true;
            }

            // remove current node from openlist add to closedlist
            openList.remove(current);
            closedList.add(current);

            // get all valid adjacent nodes to current
            ArrayList<Node> neighbours = generateNeighbours(current);
            // assign to check to repaint
            checked = new ArrayList<Node>(neighbours);

            for (int i = 0; i < neighbours.size(); i++) {
                if (closedList.contains(neighbours.get(i))) {
                    continue;
                }


                int tentativeScore = current.getG() + 1;
                // add to open if not already present
                if (!openList.contains(neighbours.get(i))) {
                    Node addToOpen = nodes.get(linearSearch(nodes, neighbours.get(i).getCoordinate()));
                    addToOpen.setParent(current);
                    addToOpen.setG(current.getG());
                    addToOpen.setH(heuristicDistance(neighbours.get(i)));
                    addToOpen.setF(addToOpen.getG() + addToOpen.getH());
                    openList.add(addToOpen);
                }
                // if node already exists in open and there is a more optimum path from start to the current node
                // then use that G value
                else if (tentativeScore < current.getG()) {
                    Node updateValues = nodes.get(linearSearch(nodes, neighbours.get(i).getCoordinate()));
                    updateValues.setG(current.getG());
                    updateValues.setH(heuristicDistance(neighbours.get(i)));
                    updateValues.setF(updateValues.getG() + updateValues.getH());
                }
            }
        }
        // if all nodes evaluated then there is no path from start to end
        return false;
    }

    // paints checked nodes
    public void paintChecked() {
        while (!checked.isEmpty()) {
            if (!closedList.contains(checked.get(0)) || checked.get(0).getBackground() == Color.blue) {
                checked.get(0).setChecked();
            }
            checked.remove(0);
        }
    }

    // draws optimum to GUI
    private void reconstructPath(Node current) {
        while (current.getParentNode() != null) {
            if (current.getBackground() != Color.red)
                nodes.get(linearSearch(nodes, current.getCoordinate())).repaint(Color.MAGENTA);
            current = current.getParentNode();
        }
    }

    // finds all adjacent nodes
    private ArrayList<Node> generateNeighbours(Node current) {
        ArrayList<Node> neighbours = new ArrayList<Node>();
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x != 0 || y != 0) {
                    // determine if node is valid
                    addNeighbour(neighbours, (int) current.getCoordinate().getX() + x,
                            (int) current.getCoordinate().getY() + y);
                }
            }
        }
        // return valid adjacent nodes
        return neighbours;
    }

    // searches nodes list to determine if adjacent node is valid
    private void addNeighbour(ArrayList<Node> neighbours, int x, int y) {
        int index = linearSearch(nodes, new Point(x, y));
        if (index != -1) {
            Node newNode = nodes.get(index);
            neighbours.add(newNode);
        }
    }

    // calculate heuristic distance from given node to end point (manhattan distance)
    private int heuristicDistance(Node current) {
        return Math.abs(end.x - current.getCoordinate().x) + Math.abs(end.y - current.getCoordinate().y);
    }

    // returns node with lowest F value from open list
    private Node getLowestF() {
        int lowestIndex = 0;
        int lowestValue = openList.get(0).getF();
        for (int i = 0; i < openList.size(); i++) {
            if (lowestValue > openList.get(i).getF()) {
                lowestIndex = i;
                lowestValue = openList.get(i).getF();
            }
        }
        return openList.get(lowestIndex);
    }

    // linear search algorithm for arraylists
    private int linearSearch(ArrayList<Node> list, Point item) {
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).equals(item))
                return i;
        }
        return -1;
    }

    // add walls to closed list and initialise start and end values
    private void initialisePath() {
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).isWall())
                closedList.add(nodes.get(i));
            else if (nodes.get(i).isStart())
                this.start = nodes.get(i).getCoordinate();
            else if (nodes.get(i).isEnd())
                this.end = nodes.get(i).getCoordinate();
        }
    }

    public boolean isEmpty() {
        return openList.isEmpty();
    }
}
