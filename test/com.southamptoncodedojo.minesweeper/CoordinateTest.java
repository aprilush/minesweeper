package com.southamptoncodedojo.minesweeper;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class CoordinateTest {

    @Test
    public void testEquality() {
        assertEquals(new Coordinate(0, 0), new Coordinate(0, 0));
        assertNotEquals(new Coordinate(0, 0), new Coordinate(0, 1));
    }

    @Test
    public void testGetSurroundingCoordinates() {
        Coordinate c = new Coordinate(5,5);
        Coordinate u = new Coordinate(5,4);
        Coordinate d = new Coordinate(5,6);
        Coordinate l = new Coordinate(4,5);
        Coordinate r = new Coordinate(6,5);
        Coordinate ul = new Coordinate(4,4);
        Coordinate ur = new Coordinate(6,4);
        Coordinate dl = new Coordinate(4,6);
        Coordinate dr = new Coordinate(6,6);

        Coordinate[] returnedCoordinates = c.getSurroundingCoordinates(10);

        assertEquals(returnedCoordinates.length, 8);

        Coordinate[] expectedCoordinates = new Coordinate[]{u,d,l,r,ul,ur,dl,dr};
        assertTrue(Arrays.asList(returnedCoordinates).containsAll(Arrays.asList(expectedCoordinates)));
        assertTrue(Arrays.asList(expectedCoordinates).containsAll(Arrays.asList(returnedCoordinates)));
    }

    @Test
    public void testGetSurroundingCoordinatesEdge() {
        Coordinate c = new Coordinate(0,5);
        Coordinate u = new Coordinate(0,4);
        Coordinate d = new Coordinate(0,6);
        Coordinate r = new Coordinate(1,5);
        Coordinate ur = new Coordinate(1,4);
        Coordinate dr = new Coordinate(1,6);

        Coordinate[] returnedCoordinates = c.getSurroundingCoordinates(10);

        assertEquals(returnedCoordinates.length, 5);

        Coordinate[] expectedCoordinates = new Coordinate[]{u,d,r,ur,dr};
        assertTrue(Arrays.asList(returnedCoordinates).containsAll(Arrays.asList(expectedCoordinates)));
    }

    @Test
    public void testIsValid() {
        Coordinate c = new Coordinate(0,0);
        assertTrue(c.isValid(10));
        assertTrue(c.isValid(1));
        c = new Coordinate(-1,0);
        assertFalse(c.isValid(10));
        assertFalse(c.isValid(1));
        c = new Coordinate(0,-1);
        assertFalse(c.isValid(10));
        assertFalse(c.isValid(1));
        c = new Coordinate(5,5);
        assertFalse(c.isValid(5));
        assertFalse(c.isValid(1));
        assertTrue(c.isValid(6));
    }
}
