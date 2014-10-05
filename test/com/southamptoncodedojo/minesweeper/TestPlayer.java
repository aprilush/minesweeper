package com.southamptoncodedojo.minesweeper;

import com.southamptoncodedojo.minesweeper.players.OrderedPlayer;

import java.util.Arrays;
import java.util.List;

public class TestPlayer extends Player {

    private final Player p;
    public int turns = 0;

    List<Coordinate> avoiding;

    public TestPlayer() {
        super();

        p = new OrderedPlayer();

    }

    @Override
    public String getName() {
        return "Test Player";
    }

    @Override
    public Coordinate takeTurn(MapState mapState) {
        turns += 1;
        Coordinate c = p.takeTurn(mapState);
        if (avoiding != null) {
            while (avoiding.contains(c)) {
                c = p.takeTurn(mapState);
            }
        }
        return c;
    }

    /**
     * Ensure that the PlayerTest does not hit any of the avoided coordinates
     * @param coordinates Coordinates to avoid
     */
    public void avoid(Coordinate[] coordinates) {
        avoiding = Arrays.asList(coordinates);
    }
}
