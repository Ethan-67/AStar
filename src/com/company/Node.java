package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/*
    * Node implementation keeps track of cell properties such as if the cell is a wall, or if it is the start or end
    * point. Also holds G, H, and F values needed by the AStar class to find most optimised path.
*/

public class Node extends JLabel {
    private Point coordinate;
    private JLabel cell;

    static boolean isEditable = false;
    private boolean isWall;

    private Node parent = null;
    private boolean start = false;
    private boolean end = false;

    private int G;
    private int H;
    private int F;

    public Node(int x, int y) {
        coordinate = new Point(x, y);
        cell = new JLabel();
        cell.setSize(15, 15);
        cell.setBackground(Color.white);
        cell.setOpaque(true);
        cell.setVisible(true);

        cell.addMouseListener(new MouseAdapter() {
            // changes color of cell and sets wall property of node to true
            @Override
            public void mouseEntered(MouseEvent e) {
                if (cell.getBackground() == Color.white && isEditable) {
                    cell.setBackground(Color.darkGray);
                    if (!isWall)
                        cell.setSize(cell.getWidth(), cell.getHeight()+1);
                    setWall(true);

                }
            }
            // detect mouse press + lctrl or lshift press
            @Override
            public void mousePressed(MouseEvent e) {
                int modifiersEx = e.getModifiersEx();
                int shiftMask = MouseEvent.SHIFT_DOWN_MASK;
                int ctrlMask = MouseEvent.CTRL_DOWN_MASK;

                if ((modifiersEx & shiftMask) == shiftMask && !start) {
                    start = true;
                    cell.setBackground(Color.red);
                    cell.setSize(cell.getWidth(), cell.getHeight()+1);
                    return;
                } else if ((modifiersEx & ctrlMask) == ctrlMask && !end) {
                    end = true;
                    cell.setBackground(Color.blue);
                    cell.setSize(cell.getWidth(), cell.getHeight()+1);
                    return;
                }

                // create wall if cell pressed
                isEditable = !isEditable;
                if (cell.getBackground() == Color.white) {
                    cell.setBackground(Color.darkGray);
                    if (!isWall)
                        cell.setSize(cell.getWidth(), cell.getHeight()+1);
                    setWall(true);
                }
            }
        });
    }

    // reset node properties
    public void setToDefault() {
        if (cell.getBackground() != Color.white) {
            cell.setSize(cell.getWidth(), cell.getHeight()-1);
        }
        isWall = false;
        start = false;
        end = false;
        this.parent = null;
        this.G = 0;
        this.H = 0;
        this.F = 0;
        cell.setBackground(Color.white);
    }

    // test if point given is equal to this node
    public boolean equals(Node n) {
        return this.coordinate.getX() == n.coordinate.getX() &&
                this.coordinate.getY() == n.coordinate.getY();
    }

    // test if point given is equal to this node
    public boolean equals(Point p) {
        return this.coordinate.getX() == p.getX() &&
                this.coordinate.getY() == p.getY();
    }

    // changes color of cell to yellow (Signifies its been checked by AStar)
    public void setChecked() {
        if (cell.getBackground() == Color.white)
            cell.setSize(cell.getWidth(), cell.getHeight()+1);
        cell.setBackground(Color.yellow);
    }

    // changes color of cell to blue (Signifies it one cell in the optimum path to end point)
    public void setPath() {
        if (cell.getBackground() == Color.white)
            cell.setSize(cell.getWidth(), cell.getHeight()+1);
        cell.setBackground(Color.blue);
    }

    // setters and getters
    public boolean isWall() {
        return isWall;
    }

    public boolean isStart() {
        return start;
    }

    public boolean isEnd() {
        return end;
    }

    public void repaint(Color c) {
        cell.setBackground(c);
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setWall(boolean wall) {
        isWall = wall;
    }

    public void setF(int f) {
        F = f;
    }

    public void setG(int g) {
        G = g;
    }

    public void setH(int h) {
        H = h;
    }

    public Point getCoordinate() {
        return coordinate;
    }

    public JLabel getCell() {
        return cell;
    }

    public Node getParentNode() {
        return parent;
    }

    public int getF() {
        return F;
    }

    public int getG() {
        return G;
    }

    public int getH() {
        return H;
    }
}
