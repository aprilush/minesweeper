package com.southamptoncodedojo.minesweeper.ui;

import com.southamptoncodedojo.minesweeper.*;
import com.southamptoncodedojo.minesweeper.exceptions.InvalidCoordinateException;
import com.southamptoncodedojo.minesweeper.exceptions.UnknownCountException;
import jlibs.core.lang.Ansi;

import java.util.ArrayList;

/**
 * The command line UI will play through an entire game until it is game over
 */
public class CommandLineUI extends MinesweeperUI implements PlayerExceptionHandler {
    Game game;  // The game being played

    // Use Ansi to format RED foreground when there is a mine
    Ansi hasMineFormatter = new Ansi(null, Ansi.Color.RED, null);
    // Green background for the last move
    Ansi lastMoveFormatter = new Ansi(null, null, Ansi.Color.GREEN);

    // The maximum width per line
    int maxTextWidth;
    // The actual width per line we're using
    int textWidth;
    // The delay between rounds
    long roundDelay;
    // Should we supress exceptions
    boolean supressExceptions;

    // This is dumped and cleared per round - and fills up with exceptions as they happen
    ArrayList<Exception> exceptionsThisRound = new ArrayList<Exception>();

    public CommandLineUI(Game game, int textWidth, long roundDelay, boolean supressExceptions) {
        this.game = game;
        this.roundDelay = roundDelay;
        this.maxTextWidth = textWidth;
        this.supressExceptions = supressExceptions;
    }

    public CommandLineUI(Game game, int textWidth, long roundDelay) {
        this(game, textWidth, roundDelay, false);
    }



    /**
     * Run the game
     */
    public void start() {
        if (supressExceptions) {
            game.setup(this);
        } else {
            game.setup();
        }

        // Calculate the usable text width
        int widthPerMap = (game.playersPlayingGame[0].mapInstance.getSize() * 3) + 5;
        int d = maxTextWidth / widthPerMap;
        this.textWidth = (widthPerMap * d);

        // Header
        System.out.println(new String(new char[textWidth]).replace("\0", "-"));
        System.out.println("Playing game with " + game.playersPlayingGame.length + " players");
        System.out.println(new String(new char[textWidth]).replace("\0", "-"));

        // Initial game state
        printState();

        while (!game.gameIsOver()) {
            game.nextRound();
            printState();
            if (roundDelay > 0) {
                // Delay if needed
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

        // Header
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
        // Ensure that we merge them on to the correct line
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

        // Then output any exceptions
        if (exceptionsThisRound.size() > 0) {
            System.out.println(new String(new char[textWidth]).replace("\0", "-"));
            System.out.println("Exceptions");
            for(Exception e : exceptionsThisRound) {
                System.out.println(e.getClass().getName() + "(" + e.getMessage() + ") at " + e.getStackTrace()[0].getClassName() + " line " + e.getStackTrace()[0].getLineNumber());
            }
            exceptionsThisRound.clear();
            System.out.println(new String(new char[textWidth]).replace("\0", "-"));
        }
    }

    /**
     * Generate a map for the given player
     * @param player
     * @return A string representation of the map
     */
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
                    String space = " ";

                    if (mapInstance.lastHit().equals(c)) {
                        space = "|";
                    }

                    if (mapInstance.mineIsAt(c)) {
                        if (mapInstance.isFlagged(c)) {
                            thiss = space + "!" + space;
                        } else {
                            thiss = space + "\u25CF" + space;
                        }
                    } else {
                        try {
                            thiss = space + mapInstance.getCount(c) + space;
                        } catch (UnknownCountException e) {
                            // We don't know - it's a space
                            if (mapInstance.isFlagged(c)) {
                                thiss = space + "\u00A1" + space;
                            } else {
                                thiss = space + "\u00B7" + space;
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
        if (mapInstance.getState() == MapInstance.State.IN_PROGRESS) {
            state = "Playing";
        } else if (mapInstance.getState() == MapInstance.State.WIN) {
            state = "Win";
        } else {
            state = "Lose";
        }

        m.add(state + String.format("%1$" + (width - state.length()) + "s", "Turn " + player.getTurn()));
        m.add(new String(new char[width]).replace("\0", "-"));

        return m.toArray(new String[]{});
    }

    @Override
    public void handleException(Player player, Exception exception) {
        // TODO: Use the player?
        exceptionsThisRound.add(exception);
    }
}
