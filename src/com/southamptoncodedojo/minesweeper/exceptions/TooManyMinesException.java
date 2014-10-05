package com.southamptoncodedojo.minesweeper.exceptions;

/**
 * When generating the map, too many mines have been specified so the map cannot be generated
 */
public class TooManyMinesException extends Exception {
    public TooManyMinesException(String s) {
        super(s);
    }
}
