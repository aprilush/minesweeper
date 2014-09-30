package com.southamptoncodedojo.minesweeper;

import com.southamptoncodedojo.minesweeper.exceptions.InvalidCoordinateException;
import com.southamptoncodedojo.minesweeper.exceptions.TooManyMinesException;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;

/**
 * A Minesweeper map
 */
public class Map {
    // The map - True indicates that there is a mine there - false indicates there isn't
    protected boolean[][] map;

    private int size;

    public enum State {
        WIN,
        LOSE,
        IN_PROGRESS
    }

    protected void generate(int numberOfMines) throws TooManyMinesException {
        generate(numberOfMines, new Coordinate[]{});
    }

    /**
     * Position the mines appropriately
     */
    protected void generate(int numberOfMines, Coordinate[] initialHits) throws TooManyMinesException {
        ArrayList<Coordinate> possibleCoordinates = new ArrayList<Coordinate>();

        // Generate all possible
        for(int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                possibleCoordinates.add(new Coordinate(x,y));
            }
        }

        Collections.shuffle(possibleCoordinates);

        // Remove initialHits
        for(Coordinate h : initialHits) {
            possibleCoordinates.remove(h);
            // And remove the areas immediately surrounding this
            for(Coordinate h1 : h.getSurroundingCoordinates(size)) {
                possibleCoordinates.remove(h1);
            }
        }


        for(int i = 0; i < numberOfMines; i++) {
            if (possibleCoordinates.size() == 0) {
                throw new TooManyMinesException("Too many mines. Increase map size or reduce number of mines");
            }

            Coordinate mineCoordinate = possibleCoordinates.get(0);

            map[mineCoordinate.getX()][mineCoordinate.getY()] = true;

            possibleCoordinates.remove(0);
        }
    }

    public Map(int size) {
        this.size = size;
        map = new boolean[size][size];
    }

    public int getSize() {
        return size;
    }

    public boolean mineIsAt(Coordinate coordinate) throws InvalidCoordinateException {
        if (!coordinate.isValid(size)) {
            throw new InvalidCoordinateException("Invalid coordinate:" + coordinate.toString());
        }
        return map[coordinate.getX()][coordinate.getY()];
    }

    public int getNumberOfMines() {
        int numberOfMines = 0;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (map[x][y]) {
                    numberOfMines += 1;
                }
            }
        }
        return numberOfMines;
    }
}
