package com.southamptoncodedojo.minesweeper.exceptions;

/**
 * An invalid coordinate has been used
 */
public class InvalidCoordinateException extends Exception {
    public InvalidCoordinateException(String s) {
        super(s);
    }
}
