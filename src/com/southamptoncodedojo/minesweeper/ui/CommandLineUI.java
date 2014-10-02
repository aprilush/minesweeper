package com.southamptoncodedojo.minesweeper.ui;

import com.southamptoncodedojo.minesweeper.*;
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

    int maxTextWidth;
    int textWidth;
    long roundDelay;

    public CommandLineUI(Game game, int textWidth, long roundDelay) {
        this.game = game;
        this.roundDelay = roundDelay;
        this.maxTextWidth = textWidth;
    }

    public void start() {
        game.setup();

        int widthPerMap = (game.playersPlayingGame[0].mapInstance.getSize() * 3) + 5;
        int d = maxTextWidth / widthPerMap;
        this.textWidth = (widthPerMap * d);

        System.out.println(new String(new char[textWidth]).replace("\0", "-"));
        System.out.println("Playing game with " + game.playersPlayingGame.length + " players");
        System.out.println(new String(new char[textWidth]).replace("\0", "-"));

        printState();

        while (!game.gameIsOver()) {
            game.nextRound();
            printState();
            if (roundDelay > 0) {
                try {
                    Thread.sleep(roundDelay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    int round = 1;
    /**
     * Print the current state of the game
     */
    void printState() {

        System.out.println(new String(new char[textWidth]).replace("\0", "-"));
        System.out.println("Round " + round);
        System.out.println(new String(new char[textWidth]).replace("\0", "-"));
        round += 1;

        ArrayList<String[]> formattedMaps = new ArrayList<String[]>();

        // First generate all maps
        for(PlayerPlayingGame p : game.playersPlayingGame) {
            formattedMaps.add(formatMap(p));
        }

        // Then merge them horizontally

        int widthPerMap = (game.playersPlayingGame[0].mapInstance.getSize() * 3) + 5;
        int mapsPerLine = (textWidth/widthPerMap);
        int numberOfLines = formattedMaps.get(0).length;

        int numberOfMapLines = (int) Math.ceil((double)game.playersPlayingGame.length / mapsPerLine);
        for (int x = 0 ; x < numberOfMapLines; x++ ) {
            for(int i = 0; i < numberOfLines; i++) {
                String s = "";

                int startCount = x * mapsPerLine;
                int endCount = Math.min((x + 1) * mapsPerLine, formattedMaps.size());

                for(int y = startCount; y < endCount; y++) {
                    String[] ss = formattedMaps.get(y);
                    s += "     " + ss[i];
                }

                System.out.println(s);

            }
        }
    }

    String[] formatMap(PlayerPlayingGame player) {
        MapInstance mapInstance = player.mapInstance;

        ArrayList<String> m = new ArrayList<String>();

        int width = mapInstance.getSize() * 3;

        m.add(String.format("%1$-" + width + "s", player.getName()));
        m.add(new String(new char[width]).replace("\0", "-"));

        for (int y = 0; y < mapInstance.getSize(); y++) {
            String s = "";
            for (int x = 0; x < mapInstance.getSize(); x++) {
                try {
                    Coordinate c = new Coordinate(x, y);
                    String thiss;

                    if (mapInstance.mineIsAt(c)) {
                        if (mapInstance.isFlagged(c)) {
                            thiss = " ! ";
                        } else {
                            thiss = " · ";
                        }
                    } else {
                        try {
                            thiss = " " + mapInstance.getCount(c) + " ";
                        } catch (UnknownCountException e) {
                            // We don't know - it's a space
                            if (mapInstance.isFlagged(c)) {
                                thiss = " ! ";
                            } else {
                                thiss = " · ";
                            }
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

        m.add(new String(new char[width]).replace("\0", "-"));

        String state;
        if (mapInstance.getState() == Map.State.IN_PROGRESS) {
            state = "Playing";
        } else if (mapInstance.getState() == Map.State.WIN) {
            state = "Win";
        } else {
            state = "Lose";
        }

        m.add(state + String.format("%1$" + (width - state.length()) + "s", "Turn " + player.getTurn()));
        m.add(new String(new char[width]).replace("\0", "-"));

        return m.toArray(new String[]{});
    }
}
