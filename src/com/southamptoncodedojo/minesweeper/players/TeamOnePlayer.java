package com.southamptoncodedojo.minesweeper.players;

import com.southamptoncodedojo.minesweeper.Coordinate;
import com.southamptoncodedojo.minesweeper.MapState;
import com.southamptoncodedojo.minesweeper.Player;
import com.southamptoncodedojo.minesweeper.exceptions.InvalidCoordinateException;

public class TeamOnePlayer extends Player {

	private boolean firstRun;
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
		firstRun = true;
	}
	
	@Override
	public String getName() {
		return "Team One";
	}

	@Override
	public Coordinate takeTurn(MapState mapState) {
		if (firstRun) {
			// first run!
			// can do this randomly too!
			return new Coordinate(0, 0);
		} else {
			if (mapState.getRemainingCoordinates()==mapState.getNumberOfMines()) {
				for (int i=0;i<mapState.getSize();i++) {
					for (int j=0;j<mapState.getSize();j++) {
						try {
							Coordinate c = new Coordinate(i, j);
							if (!mapState.isOpen(c)) {
								mapState.flag(c);
							}
						} catch (InvalidCoordinateException e) {
							e.printStackTrace();
						}						
					}
				}
			}
			computeScores(mapState);
			Coordinate next = pickBestNext(mapState);
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
	
	private Coordinate pickBestNext(MapState mapState) {
		
		int imin = 11;
		int jmin = 11;
		int min = 11;
		for (int i=0;i<mapState.getSize();i++) {
			for (int j=0;j<mapState.getSize();j++) {
				if (min > scores[i][j]) {
					min = scores[i][j];
					imin = i;
					jmin = j;
				}
			}
		}
		return new Coordinate(imin, jmin);
	}

}
