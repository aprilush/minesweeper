package com.southamptoncodedojo.minesweeper;

import com.southamptoncodedojo.minesweeper.exceptions.InvalidCoordinateException;
import com.southamptoncodedojo.minesweeper.exceptions.UnknownCountException;

/**
 * An instance of a map which can be worked with (marking which areas have been viewed, etc.)
 */
public class MapInstance {
    Map map;

    boolean[][] openedCoordinates;
    boolean[][] flagged;

    boolean hasLost = false;

    Coordinate lastHitCoordinate = null;

    public MapInstance(Map map) {
        this.map = map;

        openedCoordinates = new boolean[map.getSize()][map.getSize()];
        flagged= new boolean[map.getSize()][map.getSize()];
    }

    private void hitI(Coordinate coordinate) throws InvalidCoordinateException {
        if (!openedCoordinates[coordinate.getX()][coordinate.getY()]) {

            openedCoordinates[coordinate.getX()][coordinate.getY()] = true;

            if (map.mineIsAt(coordinate)) {
                hasLost = true;
            } else {
                try {
                    if (getCount(coordinate) == 0) {
                        for (Coordinate c : coordinate.getSurroundingCoordinates(map.getSize())) {
                            hitI(c);
                        }
                    }
                } catch (UnknownCountException e) {
                    // Won't happen - we just opened this one
                    e.printStackTrace();
                }
            }
        }
    }

    public void flag(Coordinate coordinate) throws InvalidCoordinateException {
        if (!coordinate.isValid(map.getSize())) {
            throw new InvalidCoordinateException("Invalid coordinate:" + coordinate.toString());
        }
        flagged[coordinate.getX()][coordinate.getY()] = true;
    }

    public void unFlag(Coordinate coordinate) throws InvalidCoordinateException {
        if (!coordinate.isValid(map.getSize())) {
            throw new InvalidCoordinateException("Invalid coordinate:" + coordinate.toString());
        }
        flagged[coordinate.getX()][coordinate.getY()] = false;
    }

    public boolean isFlagged(Coordinate coordinate) throws InvalidCoordinateException {
        if (!coordinate.isValid(map.getSize())) {
            throw new InvalidCoordinateException("Invalid coordinate:" + coordinate.toString());
        }
        return flagged[coordinate.getX()][coordinate.getY()];
    }

    public void hit(Coordinate coordinate) throws InvalidCoordinateException {
        lastHitCoordinate = coordinate;
        hitI(coordinate);
    }

    public int getCount(Coordinate coordinate) throws UnknownCountException, InvalidCoordinateException {
        if (!openedCoordinates[coordinate.getX()][coordinate.getY()]) {
            throw new UnknownCountException("We don't know the count for this coordinate");
        }

        int count = 0;
        for(Coordinate c : coordinate.getSurroundingCoordinates(map.getSize())) {
            if (map.mineIsAt(c)) {
                count += 1;
            }
        }
        return count;
    }

    public int getRemainingCoordinates() {
        int o = 0;
        for(int x = 0; x < map.getSize(); x++) {
            for (int y = 0; y < map.getSize(); y++) {
                if (openedCoordinates[x][y]) {
                    o += 1;
                }
            }
        }

        return (map.getSize() * map.getSize()) - o - map.getNumberOfMines();
    }

    public Map.State getState() {
        if (hasLost) {
            return Map.State.LOSE;
        } else if (getRemainingCoordinates() == 0) {
            return Map.State.WIN;
        } else {
            return Map.State.IN_PROGRESS;
        }
    }

    public boolean mineIsAt(Coordinate coordinate) throws InvalidCoordinateException {
        return map.mineIsAt(coordinate);
    }

    public int getSize() {
        return map.getSize();
    }

    public Coordinate lastHit() {
        return lastHitCoordinate;
    }
}
