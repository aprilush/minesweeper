package com.southamptoncodedojo.minesweeper.players;

import com.southamptoncodedojo.minesweeper.Coordinate;
import com.southamptoncodedojo.minesweeper.MapInstance;
import com.southamptoncodedojo.minesweeper.MapState;
import com.southamptoncodedojo.minesweeper.Player;

import java.util.Random;

/**
 * This is a demo Minesweeper Player.
 *
 * Copy this into your own class to implement your AI.
 *
 * Add your AI to Minesweeper.java to test it
 */
public class RandomPlayer extends Player {

    @Override
    public Coordinate takeTurn(MapState mapState) {
        Random r = new Random();

        return new Coordinate(r.nextInt(mapState.getSize()), r.nextInt(mapState.getSize()));
    }
}
