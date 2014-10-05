package com.southamptoncodedojo.minesweeper;

import com.southamptoncodedojo.minesweeper.exceptions.InvalidCoordinateException;
import com.southamptoncodedojo.minesweeper.exceptions.UnknownCountException;

/**
 * Represents a single instance of a map (typically used by a single user)
 *
 * This allows interaction with the map (clicking certain coordinates, flagging some, etc.)
 */
public class MapInstance {
    Map map;

    boolean[][] openedCoordinates; // Coordinates already opened
    boolean[][] flagged;           // Coordinates flagged

    boolean hasLost = false;

    Coordinate lastHitCoordinate = null;    // This represents the coordinate last "clicked"

    public MapInstance(Map map) {
        this.map = map;

        // nothing opened or flagged
        openedCoordinates = new boolean[map.getSize()][map.getSize()];
        flagged= new boolean[map.getSize()][map.getSize()];
    }

    MapState mapState;
    /**
     * Get a map state to abstract away access to this instance
     */
    public MapState getMapState() {
        if (mapState == null) {
            mapState = new MapState(this);
        }
        return mapState;
    }

    /**
     * This is used to do the work for hit - so that it doesn't update lastHitCoordinate
     * @param coordinate The coordinate to hit
     * @throws InvalidCoordinateException Thrown if the coordinate is out of bounds for map
     */
    private void hitI(Coordinate coordinate) throws InvalidCoordinateException {
        if (!openedCoordinates[coordinate.getX()][coordinate.getY()]) { // Only deal with things not already open

            openedCoordinates[coordinate.getX()][coordinate.getY()] = true; // Open it

            if (map.mineIsAt(coordinate)) {
                hasLost = true; // Lose if there is a mine there
            } else {
                try {
                    if (getCount(coordinate) == 0) {
                        for (Coordinate c : coordinate.getSurroundingCoordinates(map.getSize())) {
                            hitI(c); // Hit all surrounding coordinates - so we open all 0s
                        }
                    }
                } catch (UnknownCountException e) {
                    // Won't happen - we just opened this one
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Hit/click on a given coordinate
     *
     * If the coordinate contains a mine - the player loses
     *
     * If it has 0 mines surrounding it - the surrounding spaces will also be opened
     * @param coordinate The coordinate to hit
     * @throws InvalidCoordinateException Thrown if the coordinate is out of bounds for map
     */
    public void hit(Coordinate coordinate) throws InvalidCoordinateException {
        lastHitCoordinate = coordinate;
        hitI(coordinate);
    }

    /**
     * Add a flag on a given coordinate (indicating you think there is a mine there). If there is already a flag
     * there this will do nothing.
     *
     * You can add as many flags as you like on your turn.
     * @param coordinate
     * @throws InvalidCoordinateException
     */
    public void flag(Coordinate coordinate) throws InvalidCoordinateException {
        if (!coordinate.isValid(map.getSize())) {
            throw new InvalidCoordinateException("Invalid coordinate:" + coordinate.toString());
        }
        flagged[coordinate.getX()][coordinate.getY()] = true;
    }

    /**
     * Remove a flag from a given coordinate. If there is no flag there this will do nothing.
     *
     * You can remove as many flags as you like on your turn.
     * @param coordinate
     * @throws InvalidCoordinateException
     */
    public void unFlag(Coordinate coordinate) throws InvalidCoordinateException {
        if (!coordinate.isValid(map.getSize())) {
            throw new InvalidCoordinateException("Invalid coordinate:" + coordinate.toString());
        }
        flagged[coordinate.getX()][coordinate.getY()] = false;
    }

    /**
     * @param coordinate
     * @return True if there a flag at the given coordinate
     * @throws InvalidCoordinateException
     */
    public boolean isFlagged(Coordinate coordinate) throws InvalidCoordinateException {
        if (!coordinate.isValid(map.getSize())) {
            throw new InvalidCoordinateException("Invalid coordinate:" + coordinate.toString());
        }
        return flagged[coordinate.getX()][coordinate.getY()];
    }

    /**
     * Get the number of mines surrounding the given coordinate.
     * @param coordinate
     * @return The number of mines surrounding the given coordinate.
     * @throws UnknownCountException This will be thrown if the coordinate has not been opened yet
     * @throws InvalidCoordinateException
     */
    public int getCount(Coordinate coordinate) throws UnknownCountException, InvalidCoordinateException {
        if (!openedCoordinates[coordinate.getX()][coordinate.getY()]) {
            throw new UnknownCountException("We don't know the count for this coordinate");
        }

        return map.getCount(coordinate);
    }

    /**
     * Get the number of spaces which have not yet been opened and do not contain mines.
     *
     * This will be 0 when the map has been won.
     *
     * @return Get the number of spaces which have not yet been opened and do not contain mines.
     */
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

    /**
     * @return The state of the map, WIN/LOSE/InProgress
     */
    public State getState() {
        if (hasLost) {
            return State.LOSE;
        } else if (getRemainingCoordinates() == 0) {
            return State.WIN;
        } else {
            return State.IN_PROGRESS;
        }
    }

    /**
     * @param coordinate
     * @return True if there is a mine at the given coordinate
     * @throws InvalidCoordinateException
     */
    public boolean mineIsAt(Coordinate coordinate) throws InvalidCoordinateException {
        return map.mineIsAt(coordinate);
    }

    /**
     * @return The size of the map
     */
    public int getSize() {
        return map.getSize();
    }

    /**
     * Which coordinate was last clicked on? This only counts coordinates actually chosen by returning them from the
     * takeTurn() method.
     *
     * It does not include coordinates which have been opened automatically due to cascading zeros.
     *
     * @return
     */
    public Coordinate lastHit() {
        return lastHitCoordinate;
    }

    /**
     * @param coordinate
     * @return Has the given coordinate been opened?
     * @throws InvalidCoordinateException
     */
    public boolean isOpen(Coordinate coordinate) throws InvalidCoordinateException {
        if (!coordinate.isValid(getSize())) {
            throw new InvalidCoordinateException("Invalid Coordinate");
        }
        return openedCoordinates[coordinate.getX()][coordinate.getY()];
    }

    /**
     * The possible states a player can be in
     */
    public enum State {
        WIN,
        LOSE,
        IN_PROGRESS
    }
}
