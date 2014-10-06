package com.southamptoncodedojo.minesweeper.ui;

import com.southamptoncodedojo.minesweeper.Game;
import com.southamptoncodedojo.minesweeper.PlayerPlayingGame;

import javax.swing.*;
import java.awt.*;

class PlayerMap extends JPanel {
    PlayerPlayingGame ppg;
    public PlayerMap(PlayerPlayingGame ppg) {
        this.ppg = ppg;
        JLabel l = new JLabel(ppg.getName());
        add(l);
    }
}

class PlayerMaps extends JPanel {
    Game game;
    public PlayerMaps(Game game) {
        this.game = game;

        setLayout(new GridLayout());
        // For each player create a PlayerMap
        for (PlayerPlayingGame ppg : game.playersPlayingGame) {
            add(new PlayerMap(ppg));
        }
    }
}

class ControlPanel extends JPanel {
    Game game;
    public ControlPanel(Game game) {
        this.game = game;

        add(new JButton("Next"));
    }
}

/**
 * A Swing based UI for interacting with Minesweeper games
 */
public class SwingUI extends JFrame {
    Game game;
    boolean suppress_exceptions;

    public SwingUI(Game game, boolean suppress_exceptions) {
        this.game = game;
        this.suppress_exceptions = suppress_exceptions;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Minesweeper");
        setLayout(new GridLayout(2,0));
    }

    public void start() {
        game.setup();

        add(new PlayerMaps(game));
        add(new ControlPanel(game));

        pack();
        setVisible(true);
    }
}
