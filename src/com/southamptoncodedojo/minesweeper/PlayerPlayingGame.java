package com.southamptoncodedojo.minesweeper;

import com.southamptoncodedojo.minesweeper.exceptions.InvalidCoordinateException;

/**
 * This encapsulates a Player in a Game
 */
public class PlayerPlayingGame {
    Player player;
    public MapInstance mapInstance;
    PlayerExceptionHandler exceptionHandler;
    int turn = 1; // The current turn number of the player

    /**
     * @return The current turn number of the player
     */
    public int getTurn() {
        return turn;
    }

    public PlayerPlayingGame(Player player, MapInstance mapInstance) {
        this.player = player;
        this.mapInstance = mapInstance;
    }

    public PlayerPlayingGame(Player player, MapInstance mapInstance, PlayerExceptionHandler exceptionHandler) {
        this(player, mapInstance);
        this.exceptionHandler = exceptionHandler;
    }

    /**
     * Have the player take their turn, and apply the results to the mapInstance
     * @return The coordinate the player chose
     */
    public Coordinate takeTurn() {
        turn += 1;
        try {
            Coordinate c;
            if (exceptionHandler != null) {
                try {
                    c = player.takeTurn(mapInstance);
                } catch (Exception e) {
                    exceptionHandler.handleException(player, e);
                    return null;
                }
            } else {
                c = player.takeTurn(mapInstance);
            }
            mapInstance.hit(c);
            return c;
        } catch (InvalidCoordinateException e) {
            // TODO: We need to deal with this exception somehow
            e.printStackTrace();
            return null;
        }
    }

    Coordinate firstCoordinate;

    /**
     * Have the player take their first turn, and save the result so it can be completed with
     * completeFirstTurn
     *
     * (This is because the first turn must happen before the map is generated - to ensure that they
     *  do not click on a mine)
     * @return The coordinate the player chose
     */
    public Coordinate takeFirstTurn() {
        // Here we choose not to catch exceptions as it's right at the start of the game so if something is failing here
        // it's worth just dropping them from the game
        firstCoordinate = player.takeTurn(mapInstance);
        return firstCoordinate;
    }

    /**
     * Complete the first turn - this must be called after takeFirstTurn() and should be called after the map has
     * been generated
     */
    public void completeFirstTurn() {
        try {
            mapInstance.hit(firstCoordinate);
        } catch (InvalidCoordinateException e) {
            // TODO: We need to deal with this exception somehow
            e.printStackTrace();
        }
    }

    /**
     * @return The player's name
     */
    public String getName() {
        return player.getName();
    }
}