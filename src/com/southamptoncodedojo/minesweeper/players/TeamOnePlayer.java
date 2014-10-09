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

  private final int[][] OFFSET = [
      [-1, 1],
      [-1, 0],
      [-1, -1],
      [0, 1],
      [0, 0],
      [0, -1],
      [1, 1],
      [1, 0],
      [1, -1]
    ];
	
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
			computeScores(mapState);
			Coordinate next = pickBestNext();
			return next;
		}
	}

  private int getScoreContribution(Coordinate coord, MapState mapState) {
    int score = 0;
    int numOpen = 0;
    final int width = mapState.getSize();

    for (int i = 0; i < OFFSET.length; i++) {
      final int x = mapState.getX() + OFFSET[i][0];
      final int y = mapState.getY() + OFFSET[i][1];

      if (x < 0 || y < 0 || x >= width || y >= width) {
        continue;
      }

      Coordinate coord = new Coordinate(x, y);
      if (mapState.isOpen(coord)) {
        numOpen += 1;
        score += mapState.getCount(coord);
      }
    }

    if (numOpen == 0) {
      score = 1000;
    }

    return score;
  }
	
	private void computeScores(MapState mapState)  {
      int width = mapState.getSize();
      scores = new int[width][width];

      for (int i = 0; i < width; i++) {
        for (int j = 0; j < width; j++) {
          Coordinate coord = new Coordinate(i, j);
          if (mapState.isOpen(coord)) {
            scores[i][j] = 1000;
          } else {
            scores[i][j] = getScore(coord, mapState);
          }
        }
      }
	}
	
	private Coordinate pickBestNext() {
		
	}

}
