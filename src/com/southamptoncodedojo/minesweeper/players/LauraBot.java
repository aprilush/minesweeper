package com.southamptoncodedojo.minesweeper.players;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.southamptoncodedojo.minesweeper.Coordinate;
import com.southamptoncodedojo.minesweeper.MapState;
import com.southamptoncodedojo.minesweeper.Player;
import com.southamptoncodedojo.minesweeper.exceptions.InvalidCoordinateException;
import com.southamptoncodedojo.minesweeper.exceptions.UnknownCountException;

public class LauraBot extends Player {

	private boolean firstRun;
	private List<Coordinate> safe;

	public LauraBot() {
		firstRun = true;
		safe = new ArrayList<Coordinate>();
	}
	
	@Override
	public String getName() {
		return "LauraBot";
	}

	@Override
	public Coordinate takeTurn(MapState mapState) {
		if (firstRun) {
			this.firstRun = false;
			return new Coordinate(0, 0);
		} else {
			findBombs(mapState);
			findSafe(mapState);
			Coordinate next = pickNext(mapState);
			return next;
		}
	}

	private Coordinate pickNext(MapState mapState) {
		if (safe.size()>0) {
			return safe.remove(0);
		}
		Random r = new Random();
		while (true) {
			try {
				Coordinate c = new Coordinate(r.nextInt(mapState.getSize()), r.nextInt(mapState.getSize()));
				if (!mapState.isOpen(c) && !mapState.isFlagged(c)) {
					return c;
				}
			} catch (InvalidCoordinateException e) {
				e.printStackTrace();
			}
		}
	}

	private void findSafe(MapState mapState) {
		int width = mapState.getSize();
		safe = new ArrayList<Coordinate>();
		for (int i=0;i<width;i++) {
			for (int j=0;j<width;j++) {
				try {
					Coordinate c = new Coordinate(i, j);
					if (!mapState.isOpen(c) && !mapState.isFlagged(c)) {
						Coordinate[] neighbours = c.getSurroundingCoordinates(width);
						for (Coordinate n : neighbours) {
							if (needsBombs(n, mapState) == 0) {
								safe.add(c);
								break;
							}
						}
					}
				} catch (InvalidCoordinateException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void findBombs(MapState mapState) {
		int width = mapState.getSize();
		for (int i=0;i<width;i++) {
			for (int j=0;j<width;j++) {
				try {
					Coordinate c = new Coordinate(i, j);
					if (!mapState.isOpen(c) && !mapState.isFlagged(c)) {
						Coordinate[] neighbours = c.getSurroundingCoordinates(width);
						for (Coordinate n : neighbours) {
							int needsBombs = needsBombs(n, mapState);
							List<Coordinate> unopenedNeighbours = getUnopenedNeighbours(n, mapState);
							if (needsBombs == unopenedNeighbours.size()) {
								for (Coordinate uon : unopenedNeighbours) {
									mapState.flag(uon);
								}
							}
						}
					}
				} catch (InvalidCoordinateException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private List<Coordinate> getUnopenedNeighbours(Coordinate c, MapState mapState) {
		List<Coordinate> uon = new ArrayList<Coordinate>();
		for (Coordinate n : c.getSurroundingCoordinates(mapState.getSize())) {
			try {
				if (!mapState.isOpen(n) && !mapState.isFlagged(n)) {
					uon.add(n);
				}
			} catch (InvalidCoordinateException e) {
				e.printStackTrace();
			}
		}
		return uon;
	}

	private int needsBombs(Coordinate c, MapState mapState) throws InvalidCoordinateException {
		if (mapState.isOpen(c)) {
			try {
				int bombsNeeded = mapState.getCount(c);
				Coordinate[] neighbours = c.getSurroundingCoordinates(mapState.getSize());
				for (Coordinate n : neighbours) {
					if (mapState.isFlagged(n)) {
						bombsNeeded--;
					}
				}
				return bombsNeeded;
			} catch (UnknownCountException e) {
				// shouldn't happen
				e.printStackTrace();
			}
		}
		return -1;
	}
	
}
