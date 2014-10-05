package com.southamptoncodedojo.minesweeper;

/**
 * A Game created to test players against
 */
public class TestGame {
    Game game;
    public MapInstance mapInstance;

    public TestGame(Map map, Player player) {
        game = new Game(map, new Player[]{player});
    }

    public Coordinate firstTurn() {
        game.setup();
        mapInstance = game.playersPlayingGame[0].mapInstance;
        return game.playersPlayingGame[0].firstCoordinate;
    }

    public Coordinate nextTurn() {
        if (game.gameIsOver()) {
            return null;
        }
        return game.playersPlayingGame[0].takeTurn();
    }
}
