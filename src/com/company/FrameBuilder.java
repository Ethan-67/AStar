package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/*
    * FrameBuilder creates a GUI window and populates it with nodes
*/

public class FrameBuilder extends JFrame {

    private ArrayList<Node> nodes;

    public FrameBuilder(int matrixValue, JButton btn, JButton resetBtn, JSlider slider) {
        nodes = new ArrayList<Node>();

        this.setSize(1200, 850);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(Color.WHITE);

        this.add(btn);
        this.add(resetBtn);
        this.add(slider);

        JPanel panel = createCellPane(matrixValue);
        this.add(panel);
        this.setVisible(true);
    }

    // creates a JPanel to hold cells
    private JPanel createCellPane(int matrixValue) {
        JPanel cellPanel = new JPanel();
        cellPanel.setSize(800,800);

        GridLayout layout = new GridLayout(matrixValue, matrixValue);
        layout.setVgap(3);
        layout.setHgap(3);

        cellPanel.setLayout(layout);
        cellPanel.setBackground(Color.BLACK);
        cellPanel.setOpaque(true);
        addCells(cellPanel, matrixValue);

        return cellPanel;
    }

    // adds cells to panel
    private void addCells(JPanel cellPanel, int matrixValue) {
        for (int i = 0; i < matrixValue; i++) {
            for (int j = 0; j < matrixValue; j++) {
                Node cell = new Node(j, i);
                cellPanel.add(cell.getCell());
                nodes.add(cell);
            }
        }
    }

    // node getter
    public ArrayList<Node> getNodes() {
        return nodes;
    }
}
