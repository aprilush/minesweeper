package com.southamptoncodedojo.minesweeper;

/**
 * Abstract class which should be instantiated for each Minesweeper player
 *
 * An instance will be created for each game. So you can store state as class variables.
 */
public abstract class Player {
    /**
     * @return A unique name to identify your player
     */
    public abstract String getName();

    /**
     * In this method, you should analyse the map, mark any areas you think contain mines, and then return a
     * Coordinate to click on.
     *
     * The game will be over when you either click on a Coordinate containing a mine (Lose) or have opened every
     * Coordinate which does not contain a mine.
     *
     * @param mapState
     * @return
     */
    public abstract Coordinate takeTurn(MapState mapState);
}
