package com.southamptoncodedojo.minesweeper.players;

import com.southamptoncodedojo.minesweeper.Coordinate;
import com.southamptoncodedojo.minesweeper.MapState;
import com.southamptoncodedojo.minesweeper.Player;
import com.southamptoncodedojo.minesweeper.exceptions.InvalidCoordinateException;

import java.util.Random;

/**
 * This is a demo Minesweeper Player.
 *
 * Copy this into your own class to implement your AI.
 *
 * Add your AI to Minesweeper.java to test it
 */
public class RandomPlayer extends Player {

    /**
     * All players must have a name
     * @return Your player's name
     */
    @Override
    public String getName() {
        return "RandomPlayer";
    }

    /**
     * Players return the coordinate they wish to click on.
     * You can return an already opened coordinate, which will have no affect
     *
     * You can use mapState.getCount to get the number of mines surrounding a particular coordinate
     * it will throw a UnknownCountException if the space has not yet been revealed. If you'd rather it return
     * -1 instead of throwing an exception, use getCountOrMinusOne.
     *
     * You can flag spaces which you think contain mines using mapState.flag(coordinate), unflag it with
     * mapState.unflag(coordinate), and check if something is flagged using mapState.isFlagged(coordinate). You can do
     * this as many times as you like per turn
     *
     * @param mapState This contains the known information about the map
     * @return The coordinate you'd like to "click on"
     */
    @Override
    public Coordinate takeTurn(MapState mapState) {
        Random r = new Random();

        // Also flag at random... because why not?
        try {
            mapState.flag(new Coordinate(r.nextInt(mapState.getSize()), r.nextInt(mapState.getSize())));
        } catch (InvalidCoordinateException e) {
            // Shouldn't happen...
            e.printStackTrace();
        }

        return new Coordinate(r.nextInt(mapState.getSize()), r.nextInt(mapState.getSize()));
    }
}
