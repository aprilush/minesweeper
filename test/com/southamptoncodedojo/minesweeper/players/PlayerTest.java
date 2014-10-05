package com.southamptoncodedojo.minesweeper.players;

import com.southamptoncodedojo.minesweeper.*;
import com.southamptoncodedojo.minesweeper.TestGame;
import com.southamptoncodedojo.minesweeper.exceptions.InvalidCoordinateException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Modify this test class to set up tests for your player
 *
 * If your player avoids storing state this makes testing much easier - as we can mock out the current game state
 * and test against that. See the methods in this class for an example.
 *
 * Run the test by running ./test.sh
 */
public class PlayerTest {

    OrderedPlayer player; // Set this to your player's class

    @Before
    public void setUp() {
        // Create the player
        player = new OrderedPlayer(); // Set this to your player's class
    }

    /**
     * This is a test designed for when the Player requires state.
     *
     * We can generate the map and then call the player from the beginning - testing to ensure it is in the correct
     * state at each point.
     */
    @Test
    public void testWithState() throws InvalidCoordinateException {
        Map m = new TestMap(10, new Coordinate[]{new Coordinate(5, 5),new Coordinate(7, 5)});
        TestGame g = new TestGame(m, player);

        // Here we see that the state is maintained and we can test the behaviour
        // at each turn
        assertEquals(new Coordinate(0, 0), g.firstTurn());
        assertEquals(new Coordinate(1, 0), g.nextTurn());
        assertEquals(new Coordinate(2, 0), g.nextTurn());

        // You can test internal state if the property is "protected" or more open
        assertEquals(new Coordinate(3, 0), player.nextCoordinate);

        // We can also test the state of the map (to ensure that the correct items have been flagged, etc.)
        // (to use the mapState .firstTurn must have been called already)
        assertFalse(g.mapInstance.isFlagged(new Coordinate(3, 3)));
    }

    /**
     * If the Player does not store its own state, and only relies on that which comes from the
     * mapState, we can construct our own mapState at an arbitrary point
     */
    @Test
    public void testWithoutState() throws InvalidCoordinateException {
        RandomPlayer randomPlayer = new RandomPlayer();
        Map map = new TestMap(10, new Coordinate[]{new Coordinate(5, 5)});
        MapInstance mi = new MapInstance(map);

        // In this case we just check that it randomly selects within the bounds of the map
        assert(randomPlayer.takeTurn(mi).isValid(map.getSize()));

        // We can also set the spaces which have already been opened using isOpened
        mi.hit(new Coordinate(3, 6)); // Open (3, 6)

        // and change what is flagged
        mi.flag(new Coordinate(7, 2)); // Set (7, 2) to flagged

        randomPlayer.takeTurn(mi);
        // Then after calling .takeTurn we can check if that state has changed in a particular way
        assertFalse(mi.isFlagged(new Coordinate(4, 4))); // (4, 4) should not have been flagged
    }
}
