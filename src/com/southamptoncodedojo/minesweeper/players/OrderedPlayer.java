package com.southamptoncodedojo.minesweeper.players;

import com.southamptoncodedojo.minesweeper.Coordinate;
import com.southamptoncodedojo.minesweeper.MapInstance;
import com.southamptoncodedojo.minesweeper.MapState;
import com.southamptoncodedojo.minesweeper.Player;

import java.util.Random;

/**
 * This is a demo Minesweeper Player.
 *
 * This will start at 0,0 and move in order through all squares
 */
public class OrderedPlayer extends Player {

    Coordinate nextCoordinate = new Coordinate(0,0);

    @Override
    public Coordinate takeTurn(MapState mapState) {
        Coordinate thisCoordinate = nextCoordinate;

        if (thisCoordinate.getX() == mapState.getSize() -1) {
            nextCoordinate = new Coordinate(0, thisCoordinate.getY() + 1);
        } else {
            nextCoordinate = new Coordinate(thisCoordinate.getX() + 1, thisCoordinate.getY());
        }

        return thisCoordinate;
    }
}
