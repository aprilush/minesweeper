package com.southamptoncodedojo.minesweeper;

/**
 * Abstract class which should be instantiated for each Minesweeper player
 */
public abstract class Player {
    public abstract String getName();
    public abstract Coordinate takeTurn(MapState mapState);
}
