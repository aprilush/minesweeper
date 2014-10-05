#!/bin/sh
rm -R bin
mkdir bin
javac -d bin -sourcepath src:test -cp lib/hamcrest-core-1.3.jar:lib/jlibs-core.jar:lib/junit-4.12-beta-2.jar:. test/com/southamptoncodedojo/minesweeper/players/PlayerTest.java
java -cp lib/hamcrest-core-1.3.jar:lib/jlibs-core.jar:lib/junit-4.12-beta-2.jar:bin:. org.junit.runner.JUnitCore com.southamptoncodedojo.minesweeper.players.PlayerTest