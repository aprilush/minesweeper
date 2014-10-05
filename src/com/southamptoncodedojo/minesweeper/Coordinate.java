package com.southamptoncodedojo.minesweeper;

import java.util.ArrayList;

/**
 * Represents a 2D coordinate
 */
public class Coordinate {
    int x;
    int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean equals(Object other) {
        Coordinate c = (Coordinate)other;
        return (c.getX() == x && c.getY() == y);
    }

    /**
     * Get a list of coordinate surrounding this one, for the given map size
     * @param size Map size
     * @return An array of surrounding coordinates
     */
    public Coordinate[] getSurroundingCoordinates(int size) {
        ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
        // Above
        if (y > 0) {
            coordinates.add(new Coordinate(x, y-1));
        }
        // Above right
        if (y > 0 && x < (size - 1)) {
            coordinates.add(new Coordinate(x + 1, y-1));
        }
        // Right
        if (x < (size - 1)) {
            coordinates.add(new Coordinate(x + 1, y));
        }
        // Down Right
        if (y < (size - 1) && x < (size - 1)) {
            coordinates.add(new Coordinate(x+1, y+1));
        }
        // Down
        if (y < (size - 1)) {
            coordinates.add(new Coordinate(x, y+1));
        }
        // Down Left
        if (y < (size - 1) && x > 0) {
            coordinates.add(new Coordinate(x-1, y+1));
        }
        // Left
        if (x > 0) {
            coordinates.add(new Coordinate(x-1, y));
        }
        // Above Left
        if (y > 0 && x > 0) {
            coordinates.add(new Coordinate(x-1, y-1));
        }
        return coordinates.toArray(new Coordinate[]{});
    }

    public String toString() {
        return "Coordinate<" + x + "," + y + ">";
    }

    /**
     * Is this a valid coordinate for the given map size
     *
     * (e.g. with a coordinate at 10,10 isValid(10) would return false, but isValid(11) would return true.)
     * @param size
     * @return
     */
    public boolean isValid(int size) {
        return (x >= 0 && x < size && y >= 0 && y < size);
    }
}
