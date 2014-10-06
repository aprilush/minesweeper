#!/bin/sh
# This script just sets up a new class for your player
cp src/com/southamptoncodedojo/minesweeper/players/RandomPlayer.java src/com/southamptoncodedojo/minesweeper/players/$1.java
sed -i '' "s/RandomPlayer/$1/g" src/com/southamptoncodedojo/minesweeper/players/$1.java
sed -i '' "s/RandomPlayer/$1/g" src/com/southamptoncodedojo/minesweeper/Minesweeper.java

echo "Done. Now open src/com/southamptoncodedojo/minesweeper/players/$1.java and edit your player. Run it with ./run.sh, edit tests in test/com/southamptoncodedojo/minesweeper/players/PlayerTest.java and run tests with ./test.sh";