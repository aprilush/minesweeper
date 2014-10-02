package com.southamptoncodedojo.minesweeper;

import com.southamptoncodedojo.minesweeper.players.OrderedPlayer;
import com.southamptoncodedojo.minesweeper.players.RandomPlayer;
import com.southamptoncodedojo.minesweeper.ui.CommandLineUI;
import com.southamptoncodedojo.minesweeper.ui.SwingUI;

/**
 * Entry point for Minesweeper
 */
public class Minesweeper {
    public static void main(String[] args) {

        // Configure the game
        int MAP_SIZE = 10;
        int NUMBER_OF_MINES = 20;

        // For Text output
        int TEXT_WIDTH = 170; // We will wrap text longer than this to ensure it fits
        long ROUND_DELAY = 0; // Use this to slow down the game to watch it as it progresses



        Player[] players = new Player[]{new RandomPlayer(), new OrderedPlayer(),new RandomPlayer(), new OrderedPlayer(),new RandomPlayer()};

        Game game = new Game(MAP_SIZE, NUMBER_OF_MINES, players);
        // if, while testing, you want to configure the map manually, use:
        // Map m = new TestMap(MAP_SIZE, new Coordinate[]{new Coordinate(5, 5)});
        // game = new Game(m, players);
        // (this specific example will have a single mine at 5,5

        // Then use the CommandLineUI to play through an entire game quickly
        new CommandLineUI(game, TEXT_WIDTH, ROUND_DELAY).start();

        // or the SwingUI to step through a game graphically
        // new SwingUI(game).start();
    }
}
