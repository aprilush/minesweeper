package com.southamptoncodedojo.minesweeper;

import com.southamptoncodedojo.minesweeper.exceptions.InvalidCoordinateException;
import com.southamptoncodedojo.minesweeper.exceptions.UnknownCountException;
import com.sun.org.apache.bcel.internal.classfile.Unknown;

/**
 * This class abstracts away the current map state
 *
 * it will always refer to the latest state - so it can not be stored to be compared to later
 */
public class MapState {
    MapInstance mapInstance;
    public MapState(MapInstance mapInstance) {
        this.mapInstance = mapInstance;
    }

    /**
     * @return The size of the map
     */
    public int getSize() {
        return mapInstance.map.getSize();
    }

    /**
     * Get the number of mines surrounding the given coordinate.
     * @param coordinate
     * @return The number of mines surrounding the given coordinate.
     * @throws UnknownCountException This will be thrown if the coordinate has not been opened yet
     * @throws InvalidCoordinateException
     */
    public int getCount(Coordinate coordinate) throws InvalidCoordinateException, UnknownCountException {
        return mapInstance.getCount(coordinate);
    }

    /**
     * Get the number of spaces which have not yet been opened and do not contain mines.
     *
     * This will be 0 when the map has been won.
     *
     * @return Get the number of spaces which have not yet been opened and do not contain mines.
     */
    public int getRemainingCoordinates() {
        return mapInstance.getRemainingCoordinates();
    }

    /**
     * Add a flag on a given coordinate (indicating you think there is a mine there). If there is already a flag
     * there this will do nothing.
     *
     * You can add as many flags as you like on your turn.
     * @param coordinate
     * @throws InvalidCoordinateException
     */
    public void flag(Coordinate coordinate) throws InvalidCoordinateException { mapInstance.flag(coordinate); }

    /**
     * Remove a flag from a given coordinate. If there is no flag there this will do nothing.
     *
     * You can remove as many flags as you like on your turn.
     * @param coordinate
     * @throws InvalidCoordinateException
     */
    public void unFlag(Coordinate coordinate) throws InvalidCoordinateException { mapInstance.unFlag(coordinate); }

    /**
     * @param coordinate
     * @return True if there a flag at the given coordinate
     * @throws InvalidCoordinateException
     */
    public boolean isFlagged(Coordinate coordinate) throws InvalidCoordinateException { return mapInstance.isFlagged(coordinate); }

    /**
     * @param coordinate
     * @return Has the given coordinate been opened?
     * @throws InvalidCoordinateException
     */
    public boolean isOpen(Coordinate coordinate) throws InvalidCoordinateException { return mapInstance.isOpen(coordinate); }
}