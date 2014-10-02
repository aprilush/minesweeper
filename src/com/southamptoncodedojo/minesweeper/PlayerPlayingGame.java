package com.southamptoncodedojo.minesweeper;

import com.southamptoncodedojo.minesweeper.Coordinate;
import com.southamptoncodedojo.minesweeper.MapInstance;
import com.southamptoncodedojo.minesweeper.MapState;
import com.southamptoncodedojo.minesweeper.Player;
import com.southamptoncodedojo.minesweeper.exceptions.InvalidCoordinateException;

public class PlayerPlayingGame {
    Player player;
    public MapInstance mapInstance;
    MapState mapState;
    int turn = 1;

    public int getTurn() {
        return turn;
    }

    public PlayerPlayingGame(Player player, MapInstance mapInstance) {
        this.player = player;
        this.mapInstance = mapInstance;
        this.mapState = new MapState(mapInstance);
    }

    public Coordinate takeTurn() {
        turn += 1;
        try {
            Coordinate c = player.takeTurn(mapState);
            mapInstance.hit(c);
            return c;
        } catch (InvalidCoordinateException e) {
            // TODO: We need to deal with this exception somehow
            e.printStackTrace();
            return null;
        }
    }

    Coordinate firstCoordinate;

    public Coordinate takeFirstTurn() {
        firstCoordinate = player.takeTurn(mapState);
        return firstCoordinate;
    }

    public void completeFirstTurn() {
        try {
            mapInstance.hit(firstCoordinate);
        } catch (InvalidCoordinateException e) {
            // TODO: We need to deal with this exception somehow
            e.printStackTrace();
        }
    }

    public String getName() {
        return player.getName();
    }
}