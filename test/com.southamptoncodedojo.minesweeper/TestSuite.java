package com.southamptoncodedojo.minesweeper;

import com.southamptoncodedojo.minesweeper.CoordinateTest;
import com.southamptoncodedojo.minesweeper.MapTest;
import com.southamptoncodedojo.minesweeper.MapInstanceTest;
import com.southamptoncodedojo.minesweeper.GameTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({CoordinateTest.class, MapTest.class, MapInstanceTest.class, GameTest.class})
public class TestSuite {
}
