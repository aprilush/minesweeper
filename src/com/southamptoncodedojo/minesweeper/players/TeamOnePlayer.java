package com.southamptoncodedojo.minesweeper.players;

import java.util.Random;

import com.southamptoncodedojo.minesweeper.Coordinate;
import com.southamptoncodedojo.minesweeper.MapInstance;
import com.southamptoncodedojo.minesweeper.MapState;
import com.southamptoncodedojo.minesweeper.Player;
import com.southamptoncodedojo.minesweeper.exceptions.InvalidCoordinateException;

public class TeamOnePlayer extends Player {

	private int remainingMines;
	private int[][] scores;
	
	public TeamOnePlayer() {
		remainingMines = -1;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Team One";
	}

	@Override
	public Coordinate takeTurn(MapState mapState) {
		if (remainingMines < 1) {
			// first run!
			remainingMines = mapState.getNumberOfMines();
			System.out.println("number of mines "+remainingMines);
			return new Coordinate(0, 0);
		} else {
			computeScores();
			Coordinate next = pickBestNext();
			return next;
		}
	}
	
	private void computeScores()  {
		
	}
	
	private Coordinate pickBestNext() {
		
	}

}
