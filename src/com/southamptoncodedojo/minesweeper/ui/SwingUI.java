package com.southamptoncodedojo.minesweeper.ui;

import com.southamptoncodedojo.minesweeper.Game;

import javax.swing.*;

/**
 * A Swing based UI for interacting with Minesweeper games
 */
public class SwingUI extends JFrame {
    public SwingUI(Game game) {
        JLabel jlbHelloWorld = new JLabel("Hello World");
        add(jlbHelloWorld);
        this.setSize(100, 100);
        pack();

    }

    public void start() {
        setVisible(true);
    }
}
