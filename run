#!/bin/sh
rm -R bin
mkdir bin
javac -d bin -sourcepath src -cp lib/hamcrest-core-1.3.jar:lib/jlibs-core.jar:junit-4.12-beta-2.jar:. src/com/southamptoncodedojo/minesweeper/Minesweeper.java
java -cp lib/hamcrest-core-1.3.jar:lib/jlibs-core.jar:lib/junit-4.12-beta-2.jar:bin:. com.southamptoncodedojo.minesweeper.Minesweeper