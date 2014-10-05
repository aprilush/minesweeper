package com.southamptoncodedojo.minesweeper;

import com.southamptoncodedojo.minesweeper.exceptions.InvalidCoordinateException;
import com.southamptoncodedojo.minesweeper.exceptions.TooManyMinesException;
import com.southamptoncodedojo.minesweeper.exceptions.UnknownCountException;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;

/**
 * A Minesweeper map
 */
public class Map {
    // The map - True indicates that there is a mine there - false indicates there isn't
    protected boolean[][] map;

    // The size of the map (they are always square)
    private int size;

    /**
     * Generate the position of the mines on the map
     * @param numberOfMines The number of mines to generate
     * @throws TooManyMinesException Thrown if the mines cannot fit on the map
     */
    protected void generate(int numberOfMines) throws TooManyMinesException {
        generate(numberOfMines, new Coordinate[]{});
    }

    /**
     * Generate the position of the mines on the map
     * @param numberOfMines The number of mines to generate
     * @param initialHits The coordinates where the players will click first - so avoid putting mines around these points
     * @throws TooManyMinesException Thrown if the mines cannot fit on the map
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

    /**
     * Create a map
     * @param size The width and height of the map
     */
    public Map(int size) {
        this.size = size;
        map = new boolean[size][size];
    }

    public int getSize() {
        return size;
    }

    /**
     * @param coordinate The coordinate to check
     * @return Is there a mine at that coordinate
     * @throws InvalidCoordinateException If the coordinate is not within the bounds of this map
     */
    public boolean mineIsAt(Coordinate coordinate) throws InvalidCoordinateException {
        if (!coordinate.isValid(size)) {
            throw new InvalidCoordinateException("Invalid coordinate:" + coordinate.toString());
        }
        return map[coordinate.getX()][coordinate.getY()];
    }

    /**
     * @return The number of mines on the map
     */
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

    /**
     * Get the number of mines surrounding the given coordinate.
     * @param coordinate
     * @return The number of mines surrounding the given coordinate.
     * @throws InvalidCoordinateException
     */
    public int getCount(Coordinate coordinate) throws InvalidCoordinateException {
        int count = 0;
        for(Coordinate c : coordinate.getSurroundingCoordinates(getSize())) {
            if (mineIsAt(c)) {
                count += 1;
            }
        }
        return count;
    }
}
