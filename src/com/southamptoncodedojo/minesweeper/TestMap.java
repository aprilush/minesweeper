package com.southamptoncodedojo.minesweeper;

import com.southamptoncodedojo.minesweeper.exceptions.TooManyMinesException;

/**
 * A map used for testing - you can specify the mine locations
 */
public class TestMap extends Map {

    public TestMap(int size, Coordinate[] mineLocations) {
        super(size);

        for(Coordinate c : mineLocations) {
            map[c.getX()][c.getY()] = true;
        }
    }

    @Override
    public void generate(int numberOfMines, Coordinate[] Coordinates) {
        // We don't generate - already done
    }

}
