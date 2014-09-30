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

    public int getSize() {
        return mapInstance.map.getSize();
    }

    public int getCount(Coordinate c) throws InvalidCoordinateException, UnknownCountException {
        return mapInstance.getCount(c);
    }

    public int getCountOrMinusOne(Coordinate c) throws InvalidCoordinateException {
        try {
            return mapInstance.getCount(c);
        } catch (UnknownCountException e) {
            return -1;
        }
    }

    public int getRemainingCoordinates() {
        return mapInstance.getRemainingCoordinates();
    }
}