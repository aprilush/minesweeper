package com.southamptoncodedojo.minesweeper.ui;

import com.southamptoncodedojo.minesweeper.Coordinate;
import com.southamptoncodedojo.minesweeper.Game;
import com.southamptoncodedojo.minesweeper.MapInstance;
import com.southamptoncodedojo.minesweeper.exceptions.InvalidCoordinateException;
import com.southamptoncodedojo.minesweeper.exceptions.UnknownCountException;
import jlibs.core.lang.Ansi;

import java.util.ArrayList;

/**
 * The command line UI will play through an entire game until it is game over
 */
public class CommandLineUI extends MinesweeperUI {
    Game game;

    Ansi hasMineFormatter = new Ansi(null, Ansi.Color.RED, null);
    Ansi lastMoveFormatter = new Ansi(null, null, Ansi.Color.GREEN);

    public CommandLineUI(Game game) {
        this.game = game;
    }

    public void start() {
        game.setup();

        System.out.println("---------------------");
        System.out.println("Playing game with " + game.playersPlayingGame.length + " players");
        System.out.println("---------------------");

        printState();

        while (!game.gameIsOver()) {
            game.nextRound();
            printState();
        }
    }

    int i = 0;
    /**
     * Print the current state of the game
     */
    void printState() {
        String[] map1 = formatMap(game.playersPlayingGame[0].mapInstance);
        for(String s : map1) {
            System.out.println(s);
        }
        System.out.println("------------------------------");
    }

    String[] formatMap(MapInstance mapInstance) {
        ArrayList<String> m = new ArrayList<String>();

        for (int y = 0; y < mapInstance.getSize(); y++) {
            String s = "";
            for (int x = 0; x < mapInstance.getSize(); x++) {
                try {
                    Coordinate c = new Coordinate(x, y);
                    String thiss;

                    if (mapInstance.mineIsAt(c)) {
                        thiss = " . ";
                    } else {
                        try {
                            thiss = " " + mapInstance.getCount(c) + " ";
                        } catch (UnknownCountException e) {
                            // We don't know - it's a space
                            thiss = " . ";
                        }
                    }

                    if (mapInstance.mineIsAt(c)) {
                        thiss = hasMineFormatter.colorize(thiss);
                    }

                    if (mapInstance.lastHit().equals(c)) {
                        thiss = lastMoveFormatter.colorize(thiss);
                    }
                    s += thiss;
                } catch (InvalidCoordinateException e) {
                    // Shouldn't happen
                    e.printStackTrace();
                }
            }
            m.add(s);
        }

        return m.toArray(new String[]{});
    }
}
