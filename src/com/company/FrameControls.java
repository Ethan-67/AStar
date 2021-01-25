package com.company;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/*
    * The FrameControls class creates GUI elements such as a start/reset button and a speed bar which are added to the
    * FrameBuilder class. The start button when pressed will call the AStar class and begin the algorithm visualisation
*/

public class FrameControls implements ActionListener {
    // speed of slider
    private int speed = 1;

    // GUI elements
    private JSlider slider;
    private JButton startBtn;
    private JButton resetBtn;

    // list of all nodes and start/end points
    private ArrayList<Node> nodes;
    private Point start;
    private Point end;

    // create GUI elements
    public FrameControls(int matrixValue) {
        startBtn = createButton("Start",850, 50);
        resetBtn = createButton("Reset", 850, 100);
        JSlider slider = createSlider();

        // creates a FrameBuilder which creates a GUI frame with cells
        FrameBuilder frame = new FrameBuilder(matrixValue, startBtn, resetBtn, slider);
        nodes = new ArrayList<Node>(frame.getNodes());
    }

    // creates start and reset buttons
    private JButton createButton(String name, int x, int y) {
        JButton btn = new JButton(name);
        btn.addActionListener(this);
        btn.setBounds(x, y, 100, 30);
        btn.setFocusable(false);
        btn.setOpaque(true);
        btn.setVisible(true);
        return btn;
    }

    // creates speed slider
    private JSlider createSlider() {
        slider = new JSlider(0, 120, 80);
        slider.setPaintTicks(false);
        slider.setPaintTrack(false);
        slider.addChangeListener(
                (ChangeEvent e) -> {
                        JSlider source = (JSlider) e.getSource();
                        // increment speed
                        if (!source.getValueIsAdjusting()) {
                            this.speed = (int) source.getValue();
                        }
                });

        slider.setBounds(850, 150, 150, 100);
        slider.setOpaque(true);
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
        slider.setPaintTrack(true);
        slider.setVisible(true);
        return slider;
    }

    // initiates AStar class when start button is pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startBtn) {
            // new Astar
            AStar aStar = new AStar(nodes);
            // new JTimer
            Timer timer = new Timer(speed, null);
            timer.addActionListener((e1) -> {
                if (aStar.modifiedPathFind()) {
                    timer.stop();
                }
                if (slider.getValueIsAdjusting()) {
                    speed = slider.getValue();
                    timer.setDelay(speed);
                    timer.restart();
                }
                aStar.paintChecked();
            });
            timer.start();
        } else if (e.getSource() == resetBtn) {
            for (Node n : nodes) {
                n.setToDefault();
            }
        }
    }

    // setters and getters
    public double getSpeed() {
        return speed;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setNodes(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }
}
