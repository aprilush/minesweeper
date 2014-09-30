package com.southamptoncodedojo.minesweeper;

import com.southamptoncodedojo.minesweeper.exceptions.InvalidCoordinateException;
import com.southamptoncodedojo.minesweeper.exceptions.TooManyMinesException;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Contains a game where one or more AIs play Minesweeper
 */
public class Game {
    Map map;
    public PlayerPlayingGame[] playersPlayingGame;
    Player[] players;

    int size;
    int numberOfMines;

    int nextTurnIndex = 0;

    public Game(int size, int numberOfMines, Player[] players) {
        this.size = size;
        this.numberOfMines = numberOfMines;
        this.players = players;
        map = new Map(size);
    }

    public Game(Map map, Player[] players) {
        this(map.getSize(), map.getNumberOfMines(), players);
        this.map = map;
    }

    public void nextTurn() {
        if (gameIsOver()) {
            return; // TODO: Maybe raise an exception?
        }
        if (playersPlayingGame[nextTurnIndex].mapInstance.getState() == Map.State.IN_PROGRESS) {
            playersPlayingGame[nextTurnIndex].takeTurn();
        }
        nextTurnIndex += 1;
        if (nextTurnIndex >= players.length) nextTurnIndex = 0;
        if (playersPlayingGame[nextTurnIndex].mapInstance.getState() != Map.State.IN_PROGRESS) {
            nextTurn();
        }
    }

    public void nextRound() {
        if (gameIsOver()) {
            return; // TODO: Maybe raise an exception?
        }
        nextTurn();
        while (nextTurnIndex > 0) {
            nextTurn();
        }
    }

    public void setup() {
        ArrayList<PlayerPlayingGame> ppg = new ArrayList<PlayerPlayingGame>();
        for(Player p : players) {
            ppg.add(new PlayerPlayingGame(p, new MapInstance(map)));
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

    public boolean gameIsOver() {
        for(PlayerPlayingGame p : playersPlayingGame) {
            if (p.mapInstance.getState() == Map.State.IN_PROGRESS) {
                return false;
            }
        }
        return true;
    }
}
