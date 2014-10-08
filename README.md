Minesweeper
=========

About
--

This is a Minesweeper AI runner for Java.

It was created for use at the October Southampton Code Dojo.

Instructions
--

1. Clone the repository
```
git clone git@github.com:southampton-code-dojo/minesweeper.git
cd minesweeper
```

2. Ensure that the game runs
```
./run.sh
```

3. Run the setup script, specifying your player's name
```
./setup.sh MyPlayerName
```

4. Edit **src/com/southamptoncodedojo/minesweeper/players/YourPlayerName.java** - implement your solution in the ``takeTurn()`` method.

    Java documentation is available in the **doc/** folder. The documentation for the parameter to the ``takeTurn()`` method is available at **doc/com/southamptoncodedojo/minesweeper/MapState.html**.

5. Implement unit tests in **test/com/southamptoncodedojo/minesweeper/players/PlayerTest.java** and run the tests using ``./test.sh``

6. Run a game using your player with ``./run.sh``. Configure the game (number of mines, map size, other players, etc.) in **src/com/southamptoncodedojo/minesweeper/Minesweeper.java**

7. One you're finished, email your completed solution to **jonathan@jscott.me** and we'll play all of the entries against each other at the end of the dojo.

IDEs
--

Included are configuration files for [IntelliJ](https://www.jetbrains.com/idea/) and [Eclipse](https://www.eclipse.org). You should be able to just open/import the project as normal. If you want to use a different IDE you're on your own.