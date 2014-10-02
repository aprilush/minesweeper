package com.southamptoncodedojo.minesweeper;

import org.junit.Test;
import com.southamptoncodedojo.minesweeper.TestPlayer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class GameTest {

    @Test
    public void testFirstTurn() {
        Map m = new com.southamptoncodedojo.minesweeper.TestMap(10, new Coordinate[]{new Coordinate(9,9), new Coordinate(5,5), new Coordinate(5, 7), new Coordinate(6, 6)});
        TestPlayer p = new TestPlayer();
        Game g = new Game(m, new Player[]{p});
        g.setup();

        // Player 1 should have taken a turn
        assert(p.turns == 1);

        g.nextTurn();
        assert(p.turns == 2);
        g.nextTurn();
        assert(p.turns == 3);
        g.nextTurn();
        assert(p.turns == 4);
    }

    @Test
    public void testCanLose() {
        TestPlayer p = new TestPlayer();
        Map m = new com.southamptoncodedojo.minesweeper.TestMap(10, new Coordinate[]{new Coordinate(2,0), new Coordinate(5,5), new Coordinate(5, 7), new Coordinate(6, 6)});
        Game g = new Game(m, new Player[]{p});
        g.setup();

        assertFalse(g.gameIsOver());
        assertEquals(Map.State.IN_PROGRESS, g.playersPlayingGame[0].mapInstance.getState());
        g.nextTurn();
        assertFalse(g.gameIsOver());
        assertEquals(Map.State.IN_PROGRESS, g.playersPlayingGame[0].mapInstance.getState());
        g.nextTurn();
        assert(g.gameIsOver());
        assertEquals(Map.State.LOSE, g.playersPlayingGame[0].mapInstance.getState());
    }

    @Test
    public void testCanWin() {
        TestPlayer p = new TestPlayer();
        Coordinate[] minePositions = new Coordinate[]{new Coordinate(2,0), new Coordinate(5,5), new Coordinate(5, 7), new Coordinate(6, 6)};
        p.avoid(minePositions);
        Map m = new com.southamptoncodedojo.minesweeper.TestMap(10, minePositions);
        Game g = new Game(m, new Player[]{p});
        g.setup();

        assertFalse(g.gameIsOver());
        assertEquals(Map.State.IN_PROGRESS, g.playersPlayingGame[0].mapInstance.getState());

        for(int i = 0; i < 63; i++) {
            g.nextTurn();
        }
        assert(g.gameIsOver());
        assertEquals(Map.State.WIN, g.playersPlayingGame[0].mapInstance.getState());
    }

    @Test
    public void TestTwoPlayer() {
        Map m = new com.southamptoncodedojo.minesweeper.TestMap(10, new Coordinate[]{new Coordinate(9,9), new Coordinate(5,5), new Coordinate(5, 7), new Coordinate(6, 6)});
        TestPlayer p1 = new TestPlayer();
        TestPlayer p2 = new TestPlayer();
        Game g = new Game(m, new Player[]{p1, p2});
        g.setup();

        // Players should have taken a turn
        assertEquals(1, p1.turns);
        assertEquals(1, p2.turns);

        g.nextTurn();
        assertEquals(2, p1.turns);
        assertEquals(1, p2.turns);
        g.nextTurn();
        assertEquals(2, p1.turns);
        assertEquals(2, p2.turns);
        g.nextTurn();
        assertEquals(3, p1.turns);
        assertEquals(2, p2.turns);
    }

    @Test
    public void TestTwoPlayerOnePlayerOut() {
        TestPlayer p1 = new TestPlayer();
        TestPlayer p2 = new TestPlayer();
        Coordinate[] minePositions = new Coordinate[]{new Coordinate(2,0), new Coordinate(5,5), new Coordinate(5, 7), new Coordinate(6, 6)};
        p1.avoid(minePositions);
        Map m = new com.southamptoncodedojo.minesweeper.TestMap(10, minePositions);
        Game g = new Game(m, new Player[]{p1, p2});
        g.setup();

        // p1 should be going first, and should win - p2 should go out after taking 3 turns

        assertFalse(g.gameIsOver());
        assertEquals(Map.State.IN_PROGRESS, g.playersPlayingGame[0].mapInstance.getState());
        assertEquals(Map.State.IN_PROGRESS, g.playersPlayingGame[1].mapInstance.getState());
        g.nextTurn();
        g.nextTurn();
        assertFalse(g.gameIsOver());
        assertEquals(Map.State.IN_PROGRESS, g.playersPlayingGame[0].mapInstance.getState());
        assertEquals(Map.State.IN_PROGRESS, g.playersPlayingGame[1].mapInstance.getState());
        g.nextTurn();
        g.nextTurn();
        assertFalse(g.gameIsOver());
        assertEquals(Map.State.IN_PROGRESS, g.playersPlayingGame[0].mapInstance.getState());
        assertEquals(Map.State.LOSE, g.playersPlayingGame[1].mapInstance.getState());

        for(int i = 0; i < 61; i++) {
            g.nextTurn();
        }
        assert(g.gameIsOver());
        assertEquals(Map.State.WIN, g.playersPlayingGame[0].mapInstance.getState());
    }

    @Test
    public void testNextRound() {
        Map m = new com.southamptoncodedojo.minesweeper.TestMap(10, new Coordinate[]{new Coordinate(9,9), new Coordinate(5,5), new Coordinate(5, 7), new Coordinate(6, 6)});
        TestPlayer p1 = new TestPlayer();
        TestPlayer p2 = new TestPlayer();
        Game g = new Game(m, new Player[]{p1, p2});
        g.setup();

        // Players should have taken a turn
        assertEquals(1, p1.turns);
        assertEquals(1, p2.turns);

        g.nextRound();
        assertEquals(2, p1.turns);
        assertEquals(2, p2.turns);
        g.nextRound();
        assertEquals(3, p1.turns);
        assertEquals(3, p2.turns);
        g.nextRound();
        assertEquals(4, p1.turns);
        assertEquals(4, p2.turns);
    }

    @Test
    public void testNextTurnElimination() {
        Coordinate[] mineCoords = new Coordinate[]{new Coordinate(2, 0), new Coordinate(6, 0), new Coordinate(9, 9), new Coordinate(5, 5), new Coordinate(5, 7), new Coordinate(6, 6)};
        Map m = new com.southamptoncodedojo.minesweeper.TestMap(10, mineCoords);
        TestPlayer p1 = new TestPlayer();
        TestPlayer p2 = new TestPlayer();
        TestPlayer p3 = new TestPlayer();

        p3.avoid(mineCoords);
        p1.avoid(new Coordinate[]{new Coordinate(2, 0)});

        Game g = new Game(m, new Player[]{p1, p2, p3});
        g.setup();

        // P1 turn
        g.nextTurn();
        assertEquals(2, g.playersPlayingGame[0].getTurn());
        // P2 turn
        g.nextTurn();
        assertEquals(2, g.playersPlayingGame[1].getTurn());
        // P3 turn
        g.nextTurn();
        assertEquals(2, g.playersPlayingGame[2].getTurn());
        // P1 turn
        g.nextTurn();
        assertEquals(3, g.playersPlayingGame[0].getTurn());
        // P2 turn
        g.nextTurn();
        assertEquals(3, g.playersPlayingGame[1].getTurn());
        // P3 turn
        g.nextTurn();
        assertEquals(3, g.playersPlayingGame[2].getTurn());
        // At this point player 2 should be eliminated
        assertEquals(Map.State.LOSE, g.playersPlayingGame[1].mapInstance.getState());
        // P1 turn
        g.nextTurn();
        assertEquals(4, g.playersPlayingGame[0].getTurn());
        // P3 turn
        g.nextTurn();
        assertEquals(4, g.playersPlayingGame[2].getTurn());
        assertEquals(3, g.playersPlayingGame[1].getTurn());
        // P1 turn
        g.nextTurn();
        assertEquals(5, g.playersPlayingGame[0].getTurn());
        // P3 turn
        g.nextTurn();
        assertEquals(5, g.playersPlayingGame[2].getTurn());
        assertEquals(3, g.playersPlayingGame[1].getTurn());
        // P1 turn
        g.nextTurn();
        assertEquals(6, g.playersPlayingGame[0].getTurn());
        assertEquals(Map.State.LOSE, g.playersPlayingGame[0].mapInstance.getState());
        // P3 turn
        g.nextTurn();
        assertEquals(6, g.playersPlayingGame[2].getTurn());
        assertEquals(3, g.playersPlayingGame[1].getTurn());
        assertEquals(6, g.playersPlayingGame[0].getTurn());
        for (int i = 0; i < 57; i++) {
            // P3 turn
            g.nextTurn();
            assertEquals(7 + i, g.playersPlayingGame[2].getTurn());
            assertEquals(3, g.playersPlayingGame[1].getTurn());
            assertEquals(6, g.playersPlayingGame[0].getTurn());
        }
        assertEquals(Map.State.WIN, g.playersPlayingGame[2].mapInstance.getState());
    }

    @Test
    public void testNextRoundElimination() {
        Coordinate[] mineCoords = new Coordinate[]{new Coordinate(2, 0), new Coordinate(6, 0), new Coordinate(9, 9), new Coordinate(5, 5), new Coordinate(5, 7), new Coordinate(6, 6)};
        Map m = new com.southamptoncodedojo.minesweeper.TestMap(10, mineCoords);
        TestPlayer p1 = new TestPlayer();
        TestPlayer p2 = new TestPlayer();
        TestPlayer p3 = new TestPlayer();

        p3.avoid(mineCoords);
        p1.avoid(new Coordinate[]{new Coordinate(2, 0)});

        Game g = new Game(m, new Player[]{p1, p2, p3});
        g.setup();

        g.nextRound();
        assertEquals(2, g.playersPlayingGame[0].getTurn());
        assertEquals(2, g.playersPlayingGame[1].getTurn());
        assertEquals(2, g.playersPlayingGame[2].getTurn());

        g.nextRound();
        assertEquals(3, g.playersPlayingGame[0].getTurn());
        assertEquals(3, g.playersPlayingGame[1].getTurn());
        assertEquals(3, g.playersPlayingGame[2].getTurn());
        assertEquals(Map.State.LOSE, g.playersPlayingGame[1].mapInstance.getState());

        g.nextRound();
        assertEquals(4, g.playersPlayingGame[0].getTurn());
        assertEquals(4, g.playersPlayingGame[2].getTurn());
        assertEquals(3, g.playersPlayingGame[1].getTurn());

        g.nextRound();
        assertEquals(5, g.playersPlayingGame[2].getTurn());
        assertEquals(3, g.playersPlayingGame[1].getTurn());
        assertEquals(5, g.playersPlayingGame[0].getTurn());
        System.out.println("---");
        g.nextRound();
        assertEquals(6, g.playersPlayingGame[2].getTurn());
        assertEquals(3, g.playersPlayingGame[1].getTurn());
        assertEquals(6, g.playersPlayingGame[0].getTurn());
        assertEquals(Map.State.LOSE, g.playersPlayingGame[0].mapInstance.getState());

        for (int i = 0; i < 57; i++) {
            // P3 turn
            g.nextRound();
            assertEquals(7 + i, g.playersPlayingGame[2].getTurn());
            assertEquals(3, g.playersPlayingGame[1].getTurn());
            assertEquals(6, g.playersPlayingGame[0].getTurn());
        }
        assertEquals(Map.State.WIN, g.playersPlayingGame[2].mapInstance.getState());
    }
}
