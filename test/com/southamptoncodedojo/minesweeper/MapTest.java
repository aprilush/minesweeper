package com.southamptoncodedojo.minesweeper;

import com.southamptoncodedojo.minesweeper.exceptions.InvalidCoordinateException;
import com.southamptoncodedojo.minesweeper.exceptions.TooManyMinesException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class MapTest {

    Coordinate[] getMinePositions(Map map) {
        ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
        for(int x = 0; x < map.getSize(); x++) {
            for (int y = 0; y < map.getSize(); y++) {
                Coordinate c = new Coordinate(x, y);
                try {
                    if (map.mineIsAt(c)) {
                        coordinates.add(c);
                    }
                } catch (InvalidCoordinateException e) {
                    e.printStackTrace();
                }
            }
        }
        return coordinates.toArray(new Coordinate[]{});
    }

    @Test
    public void testCreateMap() throws TooManyMinesException {
        Map map = new Map(10);

        // The map should have no mines
        assert(getMinePositions(map).length == 0);

        map = new Map(10);
        map.generate(1);
        assertEquals(getMinePositions(map).length, 1);

        map = new Map(10);
        map.generate(5);
        assertEquals(getMinePositions(map).length, 5);

        map = new Map(10);
        map.generate(10);
        assertEquals(getMinePositions(map).length, 10);

        map = new Map(10);
        map.generate(20);
        assertEquals(getMinePositions(map).length, 20);

        map = new Map(10);
        map.generate(50);
        assertEquals(getMinePositions(map).length, 50);

        map = new Map(10);
        map.generate(100);
        assertEquals(getMinePositions(map).length, 100);
    }

    @Test(expected= TooManyMinesException.class)
    public void testCreateMapTooManyMines() throws TooManyMinesException {
        Map map = new Map(10);
        map.generate(101);
    }

    @Test
    public void testCreateMapInitialHits() throws TooManyMinesException, InvalidCoordinateException {
        Random r = new Random();
        for (int i = 0; i < 1000; i++) {
            // Run the test 1000 times to try to minimise false positives - can't be certain though due to randomness
            Coordinate c = new Coordinate(r.nextInt(10), r.nextInt(10));
            Map map = new Map(10);
            map.generate(90, new Coordinate[]{c});

            assertFalse(map.mineIsAt(c));
        }
    }

    @Test
    public void testCreateMapSurroundingInitialHits() throws TooManyMinesException, InvalidCoordinateException {
        Random r = new Random();
        for (int i = 0; i < 1000; i++) {
            // Run the test 1000 times to try to minimise false positives - can't be certain though due to randomness
            Coordinate c = new Coordinate(r.nextInt(10), r.nextInt(10));
            Map map = new Map(10);
            map.generate(90, new Coordinate[]{c});

            assertFalse(map.mineIsAt(c));

            for(Coordinate c1 : c.getSurroundingCoordinates(10)) {
                assertFalse(map.mineIsAt(c1));
            }
        }
    }

    @Test
    public void testCreateMapSurroundingInitialHitsOverlapping() throws TooManyMinesException, InvalidCoordinateException {
        Random r = new Random();
        for (int i = 0; i < 1000; i++) {
            // Run the test 1000 times to try to minimise false positives - can't be certain though due to randomness
            Coordinate c = new Coordinate(r.nextInt(9), r.nextInt(10));
            Coordinate c2 = new Coordinate(c.getX() + 1, c.getY());
            Map map = new Map(10);
            map.generate(85, new Coordinate[]{c, c2});

            assertFalse(map.mineIsAt(c));
            assertFalse(map.mineIsAt(c2));

            for(Coordinate c1 : c.getSurroundingCoordinates(10)) {
                assertFalse(map.mineIsAt(c1));
            }
            for(Coordinate c1 : c2.getSurroundingCoordinates(10)) {
                assertFalse(map.mineIsAt(c1));
            }
        }
    }
}
