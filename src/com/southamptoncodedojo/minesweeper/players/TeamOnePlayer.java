package com.southamptoncodedojo.minesweeper.players;

import com.southamptoncodedojo.minesweeper.Coordinate;
import com.southamptoncodedojo.minesweeper.MapState;
import com.southamptoncodedojo.minesweeper.Player;
import com.southamptoncodedojo.minesweeper.exceptions.InvalidCoordinateException;

public class TeamOnePlayer extends Player {

	private boolean firstRun;
	private int[][] scores;
	
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
			computeScores();
			Coordinate next = pickBestNext(mapState);
			return next;
		}
	}
	
	private void computeScores()  {
		
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
