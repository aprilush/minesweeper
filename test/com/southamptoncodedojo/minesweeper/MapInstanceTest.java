package com.southamptoncodedojo.minesweeper;


import com.southamptoncodedojo.minesweeper.exceptions.InvalidCoordinateException;
import com.southamptoncodedojo.minesweeper.exceptions.TooManyMinesException;
import com.southamptoncodedojo.minesweeper.exceptions.UnknownCountException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MapInstanceTest {

    @Test
    public void testCreateMapInstance() throws TooManyMinesException {
        Map m = new Map(10);
        m.generate(50);
        MapInstance mi = new MapInstance(m);
    }

    @Test(expected=UnknownCountException.class)
    public void testMapInstanceGetCountBad0() throws TooManyMinesException, UnknownCountException, InvalidCoordinateException {
        Map m = new Map(10);
        MapInstance mi = new MapInstance(m);
        assertEquals(null, mi.getCount(new Coordinate(0, 0)));
    }
    @Test(expected=UnknownCountException.class)
    public void testMapInstanceGetCountBad1() throws TooManyMinesException, UnknownCountException, InvalidCoordinateException {
        Map m = new Map(10);
        MapInstance mi = new MapInstance(m);
        assertEquals(null, mi.getCount(new Coordinate(9, 9)));
    }
    @Test(expected=UnknownCountException.class)
    public void testMapInstanceGetCountBad2() throws TooManyMinesException, UnknownCountException, InvalidCoordinateException {
        Map m = new Map(10);
        MapInstance mi = new MapInstance(m);
        assertEquals(null, mi.getCount(new Coordinate(5, 5)));
    }


    @Test
    public void testMapInstanceGetCountHit() throws InvalidCoordinateException, TooManyMinesException, UnknownCountException {
        Map m = new Map(10);
        MapInstance mi = new MapInstance(m);
        mi.hit(new Coordinate(0,0));

        // This should "Open" every single point on the map - so they should all return 0

        for(int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                assertEquals(0, mi.getCount(new Coordinate(x, y)));
            }
        }
    }

    @Test
    public void testMapInstanceExpand() throws InvalidCoordinateException, UnknownCountException {
        Map m = new TestMap(10, new Coordinate[]{new Coordinate(5,5)});
        MapInstance mi = new MapInstance(m);
        mi.hit(new Coordinate(0,0));

        // This should "Open all points except for 5,5

        assertEquals(0, mi.getCount(new Coordinate(0, 0)));
        assertEquals(0, mi.getCount(new Coordinate(9, 9)));
        assertEquals(1, mi.getCount(new Coordinate(4, 5)));
        assertEquals(1, mi.getCount(new Coordinate(6, 5)));
        assertEquals(1, mi.getCount(new Coordinate(5, 4)));
        assertEquals(1, mi.getCount(new Coordinate(5, 6)));
        assertEquals(1, mi.getCount(new Coordinate(6, 6)));
        assertEquals(1, mi.getCount(new Coordinate(6, 4)));
        assertEquals(1, mi.getCount(new Coordinate(4, 6)));
        assertEquals(1, mi.getCount(new Coordinate(4, 4)));
    }

    @Test
    public void testGetRemainingCoordinates() throws InvalidCoordinateException, UnknownCountException {
        Map m = new TestMap(10, new Coordinate[]{new Coordinate(5,5)});
        MapInstance mi = new MapInstance(m);
        assert(mi.getRemainingCoordinates() == 99);
        mi.hit(new Coordinate(0,0));
        assert(mi.getRemainingCoordinates() == 0);
    }

    @Test
    public void testWin() throws InvalidCoordinateException, UnknownCountException {
        Map m = new TestMap(10, new Coordinate[]{new Coordinate(5,5)});
        MapInstance mi = new MapInstance(m);
        assert(mi.getState() == MapInstance.State.IN_PROGRESS);
        mi.hit(new Coordinate(0,0));
        assert(mi.getState() == MapInstance.State.WIN);
    }

    @Test
    public void testLose() throws InvalidCoordinateException, UnknownCountException {
        Map m = new TestMap(10, new Coordinate[]{new Coordinate(5,5)});
        MapInstance mi = new MapInstance(m);
        assert(mi.getState() == MapInstance.State.IN_PROGRESS);
        mi.hit(new Coordinate(5,5));
        assert(mi.getState() == MapInstance.State.LOSE);
    }

    @Test
    public void testLastHit() throws InvalidCoordinateException {
        Map m = new TestMap(10, new Coordinate[]{new Coordinate(5,5)});
        MapInstance mi = new MapInstance(m);
        mi.hit(new Coordinate(1,1));
        assertEquals(new Coordinate(1,1),mi.lastHit());
        mi = new MapInstance(m);
        mi.hit(new Coordinate(5,5));
        assertEquals(new Coordinate(5,5),mi.lastHit());
    }
}
