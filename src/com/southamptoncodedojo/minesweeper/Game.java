package com.southamptoncodedojo.minesweeper;

import com.southamptoncodedojo.minesweeper.exceptions.TooManyMinesException;

import java.util.ArrayList;

/**
 * Contains a game where one or more AIs play Minesweeper
 */
public class Game {
    Map map;
    public PlayerPlayingGame[] playersPlayingGame; // Encapsulates state for a single player on a single map instance
    Player[] players; // All of the AIs

    int size; // The size of the map
    int numberOfMines; // The number of mines on the map

    int nextTurnIndex = 0; // Which player will take their turn next

    /**
     * Create a new game
     * @param size The size of the map
     * @param numberOfMines The number of mines on the map
     * @param players The Players to play
     */
    public Game(int size, int numberOfMines, Player[] players) {
        this.size = size;
        this.numberOfMines = numberOfMines;
        this.players = players;
        map = new Map(size);
    }

    /**
     * Create a new game, on a pre-created map
     * @param map The map to be used
     * @param players The Players to play
     */
    public Game(Map map, Player[] players) {
        this(map.getSize(), map.getNumberOfMines(), players);
        this.map = map;
    }

    /**
     * The player who's turn it is will take their turn, and the effects will be applied to the board
     *
     * This will not do anything if the game is over, and it will skip any players who have already lost
     * this game.
     */
    public void nextTurn() {
        if (gameIsOver()) {
            nextTurnIndex = lowestActivePlayer();
            return; // TODO: Maybe raise an exception?
        }
        if (playersPlayingGame[nextTurnIndex].mapInstance.getState() == MapInstance.State.IN_PROGRESS) {
            playersPlayingGame[nextTurnIndex].takeTurn();
        }
        nextTurnIndex += 1;
        if (nextTurnIndex >= players.length) nextTurnIndex = lowestActivePlayer();
        if (nextTurnIndex < 0) return;
        if (playersPlayingGame[nextTurnIndex].mapInstance.getState() != MapInstance.State.IN_PROGRESS) {
            nextTurn();
        }
    }

    /**
     * @return The lowest index of a player who is still playing the game
     */
    int lowestActivePlayer() {
        int i = 0;
        while (playersPlayingGame[i].mapInstance.getState() != MapInstance.State.IN_PROGRESS) {
            i += 1;
            if (i >= playersPlayingGame.length) {
                return -1;
            }
        }
        return i;
    }

    /**
     * Get players to make a round of turns at a time
     * (Every still active player will take a turn)
     *
     * This will not do anything if the game is over, and it will skip any players who have already lost
     * this game.
     */
    public void nextRound() {
        if (gameIsOver()) {
            return; // TODO: Maybe raise an exception?
        }

        // Get number of active players
        int activePlayers = 0;
        for (int i = 0; i < playersPlayingGame.length; i++) {
            if (playersPlayingGame[i].mapInstance.getState() == MapInstance.State.IN_PROGRESS) {
                activePlayers += 1;
            }
        }

        for (int i = 0; i < activePlayers; i++) {
            nextTurn();
        }
    }

    /**
     * Set up the game.
     *
     * Generate the map, and get every player to take their first turn.
     *
     * THIS MUST BE CALLED BEFORE ANYTHING ELSE
     */
    public void setup() {
        setup(null);
    }

    /**
     * Set up the game, providing an exception handler to deal with Player exceptions.
     *
     * Generate the map, and get every player to take their first turn.
     *
     * THIS MUST BE CALLED BEFORE ANYTHING ELSE
     */
    public void setup(PlayerExceptionHandler exceptionHandler) {
        ArrayList<PlayerPlayingGame> ppg = new ArrayList<PlayerPlayingGame>();
        for(Player p : players) {
            if (exceptionHandler != null) {
                ppg.add(new PlayerPlayingGame(p, new MapInstance(map), exceptionHandler));
            } else {
                ppg.add(new PlayerPlayingGame(p, new MapInstance(map)));
            }
        }
        playersPlayingGame = ppg.toArray(new PlayerPlayingGame[]{});

        ArrayList<Coordinate> hits = new ArrayList<Coordinate>();
        for(PlayerPlayingGame p : playersPlayingGame) {
            Coordinate c = p.takeFirstTurn();
            hits.add(c);
        }

        try {
            map.generate(numberOfMines, hits.toArray(new Coordinate[]{}));
        } catch (TooManyMinesException e) {
            // TODO: Figure out what we do if this happens
            e.printStackTrace();
        }

        for(PlayerPlayingGame p : playersPlayingGame) {
            p.completeFirstTurn();
        }
    }

    /**
     * @return Has every player either won or lost the map?
     */
    public boolean gameIsOver() {
        for(PlayerPlayingGame p : playersPlayingGame) {
            if (p.mapInstance.getState() == MapInstance.State.IN_PROGRESS) {
                return false;
            }
        }
        return true;
    }
}
